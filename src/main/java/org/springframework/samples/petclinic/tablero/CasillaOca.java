package org.springframework.samples.petclinic.tablero;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
public class CasillaOca {

    @Positive
    Integer numeroCasilla;
    private Integer turnosQueSanciona;
    private boolean repetirTirada;
    
    @ManyToOne
    TableroOca board;

}
