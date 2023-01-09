package org.springframework.samples.parchisoca.dice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaDiceTest {

    @Autowired
    OcaDiceService ocaDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    OcaBoardService ocaBoardService;

    Player playerTest = new Player();
    OcaBoard ocaBoardTest = new OcaBoard();

    OcaDice ocaDice = new OcaDice();

    @BeforeEach
    public void setUp() {

        ocaDice.setNumber(1);
        ocaDice.setOcaBoard(ocaBoardTest);
        ocaDice.setPlayer(playerTest);

        ocaDiceService.save(ocaDice);
    }

    @Test 
    public void shouldRollDice() {
        for (int i=0; i<100; i++){
            ocaDice.rollDice();
            Integer d = ocaDice.getNumber();
            assertTrue(d>=1 && d<=6);
        }
    }

    @Test
    public void shouldReturnInformation() {
        OcaBoard ocaBoard = ocaDice.getOcaBoard();
        Player ocaPlayer = ocaDice.getPlayer();
        Integer max = ocaDice.getMAX();

        assertTrue(ocaBoard.equals(ocaBoardTest));
        assertTrue(ocaPlayer.equals(playerTest));
        assertTrue(max.equals(6));
    }
}
