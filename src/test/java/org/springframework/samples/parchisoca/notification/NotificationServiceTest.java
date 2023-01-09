package org.springframework.samples.parchisoca.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;

import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Autowired
    PlayerService playerService;

    @Autowired
    NotificationRepository notificationRepository;

    Player p = null;

    Notification n = new Notification();
    
    @BeforeEach
    void setUp(){
        p = playerService.findById(1);
        n.setFriendRequest(false);
        n.setGameCode("ASDF");
        n.setInvitation(false);
        n.setPlayer(p);
        n.setSender(1);
        n.setText("Un texto para el Test");

        notificationRepository.save(n);
    }

    @Test
    public void shouldFindById(){
        Notification nTest = notificationService.findById(n.getId());
        assertThat(nTest.getId().equals(n.getId()));
        assertThat(nTest.getGameCode().equals("ASDF"));
        assertThat(nTest.getPlayer().equals(p));
        assertThat(nTest.getSender().equals(1));
        assertThat(nTest.getText().equals("Un texto para el Test"));
        assertFalse(nTest.isFriendRequest());
        assertFalse(nTest.isInvitation());
    }

    @Test
    public void shouldFindNotificationsByPlayer(){
        List<Notification> nTest = notificationService.findNotificationsByPlayer(p);
        assertTrue(nTest.size()>=1);
    }

    @Test
    public void shouldDeleteNotification(){
        List<Notification> notificationsBeforeDelete = notificationService.findAll();

        notificationService.deleteNotification(n);

        List<Notification> notificationsAfterDelete = notificationService.findAll();

        assertThat(notificationsBeforeDelete.size() < notificationsAfterDelete.size());
    }

    @Test
    public void shouldSendNotification(){
        String text = "Mensaje para comprobar el test";
        notificationService.sendNotification(p, text);

        Boolean check = false;

        for(Notification n: p.getNotifications()){
            if(n.getText().equals("Mensaje para comprobar el test")){
                check = true;
            }
        }

        assertTrue(check);
    }

    @Test
    public void shouldSendFriendRequest() {
        String text = "Mensaje para comprobar el Friend Request";
        notificationService.sendFriendRequest(p, text, 2);

        Notification nTest = null;

        for(Notification n: p.getNotifications()){
            if(n.getSender().equals(2)){
                nTest = n;
            }
        }

        assertTrue(nTest.isFriendRequest());
        assertThat(nTest.getPlayer().equals(p));
    }

    @Test
    public void sholudSendGameInvitation(){
        String text = "Mensaje para comprobar el GameInvitation";
        notificationService.sendGameInvitation(p, text, 2, "ASDF");

        Notification nTest = null;

        for(Notification n: p.getNotifications()){
            if(n.getSender().equals(2)){
                nTest = n;
            }
        }

        assertTrue(nTest.isInvitation());
        assertThat(nTest.getText().equals("Mensaje para comprobar el GameInvitation"));
    }

    @Test
    public void shouldInitNotifications(){
        notificationService.initNotifications(p);
        assertThat(p.getNotifications().size() == 0);
    }




}
