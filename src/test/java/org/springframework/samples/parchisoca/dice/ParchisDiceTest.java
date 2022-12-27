package org.springframework.samples.parchisoca.dice;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisDiceTest {

    @Test 
    void shouldRollDice(){
        ParchisDice dice = new ParchisDice();
        for (int i=0; i<100; i++){
            dice.rollDice();
            Integer p = dice.getNumber();
            assertTrue(p>=1 && p<=6);
        }
    }
    
}
