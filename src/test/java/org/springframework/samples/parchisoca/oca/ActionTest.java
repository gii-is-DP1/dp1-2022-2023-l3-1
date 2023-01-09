package org.springframework.samples.parchisoca.oca;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.oca.Action;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ActionTest {

    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    PlayerService playerService;



    OcaPiece ocaPiece = new OcaPiece();
    Player player = null;
    @BeforeEach
    void setUp(){
        player = playerService.findById(1);
        ocaPiece.setColour(Colour.RED);
        ocaPiece.setOcaBoard(new OcaBoard());
        ocaPiece.setPenalizationTurn(0);
        ocaPiece.setPlayer(player);
        ocaPiece.setXPosition(100);
        ocaPiece.setYPosition(580);
        ocaPieceService.save(ocaPiece);
    }

    @Test
    void shouldBridge(){
        Action action = new Action();
        Integer res = action.bridge(6, ocaPiece);
        assertTrue(res == 12);

        Integer resNoBridge = action.bridge(15, ocaPiece);
        assertTrue(resNoBridge!= 12);

    }

    @Test
    void shouldDeath(){
        Action action = new Action();
        Integer res = action.death(43);
        assertTrue(res == 1);
    }

    @Test
    void shouldDices(){
        Action action = new Action();
        Integer resGoDices = action.dices(26, ocaPiece);
        assertTrue(resGoDices == 53);

        Integer resNoDices = action.dices(50, ocaPiece);
        assertTrue(resNoDices == 23);
    }

    @Test
    void shouldHostal(){
        Action action = new Action();
        action.hostal(23, ocaPiece);
        assertTrue(ocaPiece.getPenalizationTurn() == 2);
    }

    @Test
    void shouldlLabyrinth(){
        Action action = new Action();
        Integer resLabyrinth =  action.labyrinth(50);
        assertTrue(resLabyrinth == 30);
    }

    @Test
    void shouldOca(){
        Action action = new Action();
        Integer resNoOca = action.oca(59, ocaPiece);
        assertTrue(resNoOca == 59);

        Integer resOca = action.oca(14, ocaPiece);
        assertTrue(resOca !=14);
    }

    @Test
    void shouldPrision(){
        Action action = new Action();
        action.prison(23, ocaPiece);
        assertTrue(ocaPiece.getPenalizationTurn() == 3);
    }

    @Test
    void shouldWell(){
        Action action = new Action();
        action.well(23, ocaPiece);
        assertTrue(ocaPiece.getPenalizationTurn() == 4);
    }

    @Test
    void shouldGoal(){
        Action action = new Action();
        Integer res = action.goal(63);
        assertTrue(res == 63);

    }



    
}
