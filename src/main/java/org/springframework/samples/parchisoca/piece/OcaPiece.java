package org.springframework.samples.parchisoca.piece;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.oca.SpecialBoxesOca;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "oca_pieces")
public class OcaPiece extends BaseEntity {
    


    @NotNull
    @Enumerated(EnumType.STRING)
    private Colour colour;
    
    @Range(min = 0, max = 7)
    @NotNull
    int xPosition;

    @Range(min = 0, max = 7)
    @NotNull
    int yPosition;

    @ManyToOne
    OcaBoard ocaBoard;

    Integer position=1;

    Integer penalizationTurn = 0;

    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }

}
