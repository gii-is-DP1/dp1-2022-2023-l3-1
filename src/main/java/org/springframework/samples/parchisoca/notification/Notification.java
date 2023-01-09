package org.springframework.samples.parchisoca.notification;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne
    private Player player;

    @Length(min=5, max=50)
    private String text;

    private boolean friendRequest = false;

    private boolean invitation = false;
    
    private String gameCode;

    private Integer sender;
    
}
