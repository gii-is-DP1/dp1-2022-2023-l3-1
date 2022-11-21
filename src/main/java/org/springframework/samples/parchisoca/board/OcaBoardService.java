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
        Integer position = ocaBoard.reboteTirada(suma);
        Integer newPosition = nextPosition(ocaBoard, piece, position);
        piece.setPosition(newPosition);
        ocaPieceService.save(piece);
        return piece;
    }

    public Integer nextPosition(OcaBoard ocaBoard,OcaPiece ocaPiece,Integer position){
        List<BoxesOca> ls = ocaBoard.getBoxes();
        BoxesOca box = ls.get(position-1);
        Integer  newPosition = ocaBoard.action(box,ocaPiece);
        return newPosition;
    }
    
     //Inititate board with piece and dice
     public OcaBoard initBoard(Game game){
        OcaBoard oca = new OcaBoard();
        ocaBoardRepository.save(oca);
        List<Player> players = game.getPlayers();
        for(Player p : players){
            OcaPiece piece = new OcaPiece();
            piece.setPlayer(p);
            piece.setColour(Colour.RED);
            piece.setOcaBoard(oca); 
            ocaPieceService.save(piece);
            OcaDice dice = new OcaDice();
            ocaDiceService.save(dice);
            dice.setOcaBoard(oca);
            p.addDice(dice);
            dice.setPlayer(p);
            playerService.save(p);
            ocaDiceService.save(dice);
        }
        
        List<BoxesOca> ls = initBoxes();
        oca.setBoxes(ls);
        ocaBoardRepository.save(oca);
        
        return oca;

    }

    // Initiates the boxes of a board
    public List<BoxesOca>  initBoxes() {
        List<BoxesOca> normalBoxesOca = new ArrayList<BoxesOca>(63);
        for (int i=1; i<=63; i++) {
            BoxesOca res = new BoxesOca();
            if(i==5 || i==9 || i==14 || i==18 || i== 23 || i==27 
            || i== 32 || i==36 || i==41 || i==45 || i==50 || i==54 || i== 59) {
                res.setSpecialBoxOca(SpecialBoxesOca.OCA);
            } else if (i==6 || i==12) {
                res.setSpecialBoxOca(SpecialBoxesOca.BRIDGE);
            } else if (i==26 || i==53) {
                res.setSpecialBoxOca(SpecialBoxesOca.DICES);
            } else if (i==19) {
                res.setSpecialBoxOca(SpecialBoxesOca.HOSTAL);
            } else if (i==31) {
                res.setSpecialBoxOca(SpecialBoxesOca.WELL);
            } else if (i==42) {
                res.setSpecialBoxOca(SpecialBoxesOca.LABYRINTH);
            } else if (i==58) {
                res.setSpecialBoxOca(SpecialBoxesOca.DEATH);
            } else if (i==63) {
                res.setSpecialBoxOca(SpecialBoxesOca.GOAL);
            } else {
                res.setSpecialBoxOca(SpecialBoxesOca.NORMAL);
            }
            res.setPositionBoard(i);
            normalBoxesOca.add(res);
            boxesOcaService.save(res);
        }
        return normalBoxesOca;
    }

    public OcaPiece nextTurn(OcaBoard ocaBoard, Integer turn){

        List <OcaPiece> pieces  = ocaBoard.getPieces();
        if (turn == pieces.size()-1){
            ocaBoard.setTurn(0);;
        }else{
            turn +=1;
            ocaBoard.setTurn(turn);
        }
        return pieces.get(turn);

    }

    public OcaDice findOcaDiceByPlayer(Player player, OcaBoard ocaBoard) {
        OcaDice result = ocaBoardRepository.getOcaDiceByPlayer(player, ocaBoard);
        return result;
    }
    
    public Boolean isActualPlayer(Player piecePlayer){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.getById(id);

        return currentPlayer.equals(piecePlayer);
        
    }
}
