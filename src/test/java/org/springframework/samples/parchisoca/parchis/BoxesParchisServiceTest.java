package org.springframework.samples.parchisoca.parchis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoxesParchisServiceTest {

    @Autowired
    BoxesParchisService boxesParchisService;

    @Autowired
    ParchisBoardService parchisBoardService;

    ParchisBoard parchisBoard = null;

    BoxesParchis boxesParchis = new BoxesParchis();

    @BeforeEach
    void setUp(){
        parchisBoard = parchisBoardService.findById(1);
        boxesParchis.setBridge(true);
        boxesParchis.setExit(false);
        boxesParchis.setParchisBoard(parchisBoard);
        boxesParchis.setPiecesInBox(new ArrayList<>());
        boxesParchis.setPositionBoard(20);
        boxesParchis.setSafe(false);
        boxesParchis.setXPosition(50);
        boxesParchis.setYPosition(50);
        boxesParchisService.save(boxesParchis);


    }

    @Test
    void shouldfindBoxByPosition(){
        BoxesParchis boxTest = boxesParchisService.findBoxByPosition(20, parchisBoard);
        assertTrue(boxTest != null);
        assertTrue(boxTest.getBridge() == true);
        assertTrue(boxTest.getExit() == false);
        assertTrue(boxTest.getParchisBoard().equals(parchisBoard));
        assertTrue(boxTest.getPiecesInBox().equals(boxesParchis.getPiecesInBox()));
        assertTrue(boxTest.getPositionBoard() == 20);
        assertTrue(boxTest.getXPosition() == 50);
        assertTrue(boxTest.getYPosition() == 50);
        assertTrue(boxTest.getSafe() == false);
    }
    
}
