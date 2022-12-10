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
        List<FinishBoxes> finishBoxes = initFinishBoxes(parchisBoard);
        
        parchisBoard.setBoxes(ls);
        parchisBoard.setFinishBoxes(finishBoxes);
        parchisBoardRepository.save(parchisBoard);
       

        //test
            // ParchisPiece ptest = parchisPieceService.findById(20);
            // ParchisPiece ptest1 = parchisPieceService.findById(21);
            // BoxesParchis box = boxesParchisService.findBoxByPosition(43, parchisBoard);
            // box.addPieceToBox(ptest); box.addPieceToBox(ptest1);
            // boxesParchisService.save(box);
        return parchisBoard;
    }
    
    private List<FinishBoxes> initFinishBoxes(ParchisBoard parchisBoard) {
        List<FinishBoxes> finishBoxes = new ArrayList<FinishBoxes>(8);
        for (int i = 0; i<=8; i++){
            FinishBoxes box = new FinishBoxes();
            if (i == 9){
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
        for(int i=0; i<=68; i++) {
            BoxesParchis box = new BoxesParchis();
            if (i==5 || i==22 || i==39 || i==56) {
               
                box.setEntry(false);
                box.setSafe(true);
                box.setExit(true);
                box.setBridge(false);

            } else if (i==12 || i==29 || i==46 || i==63) {
                
                box.setEntry(false);
                box.setSafe(true);
                box.setExit(false);
                box.setBridge(false);
            } else if (i==17 || i==34 || i==51 || i==68) {
               
                box.setEntry(true);
                box.setSafe(true);
                box.setExit(false);
                box.setBridge(false);
            } else {
                box.setEntry(false);
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
// Aciones de una pieza
    public ParchisPiece movementPiece(ParchisBoard parchisBoard, ParchisPiece parchisPiece, ParchisDice parchisDice){ 

        if (parchisPiece.getPosition()!= null) { // Se encuentra en recorrido normal
            Integer lastPosition = parchisPiece.getPosition();
            Integer diceNumber = parchisDice.getNumber();
            if (lastPosition == 0 && diceNumber == 5){ // Si la casilla esta en casa
                firstMove(parchisPiece);
                BoxesParchis box = boxesParchisService.findBoxByPosition(parchisPiece.getPosition(),parchisBoard);
                box.addPieceToBox(parchisPiece);
            }else if (lastPosition != 0) { //si ya esta en recorrido
                Integer newPosition = lastPosition + diceNumber;
                if (newPosition > 68){
                    newPosition = newPosition % 68;
                
                }
                BoxesParchis lastBox = boxesParchisService.findBoxByPosition(lastPosition,parchisBoard);
                BoxesParchis newBox = boxesParchisService.findBoxByPosition(newPosition,parchisBoard);
                movement(parchisPiece,lastBox,newBox,parchisBoard);

                if (parchisPiece.getPosition() != null){ //se comprueba por si ha entrado en las casillas finales
                    //Comerse una pieza
                    if (!newBox.getSafe()){
                        eatPiece(parchisPiece);
                    }
                    // Hacer un puente
                    bridge(parchisPiece, lastBox);
                }
                
            }
        }
        if (parchisPiece.getFinishPosition() != null && !parchisPiece.getInGoal() ){ // Se encuentra en posiciones finales
            movementFinish(parchisPiece,parchisBoard,parchisDice);
        } 
        
        return parchisPiece;
    }

    //Movimiento de una pieza
    private void movement(ParchisPiece parchisPiece,BoxesParchis lastBox, BoxesParchis newBox,ParchisBoard parchisBoard) {
        if (!parchisEntry(parchisPiece)){ //si no ha entrado en casillas finales durante el turno (no se si es necesario) 

            List<BoxesParchis> ls = new ArrayList<>();
            if (lastBox.getPositionBoard() > newBox.getPositionBoard()){
                for (int i = lastBox.getPositionBoard()+1; i >= 68; i++){
                    BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                    ls.add(box);
                } 
                for (int i = 1; i<= newBox.getPositionBoard(); i++){
                    BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                    ls.add(box);
                } 
            }else{
                for (int i = lastBox.getPositionBoard()+1; i<=newBox.getPositionBoard(); i++){
                    BoxesParchis box = boxesParchisService.findBoxByPosition(i, parchisBoard);
                    ls.add(box);
                }
            }

            for (BoxesParchis bx : ls){
                if (bx.getBridge()){
                    lastBox.getPiecesInBox().remove(parchisPiece);
                    parchisPiece.setPosition(bx.getPositionBoard()-1);
                    BoxesParchis boxToMove = boxesParchisService.findBoxByPosition(bx.getPositionBoard()-1, parchisBoard);
                    boxToMove.addPieceToBox(parchisPiece);
                    parchisPieceService.save(parchisPiece);
                    boxesParchisService.save(lastBox); 
                    boxesParchisService.save(boxToMove); 
                    break;
                }
                if (bx.equals(newBox)){
                    lastBox.getPiecesInBox().remove(parchisPiece);
                    parchisPiece.setPosition(newBox.getPositionBoard());
                    newBox.addPieceToBox(parchisPiece);
                    parchisPieceService.save(parchisPiece);
                    boxesParchisService.save(lastBox); 
                    boxesParchisService.save(newBox); 
                }
        
                if (checkIsInFinish(parchisPiece,bx)){ // Checkeamos si ha entrado en la casillas finales
                    parchisPiece.setPosition(null);
                    Integer index = ls.indexOf(bx);
                    Integer finishPosition = ls.size()- index;
                    parchisPiece.setFinishPosition(finishPosition);
                    break;
                }   
            } 
        }
    }

    private Boolean checkIsInFinish(ParchisPiece parchisPiece,BoxesParchis bx) {
        if (parchisPiece.getColour() == Colour.RED && bx.getPositionBoard() == 35) {
            return true;
        }
        if (parchisPiece.getColour() == Colour.BLUE && (bx.getPositionBoard() == 18 )){
            return true;
        }
        if (parchisPiece.getColour() == Colour.YELLOW && (bx.getPositionBoard() == 1 )){
            return true;
        }
        if (parchisPiece.getColour() == Colour.GREEN && (bx.getPositionBoard() == 52 )){
            return true;
        }

        return false;
    }
    //Comprobacion si tiene que entrar
    private boolean parchisEntry(ParchisPiece parchisPiece) {
        if ((parchisPiece.getColour() == Colour.RED && parchisPiece.getPosition() == 34) || parchisPiece.getPosition() == null){
            return true;
        }
        if ((parchisPiece.getColour() == Colour.BLUE && parchisPiece.getPosition() == 17)|| parchisPiece.getPosition() == null){
            return true;
        }   
        if ((parchisPiece.getColour() == Colour.YELLOW && parchisPiece.getPosition() == 68) || parchisPiece.getPosition() == null){
            return true;
        }
        if ((parchisPiece.getColour() == Colour.GREEN && parchisPiece.getPosition() == 51) || parchisPiece.getPosition() == null){
            return true;
        } 
        return false;
    }
    //Movimiento en casillas finales
    private void movementFinish(ParchisPiece parchisPiece,ParchisBoard parchisBoard,ParchisDice parchisDice) {
        Integer lastPosition = parchisPiece.getFinishPosition();
        Integer diceNumber = parchisDice.getNumber();
        Integer suma = lastPosition + diceNumber;
        Integer position = parchisBoard.reboteTirada(suma);
        parchisPiece.setFinishPosition(position);
        parchisPiece.setInGoal(true);
        parchisPieceService.save(parchisPiece);
    }

    //Salida de casa
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

    private void eatPiece(ParchisPiece parchisPiece) {

        Integer position = parchisPiece.getPosition();
        BoxesParchis box = boxesParchisService.findBoxByPosition(position, parchisPiece.getParchisBoard());
        if (box.getPiecesInBox().size() > 0){
            List<ParchisPiece> pieces = box.getPiecesInBox();
            Optional<ParchisPiece> pieceToEat = pieces.stream().filter(x->x.getColour() != parchisPiece.getColour()).findFirst();
            if (pieceToEat.isPresent()){
                box.getPiecesInBox().remove(pieceToEat.get());
                boxesParchisService.save(box);
                pieceToEat.get().setPosition(0);
                parchisPieceService.save(pieceToEat.get());
            }  
        } 

    }

    private void bridge(ParchisPiece parchisPiece, BoxesParchis lastBox){
        
        if (lastBox.getBridge() == true){
            lastBox.setBridge(false);
            boxesParchisService.save(lastBox);
        }
        Integer position = parchisPiece.getPosition();
        BoxesParchis box = boxesParchisService.findBoxByPosition(position, parchisPiece.getParchisBoard());
        List<ParchisPiece> pieces = box.getPiecesInBox();
        if (pieces.size() == 2 && pieces.stream().allMatch(x-> x.getColour() == parchisPiece.getColour())){
            box.setBridge(true);
            boxesParchisService.save(box);;
        }else{
            box.setBridge(false);
            boxesParchisService.save(box);
        }

    } 

}
