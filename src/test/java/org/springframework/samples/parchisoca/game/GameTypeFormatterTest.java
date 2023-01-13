package org.springframework.samples.parchisoca.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameTypeFormatterTest {

    @Autowired
    GameService gameService;

    private GameType gameType = null;
    Locale locale;

    @BeforeEach
    void setUp(){
        gameType = gameService.findGameType("OCA");

    }

    @Test
    void printTest(){
        GameTypeFormatter gameTypeFormatter = new GameTypeFormatter();
        String print = gameTypeFormatter.print(gameType, locale);
        assertTrue(print.equals(gameType.getName()));
    }
}
