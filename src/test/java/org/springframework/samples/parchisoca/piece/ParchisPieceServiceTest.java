package org.springframework.samples.parchisoca.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.board.ParchisBoard;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisPieceServiceTest {

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisBoardService parchisBoardService;

    ParchisPiece p = new ParchisPiece();



    @BeforeEach
    public void setUp() {
        Player pTest = playerService.findById(1);
        ParchisBoard bTest = parchisBoardService.findById(1);
        p.setColour(Colour.BLUE);
        p.setXPosition(10);
        p.setYPosition(10);
        p.setParchisBoard(bTest);
        p.setPlayer(pTest);
        p.setFinishPosition(10);
        p.setPosition(0);
        p.setFinishPosition(10);
        p.setJustInGoal(true);
        p.setJustAte(true);
        p.setInGoal(true);
        

        parchisPieceService.save(p);
    }


    @Test
    void shouldfindParchisPieceById(){
        ParchisPiece pFind = parchisPieceService.findById(p.getId());
        List<ParchisPiece> ls = new ArrayList<>();
        ls.add(pFind);
        assertThat(ls.size() ==1);
        assertThat(pFind.getColour().equals(Colour.BLUE));
        assertThat(pFind.getXPosition() == 10);
        assertThat(pFind.getYPosition() == 10);
        assertThat(pFind.getFinishPosition() == 10);
        assertThat(pFind.getPosition() == 0);
        assertThat(pFind.getJustAte() == true);
        assertThat(pFind.getJustInGoal() == true);
        assertThat(pFind.getInGoal() == true);

        Integer size = 100;
        assertThat(p.getPositionXInPixels(size) == pFind.getPositionXInPixels(size));
        assertThat(p.getPositionYInPixels(size) == pFind.getPositionYInPixels(size));

        assertThat(p.getPlayer().equals(pFind.getPlayer()));
        assertThat(p.getParchisBoard().equals(pFind.getParchisBoard()));
    }


    @Test
    void shouldParchisPiecesByPlayerParchisBoard(){
    }
    
}
