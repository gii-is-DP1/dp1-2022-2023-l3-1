package org.springframework.samples.parchisoca.dice;

import javax.persistence.Entity;

import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OcaDice extends BaseEntity{
    private Integer dice;
    private final Integer MAX = 6;

    public void rollDice() {

        this.dice = (int) (Math.random() * MAX ) + 1;
    }

}
