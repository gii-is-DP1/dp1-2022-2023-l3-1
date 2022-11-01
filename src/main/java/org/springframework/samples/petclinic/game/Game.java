package org.springframework.samples.petclinic.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game extends BaseEntity {
    
    //private String tipo; //fuera un set -> parchis o oca
    private String creator;
    private String winner;

    @Range(min = 1, max = 4)
    private Integer jugadores;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "game_type_id") //sirve para darle nombre a la columna del script
    GameType gameType;
    

}
