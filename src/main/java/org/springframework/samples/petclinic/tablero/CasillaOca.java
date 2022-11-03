package org.springframework.samples.petclinic.tablero;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
public class CasillaOca extends BaseEntity{

    @Positive
    Integer numeroCasilla;
    private Integer turnosQueSanciona;
    private boolean repetirTirada;
    
    @ManyToOne
    TableroOca board;

}