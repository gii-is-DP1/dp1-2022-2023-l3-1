package org.springframework.samples.parchisoca.game;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class  GameTest {
    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gr;

    @Autowired
    PlayerService playerService;


    @Test
    public void testNewPlayer(){
        testConstraints();
    }

    private void testConstraints() {

        Game g = new Game();
        g.setId(null);

        assertThrows(ConstraintViolationException.class,() -> gr.save(g),
        "You are not constraining "+ "players can not be null");

        Game g2 = new Game();
        g2.setId(9);
        g2.setJugadores(5);
        assertThrows(ConstraintViolationException.class,() -> gr.save(g2),
        "You are not constraining "+ "players can not be more than 4");

        Game g3 = new Game();
        g2.setId(5);
        g2.setJugadores(-1);
        assertThrows(ConstraintViolationException.class,() -> gr.save(g3),
        "You are not constraining "+ "players can not be less than 1");
    }

    @Test
    void shouldGeneratePassword(){
        String password = Game.generatePassword();
        assertTrue(password != null);
    }



}
