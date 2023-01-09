package org.springframework.samples.parchisoca.statics;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatServiceTest {

    @Autowired
    StatService statService;

    @Autowired
    PlayerService playerService;
    
    private Player player = null;

    @BeforeEach
    void setup() {
        player = playerService.findById(1);      
      
    }

    @Test
    public void shouldSaveNewStat(){
        Stat statTest = new Stat();
        statTest.setWonGames(3);
        statTest.setLostGames(5);
        statTest.setPlayedGames(8);
        statTest.setPlayer(player);
        statService.save(statTest);
        assertTrue(statTest.getId() != null);
    }

    @Test
    public void shouldFindStatByPlayer(){
        Stat statTest = statService.findStatsByPlayer(player);
        assertTrue(statTest != null);
    }

    @Test
    public void shouldIncreaseWonGame(){
        Stat stat = statService.findStatsByPlayer(player);
        Integer beforeIncrease = stat.getWonGames();
        statService.increaseWonGames(player);
        Integer afterIncrease = stat.getWonGames();
        assertTrue(beforeIncrease < afterIncrease);
    }

    @Test
    public void shouldIncreaseLostGame(){
        Stat stat = statService.findStatsByPlayer(player);
        Integer beforeIncrease = stat.getLostGames();
        statService.increaseLostGames(player);
        Integer afterIncrease = stat.getLostGames();
        assertTrue(beforeIncrease < afterIncrease);
    }





}