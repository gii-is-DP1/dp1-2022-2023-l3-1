package org.springframework.samples.parchisoca.player;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired(required = false)
    PlayerRepository pr;

    @Test
    void shouldFindAll(){
        List<Player> players = pr.findAll();
        assertNotNull(players);
        assertFalse(players.isEmpty());
    }

    @Test
    void shoudFindPlayerByUsername(){
        Player player = pr.getPlayerById(2);
        assertEquals(player, pr.getByUsername("pajaro"));
    }

    @Test
    void shouldFailFindPlayerByUsername(){
        Player player = pr.getByUsername("ElNano");
        assertNull(player);
    }
}
