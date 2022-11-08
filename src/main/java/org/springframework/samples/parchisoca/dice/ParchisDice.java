package org.springframework.samples.parchisoca.dice;


import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.parchisoca.model.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class ParchisDice extends BaseEntity {

    private Integer dice1;
    private Integer dice2;
    private final Integer MAX = 6;

    public void rollDices(){
        this.dice1 = (int) (Math.random() * MAX ) + 1;
        this.dice2 = (int) (Math.random() * MAX ) + 1;
    }

}
