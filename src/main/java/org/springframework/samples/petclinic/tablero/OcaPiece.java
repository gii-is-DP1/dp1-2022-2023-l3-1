package org.springframework.samples.petclinic.tablero;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OcaPiece extends BaseEntity {

    public Integer posicionTablero;
    private String color;

    public void Ficha (String color){
        this.color = color;
        this.posicionTablero = 1;
    
    }

}
