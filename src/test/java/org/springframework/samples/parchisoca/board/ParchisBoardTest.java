package org.springframework.samples.parchisoca.board;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.parchis.FinishBoxes;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisBoardTest {

    @Autowired
    ParchisBoardService parchisBoardService;

    //INSERT INTO parchis_boards (id,background,height,width) VALUES (1,'resources/images/ParchisBoard.png',800,800);

    @Test
    public void testNewBoard(){
        testConstraints();
    }

    void testConstraints(){

        ParchisBoard p1 = new ParchisBoard();
        List<BoxesParchis> boxesParchis = new ArrayList<BoxesParchis>();
        List<ParchisPiece> parchisPieces = new ArrayList<ParchisPiece>();
        List<ParchisDice> parchisDices = new ArrayList<ParchisDice>();
        List<FinishBoxes> finishBoxes = new ArrayList<FinishBoxes>();

        p1.setId(10);
        p1.setBackground("resources/images/ParchisBoard.png");
        p1.setWidth(-800);
        p1.setHeight(800);
        p1.setTurn(1);
        p1.setBoxes(boxesParchis);
        p1.setPieces(parchisPieces);
        p1.setParchisDices(parchisDices);
        p1.setFinishBoxes(finishBoxes);

        assertThrows(ConstraintViolationException.class,() -> parchisBoardService.save(p1),
        "You are not constraining "+ "width can not be negative");

        ParchisBoard p2 = new ParchisBoard();
        p2.setId(15);
        p2.setBackground("resources/images/ParchisBoard.png");
        p2.setWidth(800);
        p2.setHeight(-800);

        assertThrows(ConstraintViolationException.class,() -> parchisBoardService.save(p2),
        "You are not constraining "+ "height can not be negative");
    }

    @Test
    @Transactional
    public void shouldFindHeightAndBackground() {
        ParchisBoard p1 = new ParchisBoard();
        p1.setBackground("resources/images/ParchisBoard.png");
        p1.setHeight(800);
        parchisBoardService.save(p1);

        assertTrue(p1.getHeight() == 800);
        assertFalse(p1.getBackground().isEmpty());

    }


    
}
