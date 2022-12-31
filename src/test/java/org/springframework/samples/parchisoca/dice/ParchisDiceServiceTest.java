package org.springframework.samples.parchisoca.dice;
import static org.assertj.core.api.Assertions.assertThat;

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
public class ParchisDiceServiceTest {

    @Autowired
    ParchisDiceService parchisDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisBoardService parchisBoardService;

    ParchisDice pDice1 = new ParchisDice();
    ParchisDice pDice2 = new ParchisDice();

    Player p = null;
    ParchisBoard pb = null;

    @BeforeEach
    public void setUp() {

        p = playerService.findById(1);
        pb = parchisBoardService.findById(1);
        
        pDice1.setNumber(1);
        pDice1.setParchisBoard(pb);
        pDice1.setPlayer(p);
        parchisDiceService.save(pDice1);

        pDice2.setNumber(2);
        pDice2.setParchisBoard(pb);
        pDice2.setPlayer(p);
        parchisDiceService.save(pDice2);
        
        parchisDiceService.save(pDice1, pDice2);
    }


    @Test
    public void shouldFindDiceByParchisBoardPlayer(){
        List<ParchisDice> ls = parchisDiceService.findDiceByParchisBoardPlayer(pb, p);
        assertThat(ls.size()== 2);
    }

    @Test
    public void shoulFindById(){
        ParchisDice dFind = parchisDiceService.findById(pDice1.getId());
        assertThat(dFind.getPlayer().equals(pDice1.getPlayer()));
        assertThat(dFind.getParchisBoard().equals(pDice1.getParchisBoard()));

    }
    
}
