package org.springframework.samples.parchisoca.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisBoardServiceTest {

    @Autowired
    ParchisBoardService ps;

    // @Test
	// @Transactional
	// void shouldCreateNewParchisBoard() {

    //     ParchisBoard p = new ParchisBoard();
    //     p.setId(15);
    //     p.setBackground("resources/images/ParchisBoard.png");
    //     p.setWidth(800);
    //     p.setHeight(800);

    //     ps.save(p);
    //     ParchisBoard newP = ps.findById(1).get();
    //     assertTrue(newP.getId()==1);
	// }

    @Test
    void shouldFindBoardById(){
        Optional <ParchisBoard> p = ps.findById(1);
        assertThat(p.get().getId()==1);

    }
    
}
