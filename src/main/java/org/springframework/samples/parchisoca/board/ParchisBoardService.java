package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.parchis.BoxesParchisService;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceRepository;
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

    @Transactional(readOnly = true)
    public ParchisBoard findById(Integer id) {
		return parchisBoardRepository.findById(id).get();
	}

    @Transactional
    public void save(ParchisBoard parchisBoard) {
        parchisBoardRepository.save(parchisBoard);
    }

    public ParchisBoard initBoard(Game game) {
        ParchisBoard parchisBoard = new ParchisBoard(); 
        List<Colour> colours = List.of(Colour.RED,Colour.BLUE,Colour.YELLOW,Colour.GREEN);
        parchisBoardRepository.save(parchisBoard);
        List<Player> listPlayers = game.getPlayers();
        int j = 0;
        for(Player p: listPlayers) {
            Colour color = colours.get(j);
            for (int i=0; i < 4; i++) {
                ParchisPiece piece = new ParchisPiece();
                piece.setPlayer(p);
                piece.setColour(color);
                piece.setParchisBoard(parchisBoard);
                parchisPieceService.save(piece);
                p.addPiecePlayer(piece);
                parchisBoard.addPieceParchis(piece);
            }
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
            j++;
        }
        parchisBoardRepository.save(parchisBoard);
        List<BoxesParchis> ls = initBoxes(parchisBoard);
        parchisBoard.setBoxes(ls);
        parchisBoardRepository.save(parchisBoard);
        return parchisBoard;
    }
    
    @Transactional
    public List<BoxesParchis> initBoxes(ParchisBoard parchisBoard) {
        List<BoxesParchis> boxesParchis = new ArrayList<BoxesParchis>(68);
        for(int i=0; i<=68; i++) {
            BoxesParchis box = new BoxesParchis();
            if (i==5 || i==22 || i==39 || i==56) {
                box.boxesParchis(i, false, true, true);
            } else if (i==12 || i==29 || i==46 || i==63) {
                box.boxesParchis(i, false, true, false);
            } else if (i==17 || i==34 || i==51 || i==68) {
                box.boxesParchis(i, false, true, true);
            } else {
                box.boxesParchis(i, false, false, false);
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
        if(turn == players.size()-1){
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

    public ParchisPiece movementPiece(ParchisBoard parchisBoard, ParchisPiece parchisPiece, ParchisDice parchisDice){

        
        Integer lastPosition = parchisPiece.getPosition();
        Integer diceNumber = parchisDice.getNumber();
        if (lastPosition == 0 && diceNumber == 5){
            firstMove(parchisPiece);
            BoxesParchis box = boxesParchisService.findBoxByPosition(parchisPiece.getPosition(),parchisBoard);
            box.addPieceToBox(parchisPiece);
        }else if (lastPosition != 0) {
            Integer newPosition = lastPosition + diceNumber;
            parchisPiece.setPosition(newPosition);
            parchisPieceService.save(parchisPiece);
            BoxesParchis lastBox = boxesParchisService.findBoxByPosition(lastPosition,parchisBoard);
            lastBox.getPiecesInBox().remove(parchisPiece);
            BoxesParchis newBox = boxesParchisService.findBoxByPosition(newPosition,parchisBoard);
            newBox.addPieceToBox(parchisPiece);
            boxesParchisService.save(lastBox); boxesParchisService.save(lastBox);

            //eatPiece

            eatPiece(parchisPiece);
        }
        
        return parchisPiece;
    }

    private void firstMove(ParchisPiece parchisPiece) {
        Colour color = parchisPiece.getColour();
        if (color == Colour.BLUE){
            parchisPiece.setPosition(22);
        }else if(color == Colour.RED){
            parchisPiece.setPosition(39);
        }else if (color == Colour.GREEN){
            parchisPiece.setPosition(56);
        }else{
            parchisPiece.setPosition(5);
        }
        parchisPieceService.save(parchisPiece);
    }

    private void eatPiece(ParchisPiece parchisPiece){

        Integer position = parchisPiece.getPosition();
        BoxesParchis box = boxesParchisService.findBoxByPosition(position, parchisPiece.getParchisBoard());
        if (box.getPiecesInBox().size() > 0){
            List<ParchisPiece> pieces = box.getPiecesInBox();
            Optional<ParchisPiece> pieceToEat = pieces.stream().filter(x->x.getColour() != parchisPiece.getColour()).findFirst();
            if (pieceToEat.isPresent()){
                box.getPiecesInBox().remove(pieceToEat.get());
                boxesParchisService.save(box);
            }  
        } 

    }

}
