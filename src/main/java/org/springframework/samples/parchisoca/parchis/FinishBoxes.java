package org.springframework.samples.parchisoca.parchis;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FinishBoxes extends BaseEntity {
    
    @ManyToOne
    private ParchisBoard parchisBoard;

    private Integer position;

    private Boolean goal  = false;
}
