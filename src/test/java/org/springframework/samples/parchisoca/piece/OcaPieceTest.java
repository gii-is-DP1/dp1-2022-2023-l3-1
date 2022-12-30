package org.springframework.samples.parchisoca.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaPieceTest {

    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    OcaBoardService ocaBoardService;

    OcaPiece op = new OcaPiece();

    @BeforeEach
    void setUp(){
        OcaBoard ocaBoard = ocaBoardService.findById(1);
        op.setOcaBoard(ocaBoard);
        op.setPosition(1);
        op.setPenalizationTurn(1);
        Player player = playerService.findById(1);
        op.setPlayer(player);

    }

    @Test
    public void testColourConstraint(){
        op.setColour(null);
        op.setXPosition(100);
        op.setYPosition(100);

        assertThrows(ConstraintViolationException.class,() -> ocaPieceService.save(op),
        "You are not constraining "+ "colour can not be null");
    }


   
}
