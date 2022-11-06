package org.springframework.samples.parchisoca.game;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    
    private String creator;
    private String winner;

    @Range(min = 1, max = 4)
    private Integer players;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "game_type_id")
    GameType gameType;

    // @OneToMany
    // private Set<Player> players; //relación para poder meter jugadores dentro de una partida
    

}
