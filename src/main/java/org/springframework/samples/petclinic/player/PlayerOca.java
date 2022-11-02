package org.springframework.samples.petclinic.player;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.tablero.OcaPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerOca {
    public Integer posicionFicha = 0;
    public OcaPiece ficha;
    public Integer turnoEspera = 0;
    public Integer ordenTirada;


    public Integer tirarDado(){
        return (int)(Math.random()*6 + 1);
    }

    



    
}
