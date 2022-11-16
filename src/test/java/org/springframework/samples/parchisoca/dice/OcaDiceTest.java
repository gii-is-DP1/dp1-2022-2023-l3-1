package org.springframework.samples.parchisoca.dice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaDiceTest {

    @Test 
    void shouldRollDice(){
        OcaDice dice = new OcaDice();
        for (int i=0; i<100; i++){
            dice.rollDice();
            Integer d = dice.getNumber();
            assertTrue(d>=1 && d<=6);
        }
    }
}
