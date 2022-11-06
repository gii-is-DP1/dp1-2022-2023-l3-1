package org.springframework.samples.parchisoca.player;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.parchisoca.model.Person;
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

    @OneToMany
    @Column(name = "players_achievements")
    private Set<Achievement> achievements;

}
