package org.springframework.samples.parchisoca.piece;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaPieceServiceTest {

    @Autowired(required = false)
    OcaPieceRepository ocaPieceRepository;

    @Autowired(required = false)
    OcaPieceService ocaPieceService;

    @Autowired
    OcaBoardService ocaBoardService;

    @Autowired
    PlayerService playerService;

    OcaPiece op = new OcaPiece();

    @BeforeEach
    void setUp(){
        OcaBoard ocaBoard = ocaBoardService.findById(1);
        op.setOcaBoard(ocaBoard);
        op.setPosition(1);
        op.setXPosition(100);
        op.setYPosition(100);
        op.setPenalizationTurn(1);
        Player player = playerService.findById(1);
        op.setPlayer(player);
        op.setColour(Colour.BLUE);
        ocaPieceService.save(op);
    }


    @Test
    void shouldfindOcaPieceById(){
        OcaPiece oTest = ocaPieceService.findOcaPieceById(op.getId());
        List<OcaPiece> ls = new ArrayList<>();
        ls.add(oTest);
        assertThat(ls.size() ==1);
        assertThat(oTest.getXPosition() == 100);
        assertThat(oTest.getYPosition() == 100);
        assertThat(oTest.getOcaBoard().equals(op.getOcaBoard()));
        assertThat(oTest.getPosition() == 1);
        assertThat(oTest.getPenalizationTurn() == 1);
        assertThat(oTest.getPlayer().equals(op.getPlayer()));
        assertThat(oTest.getColour().equals(Colour.BLUE));
    }

    
}
