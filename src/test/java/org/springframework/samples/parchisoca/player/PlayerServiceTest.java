package org.springframework.samples.parchisoca.player;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {

    @Autowired(required = false)
    PlayerRepository pr;

    @Autowired(required = false)
    PlayerService ps;

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
	@Transactional
	void shouldCreateNewPlayer() {

        User u1 = new User();
        u1.setUsername("usuario6");
        u1.setEnabled(true);
        u1.setPassword("1234");

        
        
		Player p2 = new Player();
        p2.setId(9);
        p2.setFirstName("Juan");
        p2.setLastName("Martinez");
        p2.setEmail("prueba@gmail.com");
        p2.setUser(u1);

		ps.savePlayer(p2);

		Optional<Player> player = this.ps.findPlayerById(9);
		assertThat(player.isPresent());
	}

    
}
