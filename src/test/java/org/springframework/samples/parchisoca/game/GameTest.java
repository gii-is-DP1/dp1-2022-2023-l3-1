package org.springframework.samples.parchisoca.game;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class  GameTest {
    @Autowired
    GameService gs;

    @Autowired
    GameRepository gr;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

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
}
