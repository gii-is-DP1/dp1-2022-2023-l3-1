package org.springframework.samples.parchisoca.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.OcaDiceService;
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
public class OcaBoardServiceTest {
    @Autowired
    OcaBoardService ocaBoardService;

    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    OcaDiceService ocaDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    BoxesOcaService boxesOcaService;

    OcaBoard ocaBoard = new OcaBoard();

    Player player = null;

    OcaDice ocaDice = new OcaDice();

    OcaPiece ocaPiece = null;


    @BeforeEach
    void setUp(){

        player = playerService.findById(1);
        ocaDice.setPlayer(player);
        ocaDice.setOcaBoard(ocaBoard);
        ocaDice.setNumber(3);
        ocaDiceService.save(ocaDice);

        List<OcaDice> ls = new ArrayList<>();
        ls.add(ocaDice);

        BoxesOca boxOca = new BoxesOca();
        boxOca.setOcaBoard(ocaBoard);
        boxOca.setPositionBoard(50);
        boxOca.setSpecialBoxOca(SpecialBoxesOca.BRIDGE);

        List<BoxesOca> listBoxesOca = new ArrayList<>();
        listBoxesOca.add(boxOca);

       
        player.setOcaDice(ls);
        playerService.save(player);

        ocaBoard.setBoxes(listBoxesOca);
        ocaBoard.setGame(new Game());
        ocaBoard.setPieces(new ArrayList<OcaPiece>());
        ocaBoard.setTurn(1);
        ocaBoard.setBackground("backGround");
        ocaBoard.setWidth(10);
        ocaBoard.setHeight(10);
    }

    @Test
    void shouldFindOcaBoardById(){
      ocaBoardService.save(ocaBoard);
      OcaBoard ocaBoardTest = ocaBoardService.findById(ocaBoard.getId());
      assertThat(ocaBoardTest.getBackground().equals(ocaBoard.getBackground()));
      assertThat(ocaBoardTest.getBoxes()!= null);
      assertThat(ocaBoardTest.getPieces()!= null);
      assertThat(ocaBoardTest.getHeight() != 0);
      assertThat(ocaBoardTest.getWidth()!= 0);
      assertThat(ocaBoardTest.getGame() != null);

    }

    @Test
    void shouldBounceBack() {
      ocaBoardService.save(ocaBoard);
      OcaBoard ocaBoardTest = ocaBoardService.findById(ocaBoard.getId());
      Integer firstBranch = ocaBoardTest.bounceBack(10);
      assertThat(firstBranch == 10);

      Integer secondBranch = ocaBoardTest.bounceBack(65);
      assertThat(secondBranch == 63);
    }

    @Test
    void shouldAddPieceNotNull(){
      ocaBoardService.save(ocaBoard);
      OcaBoard ocaBoardTest = ocaBoardService.findById(ocaBoard.getId());
      Integer beforeAdd = ocaBoardTest.getPieces().size();
      ocaBoardTest.addPiece(ocaPiece);
      Integer afterAdd = ocaBoardTest.getPieces().size();

      assertTrue(beforeAdd < afterAdd);

    }

    @Test
    void shouldAddPieceNull() {
      ocaBoardService.save(ocaBoard);
      OcaBoard ocaBoardTest = ocaBoardService.findById(ocaBoard.getId());
      ocaBoardTest.setPieces(null);

      Integer beforeAdd = 0;
      ocaBoardTest.addPiece(ocaPiece);
      Integer afterAdd = ocaBoardTest.getPieces().size();

      assertTrue(beforeAdd < afterAdd);
    }

    @Test
    void shouldGiveNextPosition() {
      ocaBoardService.save(ocaBoard);

      OcaPiece ocaPieceTest = new OcaPiece();
      ocaPieceTest.setColour(Colour.BLUE);
      ocaPieceTest.setOcaBoard(ocaBoard);
      ocaPieceTest.setPenalizationTurn(0);
      ocaPieceTest.setPlayer(player);
      ocaPieceTest.setPosition(10);
      ocaPieceTest.setXPosition(50);
      ocaPieceTest.setYPosition(50);
      ocaPieceService.save(ocaPieceTest);

      Integer nextPosition = ocaBoardService.nextPosition(ocaBoard, ocaPieceTest, 1);
      assertTrue(nextPosition != 0);
    }

    @Test
    void shouldInitBoard() {
      Game game = new Game();
      List<Player> playerList = new ArrayList<>();
      playerList.add(player);
      game.setPlayers(playerList);
      OcaBoard ocaBoardInit = ocaBoardService.initBoard(game);
      assertThat(ocaBoardInit != null);
    }

    @Test
    void shouldNextTurn() {
      ocaBoardService.save(ocaBoard);

      OcaPiece ocaPieceTest = new OcaPiece();
      ocaPieceTest.setColour(Colour.BLUE);
      ocaPieceTest.setOcaBoard(ocaBoard);
      ocaPieceTest.setPenalizationTurn(0);
      ocaPieceTest.setPlayer(player);
      ocaPieceTest.setPosition(10);
      ocaPieceTest.setXPosition(50);
      ocaPieceTest.setYPosition(50);
      ocaPieceService.save(ocaPieceTest);

      List<OcaPiece> listOcaPiece = new ArrayList<>();
      listOcaPiece.add(ocaPieceTest);
      listOcaPiece.add(ocaPieceTest);
      ocaBoard.setPieces(listOcaPiece);


      OcaPiece ocaPieceUnderTest = ocaBoardService.nextTurn(ocaBoard);
      assertTrue(ocaPieceUnderTest != null);

      listOcaPiece.add(ocaPieceTest);
      OcaPiece ocaPieceUnderTest2 = ocaBoardService.nextTurn(ocaBoard);
      assertTrue(ocaPieceUnderTest2 != null);

    }


}