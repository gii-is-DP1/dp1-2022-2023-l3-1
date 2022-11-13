package org.springframework.samples.parchisoca.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaBoardService {
    
    @Autowired
    OcaBoardRepository ocaBoardRepository; 
    
    @Autowired
    OcaPieceService ocaPieceService;


    @Transactional(readOnly = true)
    public OcaBoard findById(Integer id){
        return ocaBoardRepository.findById(id).get();
    }

    @Transactional
    public void save(OcaBoard ocaBoard) {
        ocaBoardRepository.save(ocaBoard);
    }

    @Transactional
    public Integer rollDice(OcaDice dice) {
        dice.rollDice();
        Integer number = dice.getNumber();
        return number;
    }

    
}
