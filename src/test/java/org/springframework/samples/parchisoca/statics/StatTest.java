package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatTest {

    @Autowired
    StatService statService;

    private Stat stat = new Stat();

    @Test
    @Transactional
    public void setup() {
        stat.setWonGames(3);
        stat.setLostGames(5);
        stat.setPlayedGames(8);

        List<Stat> statsLs = new ArrayList<>();
        statsLs.add(stat);

        assertThat(stat.getLostGames() == 5);
        assertThat(stat.getWonGames() == 3);
        assertThat(stat.getPlayedGames() == 8);
    }
    
}
