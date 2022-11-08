package org.springframework.samples.parchisoca.game;


import java.util.List;
import java.util.Set;

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
    private Integer jugadores;

    @NotNull
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @OneToMany
    private List<Player> players; //relación para poder meter jugadores dentro de una partida
    
    @NotNull
    private String code;
    public static String generatePassword(){

        char[] letters = {'A','B','C','D','E','F','G','H','I'};
        char[] number = {'1','2','3','5','6','7','8','9','0'};

        StringBuilder res = new StringBuilder();
        res.append(letters);
        res.append(number);
        StringBuilder contraseña = new StringBuilder();
        for (int i = 0; i < 6 ; i++ ){
            int cantidadCarac = res.length();
            int numeroRandom = (int) (Math.random()*cantidadCarac);
            contraseña.append((res.toString().charAt(numeroRandom)));
        } 

        return contraseña.toString();


    }
    
    
}
