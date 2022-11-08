package org.springframework.samples.parchisoca.piece;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "parchis_pieces")
public class ParchisPiece extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Colour colour;

    @Range(min=0,max=18)
    @NotNull
    private int xPosition;

    @Range(min=0,max=18)
    @NotNull
    private int yPosition;

    @ManyToOne
    private ParchisBoard parchisBoard;

    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
    
}

