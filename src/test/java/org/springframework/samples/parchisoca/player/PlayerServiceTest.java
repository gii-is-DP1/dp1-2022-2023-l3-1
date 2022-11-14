package org.springframework.samples.parchisoca.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.user.User;
import org.springframework.samples.parchisoca.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {

    @Autowired(required = false)
    PlayerRepository pr;

    @Autowired(required = false)
    PlayerService ps;

    @Autowired(required = false)
    UserService us;

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
	void shouldCreateNewPlayer() {

        User u1 = new User();
        u1.setUsername("usuarioTest");
        u1.setEnabled(true);
        u1.setPassword("1234");
        u1.setAuthorities(null);   
        
		Player p3 = new Player();
        p3.setFirstName("Juan");
        p3.setLastName("Martinez");
        p3.setEmail("prueba@gmail.com");
        p3.setUser(u1);

        ps.savePlayer(p3);
        Optional<Player> pNew = ps.findPlayerById(p3.getId());
        List<Player> ls = new ArrayList<>();
        ls.add(pNew.get());
        assertThat(ls.size()==1);

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
