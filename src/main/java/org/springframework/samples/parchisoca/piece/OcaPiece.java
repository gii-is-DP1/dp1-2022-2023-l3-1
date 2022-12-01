package org.springframework.samples.parchisoca.piece;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

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
    
    @NotNull
    int xPosition = 100;

    
    @NotNull
    int yPosition = 570;

    @ManyToOne
    OcaBoard ocaBoard;

    Integer position=1;

    Integer penalizationTurn = 0;

    @ManyToOne
    private Player player;

    public int getXPosition(){
        return xPosition;
    }
    public int getYPosition(){
        return yPosition;
    }
}
