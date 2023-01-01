package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.statistic.StatRepository;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatServiceTest {

    @Autowired
    StatService statService;
    
    private Player player = new Player();

    @BeforeEach
    public void setup() {

        Stat stat = new Stat();
        stat.setWonGames(3);
        stat.setLostGames(5);
        stat.setPlayedGames(8);
        stat.setPlayer(player);
        statService.save(stat);

        Stat newStat = statService.findStatsByPlayer(stat.getPlayer());
        List<Stat> ls = new ArrayList<>();
        ls.add(newStat);
        assertTrue(ls.size() == 1);
    }

    // @Test
    // public void shouldFindStatsByPlayer() {
    //     Stat statTest = statService.findStatsByPlayer(player);
    //     assertThat(statTest.getPlayer() == player);
    // }

}