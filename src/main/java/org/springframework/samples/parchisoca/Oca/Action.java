package org.springframework.samples.parchisoca.Oca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Component;

@Component
public class Action {

    

    @Autowired
    OcaPieceService ocaPieceService;

    public static Integer bridge(Integer postion,OcaBoard ocaBoard){
        OcaDice dice = ocaBoard.getOcaDice();
        Integer nDice = 0;
        Integer pos;
        if (postion.equals(6)){
            nDice = 12;
            dice.rollDice();
            pos = dice.getNumber() + nDice;
        } else{
            nDice = 6;
            dice.rollDice();
            pos = dice.getNumber() + nDice;
        }
        return pos;
    }

    public static Integer death(Integer position) {
        return 1;
    }

    public static Integer dices(Integer position,OcaBoard ocaBoard) {
        Integer pos =0;
        OcaDice dice = ocaBoard.getOcaDice();

        int nDice;
        if(position.equals(26)){
            nDice = 53;
            dice.rollDice();
            pos =dice.getNumber() +nDice;
        }else{
            nDice = 23;
            dice.rollDice();
            pos =dice.getNumber() +nDice;
            
        }
        return pos;
    }

    
    
}
