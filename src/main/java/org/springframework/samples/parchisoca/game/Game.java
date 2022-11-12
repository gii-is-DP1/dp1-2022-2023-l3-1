package org.springframework.samples.parchisoca.game;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    
    @OneToOne
    private Player creator;
    
    @Range(min = 2, max = 4)
    private Integer jugadores;
    
    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String code;

    @ManyToMany
    private List<Player> players;
    
    private String winner;

    @Enumerated(EnumType.STRING)
    private Privacity privacity;

    @NotNull
    private boolean inProgress;

    @OneToOne
    private OcaBoard ocaBoard;

    @OneToOne
    private ParchisBoard parchisBoard;

    public static String generatePassword() {

        char[] letters = {'A','B','C','D','E','F','G','H','I'};
        char[] number = {'1','2','3','5','6','7','8','9','0'};

        StringBuilder res = new StringBuilder();
        res.append(letters);
        res.append(number);
        StringBuilder contraseña = new StringBuilder();
        for (int i = 0; i < 6 ; i++ ) {
            int cantidadCarac = res.length();
            int numeroRandom = (int) (Math.random()*cantidadCarac);
            contraseña.append((res.toString().charAt(numeroRandom)));
        } 

        return contraseña.toString();
    }
    
    public void addPlayer (Player player) {
        if (getPlayers() == null) {
            List<Player> ls = new ArrayList<>();
            ls.add(player);
            setPlayers(ls);
        } else {
            List<Player> ls = getPlayers();
            ls.add(player);
            setPlayers(ls);
        }        
    }



    public Integer getNumberOfPlayers(){
        return players.size();
    }

}
