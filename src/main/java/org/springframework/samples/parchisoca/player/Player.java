package org.springframework.samples.parchisoca.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.model.Person;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends Person {

    @OneToOne(cascade = CascadeType.ALL)
	private User user;

    @Column(unique = true)
    private String email;

    // If it was OneToMany only one player could own the achievement
    @ManyToMany(cascade = CascadeType.ALL)
    @Column(name = "players_achievements")
    private Set<Achievement> achievements;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<OcaPiece> ocaPiece;

    @OneToMany
    private List<OcaDice> ocaDice;

    @ManyToMany
    private List<Player> friends;

    @ManyToMany
    List<ParchisDice> parchisDice;

    @ManyToMany
    List<ParchisPiece> parchisPieces;

    public void addDice (OcaDice ocaDice) {
        if (getOcaDice() == null) {
            List<OcaDice> ls = new ArrayList<>();
            ls.add(ocaDice);
            setOcaDice(ls);
        } else {
            List<OcaDice> ls = getOcaDice();
            ls.add(ocaDice);
            setOcaDice(ls);
        }
    }

    public void addDicesParchis (ParchisDice dice1, ParchisDice dice2) {
        if(getParchisDice() == null){
            List<ParchisDice> ls = new ArrayList<>();
            ls.add(dice1);
            ls.add(dice2);
            setParchisDice(ls);
        } else {
            List<ParchisDice> ls = getParchisDice();
            ls.add(dice1);
            ls.add(dice2);
            setParchisDice(ls);
        }
    }
}
