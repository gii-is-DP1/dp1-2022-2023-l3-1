package org.springframework.samples.petclinic.tablero;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcaPiece {

    public Integer posicionTablero;
    private String color;

    public void Ficha (String color){
        this.color = color;
        this.posicionTablero = 1;
    
    }

}
