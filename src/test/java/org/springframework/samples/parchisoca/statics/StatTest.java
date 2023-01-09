package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatTest {

    @Autowired
    StatService statService;

    private Stat stat = new Stat();
    private Player player = new Player();

    @BeforeEach
    public void setup() {
        stat.setWonGames(3);
        stat.setLostGames(5);
        stat.setPlayedGames(8);
        stat.setPlayer(player);

        List<Stat> statsLs = new ArrayList<>();
        statsLs.add(stat); 
    }

    @Test
    public void shouldRetrieveInformation() {
        assertThat(stat.getLostGames() == 5);
        assertThat(stat.getWonGames() == 3);
        assertThat(stat.getPlayedGames() == 8);
        assertThat(stat.getPlayer().equals(player));
        assertThat(stat.ratio(3, 5).equals((3./5.)*100));
    }

    
}
