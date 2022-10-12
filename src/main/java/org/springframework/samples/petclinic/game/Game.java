package org.springframework.samples.petclinic.game;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game extends BaseEntity {
    
    private String tipo;
    private String creator;
    private String winner;

}
