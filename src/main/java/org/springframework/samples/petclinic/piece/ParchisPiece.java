package org.springframework.samples.petclinic.piece;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.board.ParchisBoard;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "parchis_pieces")
public class ParchisPiece extends BaseEntity {

    String color;

    @Range(min=0,max=18)
    int xPosition;

    @Range(min=0,max=18)
    int yPosition;

    @ManyToOne
    ParchisBoard parchisBoard;

    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
    
}
