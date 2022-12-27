package org.springframework.samples.parchisoca.piece;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;

import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ConstraintViolationException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisPieceTest {

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisBoardService parchisBoardService;

    private ParchisPiece p = new ParchisPiece();

    @BeforeEach
    public void setUp() {
        Player pTest = playerService.findById(1);
        ParchisBoard bTest = parchisBoardService.findById(1);
        p.setParchisBoard(bTest);
        p.setPlayer(pTest);
        p.setFinishPosition(10);
        p.setPosition(0);
        p.setFinishPosition(10);
        p.setJustInGoal(true);
        p.setJustAte(true);
        p.setInGoal(true);

    }
    

    @Test
    public void testConstraintsColor(){
        p.setColour(null);
        p.setXPosition(15);
        p.setYPosition(15);

        assertThrows(ConstraintViolationException.class,() -> parchisPieceService.save(p),
        "You are not constraining "+ "colour can not be null");
    }

    @Test
    public void testConstraintsYPosition(){
        p.setColour(Colour.BLUE);
        p.setXPosition(10);
        p.setYPosition(50);

        assertThrows(ConstraintViolationException.class,() -> parchisPieceService.save(p),
        "You are not constraining "+ "YPosition can not be plus than 18");
    }



    @Test
    public void testXposition() {
        p.setColour(Colour.BLUE);
        p.setXPosition(50);
        p.setYPosition(10);
        
        assertThrows(ConstraintViolationException.class,() -> parchisPieceService.save(p),
        "You are not constraining "+ "XPosition can not be plus than 18");
    }

    
}
