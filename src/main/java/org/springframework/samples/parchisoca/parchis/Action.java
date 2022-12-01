package org.springframework.samples.parchisoca.parchis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;


public class Action {

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    ParchisBoardService parchisBoardService;


    static List<Integer> safeBoxesList = List.of(5, 12, 17, 22,29,34,39,46,51,56,63,68);
    static List<Integer> exitBoxesList = List.of(5,22,39,56);

    public Integer exit(ParchisPiece piece ) {
        Integer pos = 0;

        if(ParchisDice.rollDice1() == 5 || ParchisDice.rollDice2() == 5) {
            if(piece.colour.equals(Colour.YELLOW)){
                pos = 5;
                piece.setPosition(pos);
            } else if (piece.colour.equals(Colour.BLUE)) {
                pos = 22;
                piece.setPosition(pos);
            } else if (piece.colour.equals(Colour.RED)){
                pos = 39;
                piece.setPosition(pos);
            } else  {
                pos = 56;
                piece.setPosition(pos);
            }
        }
        return pos;
    }

    //para saber en que posicion estamos -> antes de implementar el clik en patanlla
    //podemos añadir botones que nos indiquen en que posicion del tablero estamos
    //para que el usuario pueda identificar que ficha esta moviendo.  

    public void  safe(ParchisPiece piece) {
        for(int i=0; i<safeBoxesList.size(); i++){
            if(piece.getPosition().equals(safeBoxesList.get(i))){
                piece.setSafeBox(true);
            } else{
                piece.setSafeBox(false);
            }
            i++;
        }  
    }

    public void remove(ParchisPiece piece) {
        
    }

    // public void bridge(ParchisPiece piece1, ParchisPiece piece2) {
    //     if (piece1.getPosition().equals(piece2.getPosition()) 
    //         && (piece1.getColour().equals(piece2.getColour()))){
    //         piece1.setSafeBox(true);
    //         piece2.setSafeBox(true);
    //     } else if (parchisBoardService.nextTurn(null, null)){
            
    //     }
    // }


}
