package org.springframework.samples.parchisoca.parchis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoxesParchisTest {

    @Autowired
    BoxesParchisService boxesParchisService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisPieceService parchisPieceService;

    BoxesParchis boxesParchis = new BoxesParchis();

    ParchisPiece parchisPiece = null;

    @BeforeEach
    void setUp(){

        parchisPiece = parchisPieceService.findById(1);

        boxesParchis.setBridge(true);
        boxesParchis.setExit(false);
        boxesParchis.setParchisBoard(new ParchisBoard());
        boxesParchis.setPiecesInBox(new ArrayList<>());
        boxesParchis.setPositionBoard(20);
        boxesParchis.setSafe(false);
        boxesParchis.setXPosition(50);
        boxesParchis.setYPosition(50);
    }

    @Test
    void shouldCreateCorrectParchisBox(){
       boxesParchisService.save(boxesParchis);
       assertTrue(boxesParchis.getBridge().equals(true));
        
    }

    @Test
    void sholudAddPieceToBox(){
        //para una lista existente
        boxesParchisService.save(boxesParchis);
        boxesParchis.addPieceToBox(parchisPiece);
        assertTrue(boxesParchis.getPiecesInBox().size() != 0);

        //para una lista inexistente
        boxesParchis.setPiecesInBox(null);
        boxesParchis.addPieceToBox(parchisPiece);
        assertTrue(boxesParchis.getPiecesInBox().size() != 0);

    }

    @Test
    void shouldTestSpecialBoxesParchis(){
        assertTrue(SpecialBoxesParchis.valueOf("ENTRY") != null);

    }
    
}
