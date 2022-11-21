package org.springframework.samples.parchisoca.oca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;

public class Action {

    @Autowired
    OcaPieceService ocaPieceService;

    static List<Integer> ocasList = List.of(5,9,14,18,23,27,32,36,41,45,50,54,59);

    public Integer bridge(Integer postion){
        Integer pos;
        if (postion.equals(6)) {
            pos = 12;
        } else {
            pos = 6;
        }
        return pos;
    }

    public Integer death(Integer position) {
        return 1;
    }

    public Integer dices(Integer position) {
        Integer pos =0;

        if (position.equals(26)) {
            pos = 53;
           
        } else {
            pos = 23;
        }
        return pos;
    }

    public Integer hostal(Integer position,OcaPiece ocaPiece) {
        ocaPiece.setPenalizationTurn(2);
        return position;
    }

    public Integer labyrinth(Integer position) {
        return 30;
    }

    public Integer oca(Integer position) {
        Integer pos;
        if (position == 59) {
            pos = position;
        } else {
            pos =ocasList.get(ocasList.indexOf(position) +1);
        }
        return pos;
    }

    public Integer prison(Integer position, OcaPiece ocaPiece) {
        ocaPiece.setPenalizationTurn(3);
        return position;
    }

    public Integer well(Integer position, OcaPiece ocaPiece) {
        ocaPiece.setPenalizationTurn(4);
        return position;
    }

    public Integer goal(Integer position) {
        return position;
    }
  
}
