package org.springframework.samples.parchisoca.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.springframework.samples.parchisoca.model.BaseEntity;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_types")
public class GameType extends BaseEntity{

    @Size(min = 3, max = 50)
    @Column(nullable=false)
    String name;
    
}
