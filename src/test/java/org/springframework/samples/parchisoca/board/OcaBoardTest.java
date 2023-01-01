package org.springframework.samples.parchisoca.board;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaBoardTest {

    @Autowired
    OcaBoardService ocaBoardService;

    private OcaBoard ocaBoard = new OcaBoard();

    @BeforeEach
    void setUp(){
        ocaBoard.setBoxes(new ArrayList<>());
        ocaBoard.setGame(new Game());
        ocaBoard.setPieces(new ArrayList<OcaPiece>());
        ocaBoard.setTurn(0);

    }

    @Test
    public void testConstraintBackGround(){
        ocaBoard.setBackground(null);
        ocaBoard.setWidth(10);
        ocaBoard.setHeight(10);

        assertThrows(ConstraintViolationException.class,() -> ocaBoardService.save(ocaBoard),
        "You are not constraining "+ "background can not be null");

    }

    @Test
    public void testConstraintWidth(){
        ocaBoard.setBackground("background");
        ocaBoard.setWidth(-10);
        ocaBoard.setHeight(10);

        assertThrows(ConstraintViolationException.class,() -> ocaBoardService.save(ocaBoard),
        "You are not constraining "+ "width can not be less than 0");

    }

    @Test
    public void testConstraintHeigth(){
        ocaBoard.setBackground("background");
        ocaBoard.setWidth(10);
        ocaBoard.setHeight(-10);

        assertThrows(ConstraintViolationException.class,() -> ocaBoardService.save(ocaBoard),
        "You are not constraining "+ "height can not be less than 0");

    }

    
}