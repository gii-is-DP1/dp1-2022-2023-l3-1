package org.springframework.samples.parchisoca.dice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



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