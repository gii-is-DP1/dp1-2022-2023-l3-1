package org.springframework.samples.parchisoca.board;

import static org.assertj.core.api.Assertions.assertThat;
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

    OcaBoard ocaBoard = new OcaBoard();

    Player player = null;

    OcaDice ocaDice = new OcaDice();

    OcaPiece ocaPiece = null;


    @BeforeEach
    void setUp(){

        player = playerService.findById(1);
        ocaDice.setPlayer(player);
        ocaDice.setOcaBoard(ocaBoard);
        ocaDiceService.save(ocaDice);
        List<OcaDice> ls = new ArrayList<>();
        ls.add(ocaDice);

       
        player.setOcaDice(ls);
        playerService.save(player);

        ocaBoard.setBoxes(new ArrayList<>());
        ocaBoard.setGame(new Game());
        ocaBoard.setPieces(new ArrayList<OcaPiece>());
        ocaBoard.setTurn(0);
        ocaBoard.setBackground("backGround");
        ocaBoard.setWidth(10);
        ocaBoard.setHeight(10);

    }

    @Test
    void shouldFindOcaBoardById(){
      ocaBoardService.save(ocaBoard);
      OcaBoard ocaBoardTest = ocaBoardService.findById(ocaBoard.getId());
      assertThat(ocaBoardTest.getBackground().equals(ocaBoard.getBackground()));
    }

    // @Test
    // void shouldFindOcaDiceByPlayerAndBoard(){
    //   ocaBoardService.save(ocaBoard);
    //   OcaDice d = ocaBoardService.findOcaDiceByPlayerAndBoard(player, ocaBoard);
    //   assertTrue(d != null);
      
    // }





}
