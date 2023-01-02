package org.springframework.samples.parchisoca.oca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
<<<<<<< HEAD
import org.springframework.samples.parchisoca.oca.BoxesOca;
import org.springframework.samples.parchisoca.oca.BoxesOcaService;
import org.springframework.samples.parchisoca.oca.SpecialBoxesOca;
=======
>>>>>>> master
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;

import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoxesOcaServiceTest {

    @Autowired
    BoxesOcaService boxesOcaService;

    @Autowired
    OcaBoardService ocaBoardService;

    OcaBoard ocaBoard = null;
    BoxesOca boxOca = new BoxesOca();

    @BeforeEach
    void setup(){
        ocaBoard = ocaBoardService.findById(1);

        boxOca.setOcaBoard(ocaBoard);
        boxOca.setPositionBoard(5);
        boxOca.setSpecialBoxOca(SpecialBoxesOca.OCA);
        boxOca.setXPosition(100);
        boxOca.setYPosition(100);

        boxesOcaService.save(boxOca);
    }

    @Test
    public void shouldFindBoxByPosition(){
        BoxesOca bo = boxesOcaService.findBoxByPosition(1, 5);
        assertThat(bo.getPositionBoard() == 5);
        assertThat(bo.getId().equals(boxOca.getId()));
        assertTrue(bo.getXPosition().equals(100));
        assertTrue(bo.getYPosition().equals(100));
        assertThat(bo.getOcaBoard().equals(boxOca.getOcaBoard()));
        assertTrue(bo.getSpecialBoxOca().equals(SpecialBoxesOca.OCA));

    }


    
}
