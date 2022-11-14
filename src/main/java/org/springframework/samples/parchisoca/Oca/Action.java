package org.springframework.samples.parchisoca.Oca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Component;

@Component
public class Action {

    
    @Autowired
    OcaPieceService ocaPieceService;

    static List<Integer> ocasList = List.of(5,9,14,18,23,27,32,36,41,45,50,54,59);

    public static Integer bridge(Integer postion){
        Integer pos;
        if (postion.equals(6)){
            pos = 12;
        } else{
            pos = 6;
        }
        return pos;
    }

    public static Integer death(Integer position) {
        return 1;
    }

    public static Integer dices(Integer position) {
        Integer pos =0;

        if(position.equals(26)){
            pos = 53;
           
        }else{
            pos = 23;
 
        }
        return pos;
    }

    public static Integer hostal(Integer position) {
        return position;
    }

    public static Integer labyrinth(Integer position) {
        return 30;
    }

    public static Integer oca(Integer position) {
        Integer pos;

        if(position == 59){
            pos = position;
        }
        pos =ocasList.get(ocasList.indexOf(position) +1);
        return pos;
    }

    
    
    
}
