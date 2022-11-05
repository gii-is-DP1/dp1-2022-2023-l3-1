package org.springframework.samples.petclinic.dado;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


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
