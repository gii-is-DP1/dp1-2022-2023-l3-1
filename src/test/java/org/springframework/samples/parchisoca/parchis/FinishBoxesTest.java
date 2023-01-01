package org.springframework.samples.parchisoca.parchis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FinishBoxesTest {

    @Autowired
    FinishBoxesService finishBoxesService;

    @Autowired
    ParchisBoardService parchisBoardService;

    FinishBoxes finishBoxes = new FinishBoxes();

    ParchisBoard parchisBoard = null;

    @BeforeEach
    void setUp(){
        parchisBoard = parchisBoardService.findById(1);

        finishBoxes.setGoal(true);
        finishBoxes.setParchisBoard(parchisBoard);
        finishBoxes.setPosition(50);
    }

    @Test
    void shouldCreateNewFinishBox(){
        finishBoxesService.save(finishBoxes);
        assertTrue(finishBoxes.getId() != null);
        assertTrue(finishBoxes.getGoal()== true);
        assertTrue(finishBoxes.getParchisBoard() != null);
        assertTrue(finishBoxes.getPosition() == 50);
    }
    
}
