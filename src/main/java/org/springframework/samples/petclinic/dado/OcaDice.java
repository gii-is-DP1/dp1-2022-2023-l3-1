package org.springframework.samples.petclinic.dado;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OcaDice extends BaseEntity {

    private Integer dice;
    private final Integer MAX = 6;

    public void rollDice() {

        this.dice = (int) (Math.random() * MAX ) + 1;
    }
}
