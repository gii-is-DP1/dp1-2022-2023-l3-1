package org.springframework.samples.parchisoca.dice;

import lombok.Getter;
import lombok.Setter;

import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@Getter
@Setter
public class ParchisDice extends BaseEntity {

    private Integer dice1;
    private Integer dice2;
    private final static Integer MAX = 6;

    @ManyToMany
    ParchisBoard parchisBoard;

    @ManyToMany
    Player player;


    // lo hacemos asi para poder obtener el valor de cada uno
    // por ejemplo, cuando vas a salir, con sacar un 5 en uno de los dados
    // ya puedes salir de tu casa
    public static Integer rollDice1(){
        Integer res;
        Integer dice1 = (int) (Math.random() * MAX ) + 1;
        res = dice1;
        return res;
    }

    public static Integer rollDice2() {
        Integer res = 0;
        Integer dice2 = (int) (Math.random() * MAX ) + 1;
        res = dice2;
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
