package org.springframework.samples.parchisoca.parchis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoxesParchisTest {

    @Autowired
    BoxesParchisService boxesParchisService;

    BoxesParchis boxesParchis = new BoxesParchis();
    ParchisBoard parchisBoard = new ParchisBoard();

    @BeforeEach
    public void setUp() {

        boxesParchis.setPositionBoard(5);
        boxesParchis.setParchisBoard(parchisBoard);
        boxesParchis.setSafe(true);
        boxesParchis.setExit(false);
        boxesParchis.setBridge(false);
        boxesParchis.setXPosition(51);
        boxesParchis.setYPosition(98);

    }

    @Test
    @Transactional
    public void shouldRetrieveSetUp() {
        assertThat(boxesParchis.getPositionBoard() == 5);
        assertThat(boxesParchis.getParchisBoard().equals(parchisBoard));
        assertThat(boxesParchis.getSafe()).isTrue();
        assertThat(boxesParchis.getExit()).isFalse();
        assertThat(boxesParchis.getBridge()).isFalse();
        assertThat(boxesParchis.getXPosition() == 51);
        assertThat(boxesParchis.getYPosition() == 98);

    }
    
}
