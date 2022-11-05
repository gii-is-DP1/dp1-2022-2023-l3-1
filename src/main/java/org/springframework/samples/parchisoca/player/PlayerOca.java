package org.springframework.samples.parchisoca.player;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.samples.parchisoca.model.BaseEntity;
// import org.springframework.samples.parchisoca.tablero.OcaPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerOca extends BaseEntity {
    public Integer posicionFicha = 0;

    // @OneToOne
    // public OcaPiece ficha;

    public Integer turnoEspera = 0;
    public Integer ordenTirada;


    public Integer tirarDado(){
        return (int)(Math.random()*6 + 1);
    }

    



    
}
