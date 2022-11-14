package org.springframework.samples.parchisoca.board;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisBoardTest {

    @Autowired
    ParchisBoardService ps;

    //INSERT INTO parchis_boards (id,background,height,width) VALUES (1,'resources/images/ParchisBoard.png',800,800);

    @Test
    public void testNewBoard(){
        testConstraints();
    }

    void testConstraints(){
        ParchisBoard p = new ParchisBoard();
        p.setId(10);
        p.setBackground("resources/images/ParchisBoard.png");
        p.setWidth(-800);
        p.setHeight(800);

        assertThrows(ConstraintViolationException.class,() -> ps.save(p),
        "You are not constraining "+ "width can not be negative");

        ParchisBoard p2 = new ParchisBoard();
        p2.setId(15);
        p2.setBackground("resources/images/ParchisBoard.png");
        p2.setWidth(800);
        p2.setHeight(-800);

        assertThrows(ConstraintViolationException.class,() -> ps.save(p2),
        "You are not constraining "+ "height can not be negative");
    }


    
}
