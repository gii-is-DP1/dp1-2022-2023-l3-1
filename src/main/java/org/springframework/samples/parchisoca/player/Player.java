package org.springframework.samples.parchisoca.player;

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
import org.springframework.samples.parchisoca.model.Person;
import org.springframework.samples.parchisoca.piece.OcaPiece;
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
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @Column(unique = true)
    private String email;

    // If it was OneToMany only one player could own the achievement
    @ManyToMany(cascade = CascadeType.ALL)
    @Column(name = "players_achievements")
    private Set<Achievement> achievements;

<<<<<<< HEAD
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<OcaPiece> ocaPiece;
=======
    @ManyToMany
    private List<Player> friends;
>>>>>>> master

}
