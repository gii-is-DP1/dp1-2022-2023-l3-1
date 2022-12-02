package org.springframework.samples.parchisoca.dice;

import lombok.Getter;
import lombok.Setter;

import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ParchisDice extends BaseEntity {

    private Integer dice;

    private final static Integer MAX = 6;

    @ManyToOne
    private ParchisBoard parchisBoard;

    @ManyToOne
    private Player player;

    public static Integer rollDice1(){
        Integer res;
        Integer dice1 = (int) (Math.random() * MAX ) + 1;
        res = dice1;
        return res;
    }

    // preguntar como hacer para obtener la suma de los dados
    // si cuando se hace la funcion de abajo, se vuelve a tirar el dado o
    // coge los mismos valores
    // public Integer sumDices (){
    //     Integer res;
    //     res = rollDice1() + rollDice2();
    //     return res;
    // }

}
