package org.springframework.samples.parchisoca.board;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.Oca.BoxesOca;
import org.springframework.samples.parchisoca.Oca.BoxesOcaService;
import org.springframework.samples.parchisoca.Oca.SpecialBoxesOca;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaBoardTest {

    @Autowired
    OcaBoardService ocaBoardService;

    @Autowired
    BoxesOcaService boxesOcaService;

    @Autowired
    PlayerService playerService;

    @Autowired
    OcaPieceService ocaPieceService;

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

    @Test
    public void testAction () {
        BoxesOca boxesOca = new BoxesOca();
        OcaPiece ocaPiece = new OcaPiece();
        Player player = playerService.findById(1);

        ocaBoard.setBackground("BACKGROUNDTEST");
        ocaBoard.setWidth(10);
        ocaBoard.setHeight(10);
        ocaBoardService.save(ocaBoard);

        boxesOca.setOcaBoard(ocaBoard);
        boxesOca.setPositionBoard(24);
        boxesOca.setXPosition(300);
        boxesOca.setYPosition(16);
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.HOSTAL);
        boxesOcaService.save(boxesOca);

        ocaPiece.setColour(Colour.RED);
        ocaPiece.setXPosition(100);
        ocaPiece.setYPosition(580);
        ocaPiece.setOcaBoard(ocaBoard);
        ocaPiece.setPosition(1);
        ocaPiece.setPenalizationTurn(0);
        ocaPiece.setPlayer(player);
        ocaPieceService.save(ocaPiece);

        //test Hostal
        ocaBoard.action(boxesOca, ocaPiece);  
        assertTrue(ocaPiece.getPenalizationTurn() == 2);      
        
        //test Oca
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.OCA);
        boxesOca.setPositionBoard(59);
        Integer res = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(res == 59);

        //test Puente
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.BRIDGE);
        boxesOca.setPositionBoard(6);
        Integer resBridge = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resBridge == 12);

        //test Dados
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.DICES);
        boxesOca.setPositionBoard(26);
        Integer resDices = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resDices == 53);

        //test Pozo
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.WELL);
        ocaBoard.action(boxesOca, ocaPiece);  
        assertTrue(ocaPiece.getPenalizationTurn() == 4);  

        //test Laberinto
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.LABYRINTH);
        boxesOca.setPositionBoard(42);
        Integer resLabyrinth = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resLabyrinth == 30);

        //test Muerte
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.DEATH);
        boxesOca.setPositionBoard(58);
        Integer resDeath = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resDeath == 1);

        //test Meta
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.GOAL);
        boxesOca.setPositionBoard(63);
        Integer resGoal = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resGoal == 63);

        //test Prision
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.PRISON);
        ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(ocaPiece.getPenalizationTurn() == 3);

        //test Normal
        boxesOca.setSpecialBoxOca(SpecialBoxesOca.NORMAL);
        Integer resNormal = ocaBoard.action(boxesOca, ocaPiece);
        assertTrue(resNormal.equals(boxesOca.getPositionBoard()));
        
    }

    
}
