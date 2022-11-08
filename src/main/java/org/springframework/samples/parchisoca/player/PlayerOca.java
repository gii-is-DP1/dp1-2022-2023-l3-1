package org.springframework.samples.parchisoca.player;

import javax.persistence.Entity;

import org.springframework.samples.parchisoca.model.BaseEntity;
// import org.springframework.samples.parchisoca.tablero.OcaPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerOca extends BaseEntity {
    
    public Integer piecePosition = 0;

    // @OneToOne
    // public OcaPiece ficha;

    public Integer waitingTurn = 0;
    public Integer rollOrder;


    public Integer rollDice(){
        return (int)(Math.random()*6 + 1);
    }

    



    
}
