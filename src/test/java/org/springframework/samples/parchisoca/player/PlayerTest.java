package org.springframework.samples.parchisoca.player;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.security.Provider.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerTest {

    @Autowired(required = false)
    PlayerRepository pr;

    @Autowired(required = false)
    PlayerService ps;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

    @Test
    public void testNewPlayer(){
        testConstraints();
    }

 

    void testConstraints(){
        
        Player p = new Player();
        p.setId(null);

        assertFalse(validator.validate(p).isEmpty(), "It should not allow empty player id");


        Player p2 = new Player();
        p2.setId(9);
        p2.setFirstName(null);
        p2.setLastName("Martinez");
        p2.setEmail("prueba@gmail.com");

        assertFalse(validator.validate(p2).isEmpty(), "It should not allow empty player firstName");


        Player p3 = new Player();
        p3.setId(8);
        p3.setFirstName("Juan");
        p3.setLastName(null);
        p3.setEmail("prueba@gmail.com");
        
        assertFalse(validator.validate(p3).isEmpty(), "It should not allow empty player lastName");

        

        

        


    }




}

