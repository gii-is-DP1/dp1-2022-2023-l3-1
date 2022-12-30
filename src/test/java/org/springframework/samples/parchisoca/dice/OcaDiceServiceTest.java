package org.springframework.samples.parchisoca.dice;

import java.security.Provider.Service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaDiceServiceTest {
    
    @Autowired
    OcaDiceService ocaDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    OcaBoardService ocaBoardService;

    OcaDice ocaDice = new OcaDice();

    Player player = null;
    OcaBoard ocaBoard = null;

    @BeforeEach
    public void setUp() {

        player = playerService.findById(1);
        ocaBoard = ocaBoardService.findById(1);

        ocaDice.setNumber(1);
        ocaDice.setOcaBoard(ocaBoard);
        ocaDice.setPlayer(player);

        ocaDiceService.save(ocaDice);
    }

    

}
