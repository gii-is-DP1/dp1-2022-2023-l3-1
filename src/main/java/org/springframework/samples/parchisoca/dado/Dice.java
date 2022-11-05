package org.springframework.samples.parchisoca.dado;

import javax.persistence.Entity;

import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Dice extends BaseEntity {

    private final Integer MAX = 6;

    public int roll() {

        return (int) (Math.random() * MAX ) + 1;
    }
}
