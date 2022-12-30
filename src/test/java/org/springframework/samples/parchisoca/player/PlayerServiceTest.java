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
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.statistic.AchievementService;
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

    private Player pTest = new Player();
    private Achievement aTest = new Achievement();

    @BeforeEach
    public void setup(){
        aTest.setName("AchievementTest");
        aTest.setDescription("Un achievement de test");
        as.save(aTest);

        Set<Achievement> achievementSet = new HashSet<>();
        achievementSet.add(aTest);

        pTest.setAchievements(achievementSet);
        pTest.setFirstName("NomberTest");
        pTest.setLastName("ApelldioTest");
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

        User u1 = new User();
        Authorities auth = new Authorities();
        u1.setUsername("usuarioTest");
        u1.setEnabled(true);
        auth.setAuthority("player");
        auth.setUser(u1);
        u1.setPassword("1234"); 
        
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

    
}