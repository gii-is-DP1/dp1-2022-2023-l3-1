package org.springframework.samples.parchisoca.Oca;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxesOca extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SpecialBoxesOca specialBoxOca;

    private Integer positionBoard;
    
    private Integer xPosition; 
    
    private Integer yPosition;

    @ManyToOne(cascade = CascadeType.ALL)
    private OcaBoard ocaBoard;
}
