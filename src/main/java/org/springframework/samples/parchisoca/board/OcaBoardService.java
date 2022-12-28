package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.OcaDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.oca.BoxesOca;
import org.springframework.samples.parchisoca.oca.BoxesOcaService;
import org.springframework.samples.parchisoca.oca.SpecialBoxesOca;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaBoardService {
    
    @Autowired
    OcaBoardRepository ocaBoardRepository; 
    
    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    OcaDiceService ocaDiceService;

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    BoxesOcaService boxesOcaService;


    @Transactional(readOnly = true)
    public OcaBoard findById(Integer id){
        return ocaBoardRepository.findById(id).get();
    }

    @Transactional
    public void save(OcaBoard ocaBoard) {
        ocaBoardRepository.save(ocaBoard);
    }

    // Calculates the actual position on board
    public OcaPiece actualPosition(OcaBoard ocaBoard, OcaPiece piece){
        Player player = piece.getPlayer();
        OcaDice dice = ocaBoardRepository.getOcaDiceByPlayer(player, ocaBoard);
        Integer diceNumber = dice.getNumber();
        Integer suma = piece.getPosition() + diceNumber;
        Integer position = ocaBoard.bounceBack(suma);
        Integer newPosition = nextPosition(ocaBoard, piece, position);
        piece.setPosition(newPosition);
        List<BoxesOca> ls = ocaBoard.getBoxes();
        BoxesOca boxOca = ls.get(newPosition-1);
        piece.setXPosition(boxOca.getXPosition());
        piece.setYPosition(boxOca.getYPosition());
        ocaPieceService.save(piece);
        return piece;
    }

    @Transactional
    public Integer nextPosition(OcaBoard ocaBoard,OcaPiece ocaPiece,Integer position){
        List<BoxesOca> ls = ocaBoard.getBoxes();
        BoxesOca box = ls.get(position-1);
        Integer  newPosition = ocaBoard.action(box,ocaPiece);
        ocaBoardRepository.save(ocaBoard);
        return newPosition;
    }
    
    //Inititate board with piece and dice
    @Transactional
    public OcaBoard initBoard(Game game) {
        OcaBoard ocaBoard = new OcaBoard();
        List<Colour> colours = List.of(Colour.RED,Colour.BLUE,Colour.YELLOW,Colour.GREEN);
        ocaBoardRepository.save(ocaBoard);
        List<Player> players = game.getPlayers();
        int i = 0;
        for(Player p : players){
            OcaPiece piece = new OcaPiece();
            piece.setPlayer(p);
            Colour color = colours.get(i);
            piece.setColour(color);
            piece.setOcaBoard(ocaBoard); 
            ocaPieceService.save(piece);
            OcaDice dice = new OcaDice();
            ocaDiceService.save(dice);
            dice.setOcaBoard(ocaBoard);
            p.addDice(dice);
            dice.setPlayer(p);
            playerService.save(p);
            ocaDiceService.save(dice);
            i++;
        }
        
        List<BoxesOca> ls = initBoxes(ocaBoard);
        ocaBoard.setBoxes(ls);
        ocaBoardRepository.save(ocaBoard);
        
        return ocaBoard;

    }

    // Initiates the boxes of a board
    @Transactional
    public List<BoxesOca>  initBoxes(OcaBoard ocaBoard) {
        List<BoxesOca> boxesOca = new ArrayList<BoxesOca>(63);
        for (int i=1; i<=63; i++) {
            BoxesOca box = new BoxesOca();
            if(i==5 || i==9 || i==14 || i==18 || i== 23 || i==27 
            || i== 32 || i==36 || i==41 || i==45 || i==50 || i==54 || i== 59) {
                box.setSpecialBoxOca(SpecialBoxesOca.OCA);
            } else if (i==6 || i==12) {
                box.setSpecialBoxOca(SpecialBoxesOca.BRIDGE);
            } else if (i==26 || i==53) {
                box.setSpecialBoxOca(SpecialBoxesOca.DICES);
            } else if (i==19) {
                box.setSpecialBoxOca(SpecialBoxesOca.HOSTAL);
            } else if (i==31) {
                box.setSpecialBoxOca(SpecialBoxesOca.WELL);
            } else if (i==42) {
                box.setSpecialBoxOca(SpecialBoxesOca.LABYRINTH);
            } else if (i==58) {
                box.setSpecialBoxOca(SpecialBoxesOca.DEATH);
            } else if (i==63) {
                box.setSpecialBoxOca(SpecialBoxesOca.GOAL);
            }else if (i==52){
                box.setSpecialBoxOca(SpecialBoxesOca.PRISON);
            } else {
                box.setSpecialBoxOca(SpecialBoxesOca.NORMAL);
            }
            box.setPositionBoard(i);
            box.setOcaBoard(ocaBoard);
            boxesOca.add(box);
            boxesOcaService.save(box);
            box = SetOcaBoxPositionPx(box,i);
            boxesOcaService.save(box);
        }
        return boxesOca;
    }

    @Transactional
    private BoxesOca SetOcaBoxPositionPx(BoxesOca res, int position) {
        
        if (position == 1){
            res.setXPosition(100);
            res.setYPosition(580);
            return res;  
        }
             
        if(position >= 2 && position <= 8 ){
            res.setXPosition(225);
            res.setYPosition(580);
            res.setXPosition(res.getXPosition() + ((position-2) * 54 ));
            return res;
        }
        if (position >= 9 && position <= 18){
            res.setXPosition(590);
            res.setYPosition(550);
            res.setYPosition(res.getYPosition() - ((position-9) * 54 ));
        }
        if (position >= 19 && position <= 28){
            res.setXPosition(550);
            res.setYPosition(25);
            res.setXPosition(res.getXPosition() - ((position-19) * 54 ));
        }
        if (position >= 29 && position <= 36){
            res.setXPosition(30);
            res.setYPosition(80);
            res.setYPosition(res.getYPosition() + ((position-29) * 54 ));
        }
        if (position >= 37 && position <= 44){
            res.setXPosition(80);
            res.setYPosition(500);
            res.setXPosition(res.getXPosition() + ((position-37) * 54 ));
        }
        if (position >= 45 && position <= 50){
            res.setXPosition(500);
            res.setYPosition(450);
            res.setYPosition(res.getYPosition() - ((position-45) * 54 ));
        }
        if (position >= 51 && position <= 56){
            res.setXPosition(450);
            res.setYPosition(115);
            res.setXPosition(res.getXPosition() - ((position-51) * 54 ));
        }  
        if (position >= 57 && position <= 60){
            res.setXPosition(120);
            res.setYPosition(185);
            res.setYPosition(res.getYPosition() + ((position-57) * 54 ));
        }   
        if (position >= 61 && position <= 63){
            res.setXPosition(180);
            res.setYPosition(405);
            res.setXPosition(res.getXPosition() + ((position-61) * 54 ));
        }   
        return res;
    }
    
    @Transactional
    public OcaPiece nextTurn(OcaBoard ocaBoard){

        List <OcaPiece> pieces  = ocaBoard.getPieces();
        Integer turn = ocaBoard.getTurn();
        if (turn == pieces.size()-1){
            ocaBoard.setTurn(0);
        }else{
            turn +=1;
            ocaBoard.setTurn(turn);
        }
        return pieces.get(turn);

    }

    @Transactional(readOnly = true)
    public OcaDice findOcaDiceByPlayerAndBoard(Player player, OcaBoard ocaBoard) {
        OcaDice result = ocaBoardRepository.getOcaDiceByPlayer(player, ocaBoard);
        return result;
    }
    
    @Transactional(readOnly = true)
    public Boolean isActualPlayer(Player piecePlayer){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

        return currentPlayer.equals(piecePlayer);
        
    }
}
