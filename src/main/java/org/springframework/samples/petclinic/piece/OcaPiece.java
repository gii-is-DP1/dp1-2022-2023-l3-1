package org.springframework.samples.petclinic.piece;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.board.OcaBoard;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "oca_pieces")
public class OcaPiece extends BaseEntity {
    
    String color; 
    
    @Range(min = 0, max = 7)
    int xPosition;

    @Range(min = 0, max = 7)
    int yPosition;

    @ManyToOne
    OcaBoard ocaBoard;

    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }

}
