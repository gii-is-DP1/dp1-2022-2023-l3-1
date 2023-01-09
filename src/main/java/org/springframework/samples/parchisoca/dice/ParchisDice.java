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

    private Integer number;

    private final static Integer MAX = 6;

    @ManyToOne
    private ParchisBoard parchisBoard;

    @ManyToOne
    private Player player;

    public void rollDice(){
        this.number = (int) (Math.random() * MAX ) + 1;
    }

}
