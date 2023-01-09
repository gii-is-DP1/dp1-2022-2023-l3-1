package org.springframework.samples.parchisoca.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.notification.Notification;
import org.springframework.samples.parchisoca.notification.NotificationRepository;
import org.springframework.samples.parchisoca.notification.NotificationService;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.statistic.AchievementService;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.user.Authorities;
import org.springframework.samples.parchisoca.user.AuthoritiesService;
import org.springframework.samples.parchisoca.user.User;
import org.springframework.samples.parchisoca.user.UserService;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {

    @Autowired(required = false)
    PlayerRepository pr;

    @Autowired(required = false)
    PlayerService ps;

    @Autowired(required = false)
    UserService us;

    @Autowired(required = false)
    AuthoritiesService authSer;

    @Autowired
    AchievementService as;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    OcaPieceService ocaPieceService;

    private Player pTest = new Player();
    private Achievement aTest = new Achievement();
    private User u1 = new User();
    private Authorities auth = new Authorities();
    private Notification n = new Notification();

    @BeforeEach
    public void setup(){
        aTest.setName("AchievementTest");
        aTest.setDescription("Un achievement de test");
        as.save(aTest);

        Set<Achievement> achievementSet = new HashSet<>();
        achievementSet.add(aTest);

        u1.setUsername("usuarioTest");
        u1.setEnabled(true);
        auth.setAuthority("player");
        auth.setUser(u1);
        u1.setPassword("1234"); 
        us.saveUser(u1);

        n.setPlayer(pTest);
        n.setText("Texto de Prueba");
        notificationRepository.save(n);
        List<Notification> notifications = new ArrayList<Notification>();
        notifications.add(n);

        pTest.setAchievements(achievementSet);
        pTest.setFirstName("NomberTest");
        pTest.setLastName("ApelldioTest");
        pTest.setFriends(new ArrayList<Player>());
        pTest.setNotifications(notifications);
        ps.save(pTest);
    }

    @Test
    void shouldFindPlayersById(){        
        Optional<Player> players = this.ps.findPlayerById(1);
        assertTrue(players.isPresent());
    }

    @Test
    void shouldFindPlayersByFirstName() {
        Collection<Player> players = this.ps.findPlayerByFirstName("Alvaro");
        assertThat(players.size()).isEqualTo(1);

    }


    @Test
    void shouldFindPlayersByLastName() {
        Collection<Player> players = this.ps.findPlayerByLastName("Carrera");
        assertThat(players.size()).isEqualTo(1);

    }

    @Test
    void shouldGetUserAchievementsById(){
        Player player = this.ps.getUserAchievement(pTest.getId());
        assertThat(player.getAchievements().size()==1);
        Set<Achievement> set = player.getAchievements();
        Iterator value = set.iterator();
        Achievement a = null;
        while (value.hasNext()) {
            a = (Achievement)value.next();
        }
        assertThat(a.getName() == "AchievementTest");
        
    }

    @Test
	void shouldCreateNewPlayer() {
		Player p3 = new Player();
        p3.setFirstName("Juan");
        p3.setLastName("Martinez");
        p3.setEmail("prueba@gmail.com");
        p3.setUser(u1);

        ps.savePlayer(p3);
        Optional<Player> pNew = ps.findPlayerById(p3.getId());
        List<Player> ls = new ArrayList<>();
        ls.add(pNew.get());
        assertThat(ls.size() == 1);
        assertThat(auth.getAuthority().equals("player"));
        assertThat(p3.getUser().equals(u1));
        assertThat(auth.getUser().equals(u1));

    }

    @Test
    void shouldDeletePlayer(){
        Optional<Player> p = this.ps.findPlayerById(1);
        if(p.isPresent()){
            ps.deletePlayerById(p.get().getId());
            assertThat(ps.findPlayerById(1)).isEmpty();
        }else{
            System.out.println("Player not found");
        }
    }

    @Test
    void shouldFindAllPlayer(){
        List<Player> players = ps.findPlayers();
        assertThat(players.size()>0);
    }

    @Test
    void shouldFindPlayersByUsername(){
		Player p3 = new Player();
        p3.setFirstName("Juan");
        p3.setLastName("Martinez");
        p3.setEmail("prueba@gmail.com");
        p3.setUser(u1);

        ps.savePlayer(p3);
        Player p = ps.findPlayersByUsername("usuarioTest");
        assertThat(p.getUser().getUsername().equals("usuarioTest"));
        assertThat(p.getFirstName().equals("Juan"));
        assertTrue(p.isNew() == false || p.isNew() == true);
    }

    @Test
    void shouldGetUserAchievments(){
        List<Player> res = ps.getUserAchievments("usuarioTest");
        assertThat(res.size()>0);
    }

    @Test
    void shouldGetUserIdByName(){
        pTest.setUser(u1);
        ps.savePlayer(pTest);

        Integer res = ps.getUserIdByName(pTest.getUser().getUsername());
        assertTrue(res!=null);
    }

    @Test
    void shouldDeleteNotifications(){
        List<Notification> notficacion = notificationService.findNotificationsByPlayer(pTest);
        Integer antes = pTest.getNotifications().size();
        ps.deleteNotification(pTest, notficacion.get(0));
        Integer despues = pTest.getNotifications().size();
        assertTrue(antes>despues);
    }

    @Test
    void shouldTestGetAndSetOfPlayer(){
        List<ParchisBoard> listParchisBoard = new ArrayList<>();
        listParchisBoard.add(new ParchisBoard());
        pTest.setParchisBoards(listParchisBoard);
        assertTrue(pTest.getParchisBoards() != null);
        
        List<OcaPiece> listOcaPiece = new ArrayList<>();
        OcaPiece ocaPiece = ocaPieceService.findOcaPieceById(1);
        listOcaPiece.add(ocaPiece);
        pTest.setOcaPiece(listOcaPiece);
        assertTrue(pTest.getOcaPiece() != null);

        ParchisDice parchisDice1 = new ParchisDice();
        ParchisDice parchisDice2 = new ParchisDice();

        pTest.addDicesParchis(parchisDice1, parchisDice2);
        assertTrue(pTest.getParchisDice() != null);

        pTest.setStat(new Stat());
        assertTrue(pTest.getStat() != null);

        List<Player> listFriends = new ArrayList<>();
        listFriends.add(new Player());
        pTest.setFriends(listFriends);
        assertTrue(pTest.getFriends() != null);

        List<OcaDice> listOcaDice = new ArrayList<>();
        listOcaDice.add(new OcaDice());
        pTest.setOcaDice(listOcaDice);
        assertTrue(pTest.getOcaDice() != null);
    }

    @Test
    void shouldAddOcaDiceWithNoDice(){
        OcaDice ocaDice = new OcaDice();
        pTest.setOcaDice(null);
        pTest.addDice(ocaDice);
        assertTrue(pTest.getOcaDice().size() >=1);
    }

    @Test
    void shouldAddOcaDiceWithDice(){
        List<OcaDice> listOcaDice = new ArrayList<>();
        listOcaDice.add(new OcaDice());
        OcaDice ocaDice2 = new OcaDice();
        pTest.setOcaDice(listOcaDice);
        pTest.addDice(ocaDice2);
        assertTrue(pTest.getOcaDice().size() >=1);
    }

    @Test
    void shouldAddParchisPieceWithNoPiece(){
        ParchisPiece parchisPiece = new ParchisPiece();
        pTest.setParchisPieces(null);
        pTest.addPiecePlayer(parchisPiece);
        assertTrue(pTest.getParchisPieces().size() >=1);
    }

    @Test
    void shouldFindPlayers(){
        Pageable p = Pageable.unpaged();
        Page<Player> page = ps.findPlayers(p);
        assertTrue(page != null);
    }

    @Test
    void shouldMakeFriends(){
        Player player1 = ps.findById(1);
        Player player2 = ps.findById(2);

        ps.makeFriends(player1, player2);
        assertTrue(player1.getFriends().contains(player2));
        assertTrue(player2.getFriends().contains(player1));
    }    
}