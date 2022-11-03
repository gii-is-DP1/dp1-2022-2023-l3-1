package org.springframework.samples.petclinic.tablero;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OcaPiece extends BaseEntity {


    String color;

    int xPosition;

    int yPosition;

    @ManyToOne
    TableroOca tablero;

    // public Integer posicionTablero;
    // private String color;

    // public void Ficha (String color){
    //     this.color = color;
    //     this.posicionTablero = 1;
    
    // }

}
