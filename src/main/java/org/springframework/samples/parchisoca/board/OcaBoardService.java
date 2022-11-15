package org.springframework.samples.parchisoca.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.OcaDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.oca.BoxesOca;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
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


    @Transactional(readOnly = true)
    public OcaBoard findById(Integer id){
        return ocaBoardRepository.findById(id).get();
    }

    @Transactional
    public void save(OcaBoard ocaBoard) {
        ocaBoardRepository.save(ocaBoard);
    }

   //calculating actual position on board
    public OcaPiece actualPosition(int ocaBoardId, int pieceId){
        OcaBoard ocaBoard = ocaBoardRepository.findById(ocaBoardId).get();
        Integer diceNumber = ocaBoard.getOcaDice().getNumber();
        OcaPiece ocaPiece = ocaPieceService.findOcaPieceById(pieceId);
        Integer suma = ocaPiece.getPosition()+62;
        Integer position = ocaBoard.reboteTirada(suma);
        Integer newPosition = nextPosition(ocaBoard, ocaPiece, position);
        ocaPiece.setPosition(newPosition);
        ocaPieceService.save(ocaPiece);
        return ocaPiece;
    }

    public Integer nextPosition(OcaBoard ocaBoard,OcaPiece ocaPiece,Integer position){
        
        List<BoxesOca> ls = ocaBoard.getBoxes();
        BoxesOca box = ls.get(position-1);
        Integer  newPosition = ocaBoard.action(box,ocaPiece);
        return newPosition;
    }
    
}
