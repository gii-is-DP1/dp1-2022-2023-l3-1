package org.springframework.samples.parchisoca.game;

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
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.ToString;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {

    @Autowired
    GameService gs;

    @Autowired
    PlayerService ps;


    private GameType ge = new GameType();
    private Game g1 = new Game();

    @BeforeEach
    void setup(){
        ge.setId(1);
        ge.setName("PARCHIS");
        Optional<Player> p = ps.findPlayerById(1);
        g1.setId(12);
        g1.setWinner(p.get());
        g1.setName("PartidaTest");
        g1.setJugadores(4);
        g1.setCode("ABCDF");
        g1.setPrivacity(Privacity.PUBLIC);
        g1.setGameType(ge);
    }

    @Test
	@Transactional
	void shouldCreateNewGame() {
        gs.save(g1);
		Game game = gs.findGameByCode(g1.getCode());
        List<Game> gameLs = new ArrayList<>();
        gameLs.add(game); 

		assertTrue(gameLs.size() == 1);
	}

    @Test
    void shouldFindGameByCode(){
        gs.save(g1);
        Game g = gs.findGameByCode("ABCDF");
        assertThat(g.getName()== "PARCHIS");
    }



    

   


    
}
