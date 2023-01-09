package org.springframework.samples.parchisoca.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;

import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ConstraintViolationException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class NotificationTest {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PlayerService playerService;

    Player p = null;

    @BeforeEach
    void setUp(){
        p = playerService.findById(1);
    }

    @Test
    public void testConstraint(){
        Notification n = new Notification();
        n.setFriendRequest(false);
        n.setGameCode("ASDF");
        n.setInvitation(false);
        n.setPlayer(p);
        n.setSender(1);
        n.setText("min");

        assertThrows(ConstraintViolationException.class,() -> notificationRepository.save(n),
        "You are not constraining "+ "text can not be less than 5 or greater than 50");

    }


    
}
