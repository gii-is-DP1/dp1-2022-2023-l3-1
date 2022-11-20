package org.springframework.samples.parchisoca.dice;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OcaDice extends BaseEntity{
    private final Integer MAX = 6;
    
    private Integer number;

    @ManyToOne
    private Player player;

    @ManyToOne
    private OcaBoard ocaBoard;

    public void rollDice() {
        this.number = (int) (Math.random() * MAX ) + 1;
    }

}
