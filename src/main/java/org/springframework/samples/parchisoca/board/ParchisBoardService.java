package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.parchis.BoxesParchisService;
import org.springframework.samples.parchisoca.parchis.FinishBoxes;
import org.springframework.samples.parchisoca.parchis.FinishBoxesService;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisBoardService {

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    ParchisDiceService parchisDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisBoardRepository parchisBoardRepository;

    @Autowired
    BoxesParchisService boxesParchisService;

    @Autowired
    FinishBoxesService finishBoxesService;

    @Transactional(readOnly = true)
    public ParchisBoard findById(Integer id) {
        return parchisBoardRepository.findById(id).get();
    }

    @Transactional
    public void save(ParchisBoard parchisBoard) {
        parchisBoardRepository.save(parchisBoard);
    }

    @Transactional
    public ParchisBoard initBoard(Game game) {
        ParchisBoard parchisBoard = new ParchisBoard();

        parchisBoardRepository.save(parchisBoard);
        List<Player> listPlayers = game.getPlayers();

        initPlayers(listPlayers, parchisBoard);

        parchisBoardRepository.save(parchisBoard);
        List<BoxesParchis> ls = initBoxes(parchisBoard);
        List<FinishBoxes> finishBoxes = initFinishBoxes(parchisBoard);

        parchisBoard.setBoxes(ls);
        parchisBoard.setFinishBoxes(finishBoxes);
        parchisBoardRepository.save(parchisBoard);

        return parchisBoard;
    }

    private void initPlayers(List<Player> listPlayers, ParchisBoard parchisBoard) {
        List<Colour> colours = List.of(Colour.RED, Colour.BLUE, Colour.YELLOW, Colour.GREEN);
        int j = 0;
        for (Player p : listPlayers) {
            Colour color = colours.get(j);
            for (int i = 0; i < 4; i++) {
                ParchisPiece piece = new ParchisPiece();
                piece.setPlayer(p);
                piece.setColour(color);
                piece.setParchisBoard(parchisBoard);

                parchisPieceService.save(piece);
                p.addPiecePlayer(piece);
                parchisBoard.addPieceParchis(piece);
            }
            initDices(parchisBoard, p);
            j++;
        }
    }

    private List<FinishBoxes> initFinishBoxes(ParchisBoard parchisBoard) {
        List<FinishBoxes> finishBoxes = new ArrayList<FinishBoxes>(8);

        for (int i = 0; i <= 8; i++) {
            FinishBoxes box = new FinishBoxes();
            if (i == 9) {
                box.setGoal(true);
            }
            box.setPosition(9);
            finishBoxes.add(box);
            finishBoxesService.save(box);
        }

        return finishBoxes;
    }

    @Transactional
    public List<BoxesParchis> initBoxes(ParchisBoard parchisBoard) {
        List<BoxesParchis> boxesParchis = new ArrayList<BoxesParchis>(68);
        for (int i = 0; i <= 68; i++) {
            BoxesParchis box = new BoxesParchis();
            if (i == 5 || i == 22 || i == 39 || i == 56) {
                box.setSafe(true);
                box.setExit(true);
                box.setBridge(false);

            } else if (i == 12 || i == 29 || i == 46 || i == 63) {
                box.setSafe(true);
                box.setExit(false);
                box.setBridge(false);
            } else if (i == 17 || i == 34 || i == 51 || i == 68) {
                box.setSafe(true);
                box.setExit(false);
                box.setBridge(false);
            } else {
                box.setSafe(false);
                box.setExit(false);
                box.setBridge(false);
            }

            box.setPositionBoard(i);
            box.setParchisBoard(parchisBoard);
            boxesParchis.add(box);
            boxesParchisService.save(box);
        }
        return boxesParchis;
    }

    @Transactional
    public Player nextTurn(ParchisBoard parchisBoard, Game game, Integer turn) {
        List<Player> players = game.getPlayers();
        if (turn == players.size() - 1) {
            parchisBoard.setTurn(0);
        } else {
            turn += 1;
            parchisBoard.setTurn(turn);
        }
        return players.get(turn);
    }

    @Transactional(readOnly = true)
    public List<ParchisDice> findParchisDiceByPlayer(Player player, ParchisBoard parchisBoard) {
        List<ParchisDice> result = parchisBoardRepository.getParchisDiceByPlayer(player, parchisBoard);
        return result;
    }

    @Transactional(readOnly = true)
    public Boolean isActualPlayer(Player piecePlayer) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

        return currentPlayer.equals(piecePlayer);
    }

    // Aciones de una pieza
    @Transactional
    public ParchisPiece movementPiece(ParchisBoard parchisBoard, ParchisPiece parchisPiece, ParchisDice parchisDice) {

        if (parchisPiece.getFinishPosition() != null && !parchisPiece.getInGoal()) { // Se encuentra en posiciones
                                                                                     // finales // finales
            movementFinish(parchisPiece, parchisBoard, parchisDice);
        }

        if (parchisPiece.getPosition() != null) { // Se encuentra en recorrido normal
            normalMovement(parchisPiece, parchisBoard, parchisDice);

        }

        return parchisPiece;
    }

    @Transactional
    private void normalMovement(ParchisPiece parchisPiece, ParchisBoard parchisBoard, ParchisDice parchisDice) {
        Integer lastPosition = parchisPiece.getPosition();
        Integer diceNumber = parchisDice.getNumber();

        if (lastPosition == 0 && diceNumber == 5) { // Si la casilla esta en casa
            firstMove(parchisPiece, parchisBoard);

        } else if (lastPosition != 0) { // si ya esta en recorrido
            Integer newPosition = lastPosition + diceNumber;

            if (newPosition > 68) {
                newPosition = newPosition % 68;
            }

            BoxesParchis lastBox = boxesParchisService.findBoxByPosition(lastPosition, parchisBoard);
            BoxesParchis newBox = boxesParchisService.findBoxByPosition(newPosition, parchisBoard);

            movement(parchisPiece, lastBox, newBox, parchisBoard);

            actionPiece(parchisPiece, newBox, lastBox);

        }
    }

    // Realiza acciones del parchis
    @Transactional
    private void actionPiece(ParchisPiece parchisPiece, BoxesParchis newBox, BoxesParchis lastBox) {
        if (parchisPiece.getPosition() != null) {
            // Comerse una pieza
            if (!newBox.getSafe()) {
                eatPiece(parchisPiece);
            }
            // Hacer un puente
            bridge(parchisPiece, lastBox);
        }
    }

    // Movimiento de una pieza
    @Transactional
    private void movement(ParchisPiece parchisPiece, BoxesParchis lastBox, BoxesParchis newBox,
            ParchisBoard parchisBoard) {

        List<BoxesParchis> ls = listBoxesToGo(lastBox, newBox, parchisBoard);

        boxesCheckFor(ls, parchisPiece, lastBox, newBox, parchisBoard);
    }

    @Transactional
    private List<BoxesParchis> listBoxesToGo(BoxesParchis lastBox, BoxesParchis newBox, ParchisBoard parchisBoard) {
        List<BoxesParchis> res = new ArrayList<>();
        if (lastBox.getPositionBoard() > newBox.getPositionBoard()) {
            for (int i = lastBox.getPositionBoard() + 1; i >= 68; i++) {
                BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                res.add(box);
            }
            for (int i = 1; i <= newBox.getPositionBoard(); i++) {
                BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                res.add(box);
            }
        } else {
            for (int i = lastBox.getPositionBoard() + 1; i <= newBox.getPositionBoard(); i++) {
                BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                res.add(box);
            }
        }
        return res;
    }

    @Transactional
    private void boxesCheckFor(List<BoxesParchis> ls, ParchisPiece parchisPiece, BoxesParchis lastBox,
            BoxesParchis newBox, ParchisBoard parchisBoard) {
        for (BoxesParchis bx : ls) {
            if (bx.getBridge()) {
                checkBridge(bx, parchisPiece, lastBox, parchisBoard);
                break;
            }

            checkEquals(bx, newBox, lastBox, parchisPiece);

            if (checkIsInFinish(parchisPiece, bx)) { // Checkeamos si ha entrado en la casillas finales
                enterFinishPositions(bx, parchisPiece, parchisBoard, lastBox, newBox, ls);
                break;
            }
        }
    }

    @Transactional
    private void enterFinishPositions(BoxesParchis bx, ParchisPiece parchisPiece, ParchisBoard parchisBoard,
            BoxesParchis lastBox,
            BoxesParchis newBox, List<BoxesParchis> ls) {
        lastBox.getPiecesInBox().remove(parchisPiece);
        parchisPiece.setPosition(null);
        Integer index = ls.indexOf(bx);
        Integer finishPosition = ls.size() - index;
        if (finishPosition > 7) {
            finishPosition = parchisBoard.bounceBack(finishPosition);
        }
        parchisPiece.setFinishPosition(finishPosition);
        parchisPieceService.save(parchisPiece);
        boxesParchisService.save(lastBox);
    }

    @Transactional
    private void checkEquals(BoxesParchis bx, BoxesParchis newBox, BoxesParchis lastBox, ParchisPiece parchisPiece) {
        if (bx.equals(newBox)) {
            lastBox.getPiecesInBox().remove(parchisPiece);
            parchisPiece.setPosition(newBox.getPositionBoard());
            newBox.addPieceToBox(parchisPiece);
            parchisPieceService.save(parchisPiece);
            boxesParchisService.save(lastBox);
            boxesParchisService.save(newBox);
        }
    }

    @Transactional
    private void checkBridge(BoxesParchis bx, ParchisPiece parchisPiece, BoxesParchis lastBox,
            ParchisBoard parchisBoard) {

        lastBox.getPiecesInBox().remove(parchisPiece);
        parchisPiece.setPosition(bx.getPositionBoard() - 1);
        BoxesParchis boxToMove = boxesParchisService.findBoxByPosition(bx.getPositionBoard() - 1, parchisBoard);
        boxToMove.addPieceToBox(parchisPiece);
        parchisPieceService.save(parchisPiece);
        boxesParchisService.save(lastBox);
        boxesParchisService.save(boxToMove);

    }

    // Comprobacion si tiene que entrar en casillas finales
    @Transactional
    private Boolean checkIsInFinish(ParchisPiece parchisPiece, BoxesParchis bx) {
        if (parchisPiece.getColour() == Colour.RED && bx.getPositionBoard() == 35) {
            return true;
        }
        if (parchisPiece.getColour() == Colour.BLUE && (bx.getPositionBoard() == 18)) {
            return true;
        }
        if (parchisPiece.getColour() == Colour.YELLOW && (bx.getPositionBoard() == 1)) {
            return true;
        }
        if (parchisPiece.getColour() == Colour.GREEN && (bx.getPositionBoard() == 52)) {
            return true;
        }

        return false;
    }

    // Movimiento en casillas finales
    @Transactional
    private void movementFinish(ParchisPiece parchisPiece, ParchisBoard parchisBoard, ParchisDice parchisDice) {
        Integer lastPosition = parchisPiece.getFinishPosition();
        Integer diceNumber = parchisDice.getNumber();
        Integer suma = lastPosition + diceNumber;
        Integer position = parchisBoard.bounceBack(suma);
        parchisPiece.setFinishPosition(position);
        if (position == 7) {
            parchisPiece.setInGoal(true);
            parchisPiece.setJustInGoal(true);
        }
        parchisPieceService.save(parchisPiece);
    }

    // Salida de casa
    @Transactional
    private void firstMove(ParchisPiece parchisPiece, ParchisBoard parchisBoard) {
        BoxesParchis box22 = boxesParchisService.findBoxByPosition(22, parchisBoard);
        BoxesParchis box39 = boxesParchisService.findBoxByPosition(39, parchisBoard);
        BoxesParchis box56 = boxesParchisService.findBoxByPosition(56, parchisBoard);
        BoxesParchis box5 = boxesParchisService.findBoxByPosition(5, parchisBoard);
        Colour color = parchisPiece.getColour();
        if (color == Colour.BLUE && box22.getPiecesInBox().size() < 2) {
            parchisPiece.setPosition(22);
            box22.addPieceToBox(parchisPiece);
            boxesParchisService.save(box22);
        } else if (color == Colour.RED && box39.getPiecesInBox().size() < 2) {
            parchisPiece.setPosition(39);
            box39.addPieceToBox(parchisPiece);
            boxesParchisService.save(box39);
        } else if (color == Colour.GREEN && box56.getPiecesInBox().size() < 2) {
            parchisPiece.setPosition(56);
            box56.addPieceToBox(parchisPiece);
            boxesParchisService.save(box56);
        } else if (color == Colour.YELLOW && box5.getPiecesInBox().size() < 2) {
            parchisPiece.setPosition(5);
            box5.addPieceToBox(parchisPiece);
            boxesParchisService.save(box5);
        }
        parchisPieceService.save(parchisPiece);
    }

    @Transactional
    private void eatPiece(ParchisPiece parchisPiece) {

        Integer position = parchisPiece.getPosition();
        BoxesParchis box = boxesParchisService.findBoxByPosition(position, parchisPiece.getParchisBoard());
        if (box.getPiecesInBox().size() > 0) {
            List<ParchisPiece> pieces = box.getPiecesInBox();
            Optional<ParchisPiece> pieceToEat = pieces.stream().filter(x -> x.getColour() != parchisPiece.getColour())
                    .findFirst();
            if (pieceToEat.isPresent()) {
                box.getPiecesInBox().remove(pieceToEat.get());
                boxesParchisService.save(box);
                pieceToEat.get().setPosition(0);
                parchisPieceService.save(pieceToEat.get());
                parchisPiece.setJustAte(true);
                parchisPieceService.save(parchisPiece);
            }
        }

    }

    @Transactional
    private void bridge(ParchisPiece parchisPiece, BoxesParchis lastBox) {

        if (lastBox.getBridge() == true) {
            lastBox.setBridge(false);
            boxesParchisService.save(lastBox);
        }
        Integer position = parchisPiece.getPosition();
        BoxesParchis box = boxesParchisService.findBoxByPosition(position, parchisPiece.getParchisBoard());
        List<ParchisPiece> pieces = box.getPiecesInBox();
        if (pieces.size() == 2 && pieces.stream().allMatch(x -> x.getColour() == parchisPiece.getColour())) {
            box.setBridge(true);
            boxesParchisService.save(box);
            ;
        } else {
            box.setBridge(false);
            boxesParchisService.save(box);
        }

    }

    @Transactional
    private void initDices(ParchisBoard parchisBoard, Player p) {
        ParchisDice parchisDice1 = new ParchisDice();
        ParchisDice parchisDice2 = new ParchisDice();
        parchisDiceService.save(parchisDice1, parchisDice2);
        parchisDice1.setParchisBoard(parchisBoard);
        parchisDice2.setParchisBoard(parchisBoard);
        p.addDicesParchis(parchisDice1, parchisDice2);
        parchisDice1.setPlayer(p);
        parchisDice2.setPlayer(p);
        playerService.save(p);
        parchisDiceService.save(parchisDice1, parchisDice2);
    }

}
