package org.springframework.samples.parchisoca.badWords;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.badWord.BadWordsService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BadWordsServiceTest {

    @Autowired
    BadWordsService badWordsService;

    @Autowired
    PlayerService playerService;

    @Test
    public void shouldCheckBadWords(){
        String badWordToCheck = "COCK";
        Boolean check = badWordsService.checkBadWords(badWordToCheck);
        assertTrue(check);
    }

    @Test
    public void shouldCheckPlayerBadWordsFirstName(){
        Player p = playerService.findById(1);
        p.setFirstName("COCK");
        Boolean check = badWordsService.checkPlayerBadWords(p);
        assertTrue(check);
    }

    @Test
    public void shouldCheckPlayerBadWordsSecondName(){
        Player p = playerService.findById(1);
        p.setLastName("COCK");
        Boolean check = badWordsService.checkPlayerBadWords(p);
        assertTrue(check);
    }
    

    @Test
    public void shouldCheckPlayerBadWordsUsername(){
        Player p = playerService.findById(1);
        p.getUser().setUsername("COCK");
        Boolean check = badWordsService.checkPlayerBadWords(p);
        assertTrue(check);
    }


}
