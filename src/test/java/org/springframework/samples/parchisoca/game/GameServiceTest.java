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
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;


    private GameType ge = new GameType();
    private Game g1 = new Game();

    @BeforeEach
    void setup(){
        ge.setId(1);
        ge.setName("PARCHIS");
        Optional<Player> p = playerService.findPlayerById(1);
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
        gameService.save(g1);
		Game game = gameService.findGameByCode(g1.getCode());
        List<Game> gameLs = new ArrayList<>();
        gameLs.add(game); 

		assertTrue(gameLs.size() == 1);
	}

    @Test
    void shouldFindGameByCode(){
        gameService.save(g1);
        Game g = gameService.findGameByCode("ABCDF");
        assertThat(g.getName()== "PARCHIS");
    }

    @Test
    void shouldGetAllGames(){
        List<Game> allGames = gameService.getGames();
        assertTrue(allGames != null);
        assertTrue(allGames.size() > 0);
    } 

    @Test
    void shouldFindGameType(){
        GameType gameType = gameService.findGameType("PARCHIS");
        assertTrue(gameType != null);
    }

    @Test
    void shouldFindAllGamesTypes(){
        List<GameType> gameTypes = gameService.findAllGameTypes();
        assertTrue(gameTypes != null);
        assertTrue(gameTypes.size() > 0);
        assertTrue(gameTypes.get(0).getName().equals("PARCHIS") || gameTypes.get(0).getName().equals("OCA"));
    }

    @Test
    void shouldDeleteGameById(){
        List<Game> allGamesBeforeDelete = gameService.getGames();
        gameService.deleteGameById(1);
        List<Game> allGamesAfterDelete = gameService.getGames();
        assertTrue(allGamesBeforeDelete.size() > allGamesAfterDelete.size());
    }

    @Test
    void shouldFindPublicGames(){
        List<Game> allGamesPublic = gameService.findPublicGames();
        assertTrue(allGamesPublic != null);
        assertTrue(allGamesPublic.size() > 0);
    }

    @Test
    void shouldFindPublicGamesNotFinished(){
        List<Game> allGamesPublicNotFinish = gameService.findPublicGamesNotFinished();
        assertTrue(allGamesPublicNotFinish != null);
        assertTrue(allGamesPublicNotFinish.size() > 0);
    }

    @Test
    void shouldFindGamesFinished(){
        List<Game> allGamesPublicFinish = gameService.findGamesFinished();
        assertTrue(allGamesPublicFinish != null);
        assertTrue(allGamesPublicFinish.size() > 0);
    }

    @Test
    void shouldFindGamesInProgress(){
        List<Game> allGamesPublicFinish = gameService.findGamesInProgress();
        assertTrue(allGamesPublicFinish != null);
        assertTrue(allGamesPublicFinish.size() > 0);
    }

    @Test
    void shouldFindPlayerById(){
        Player player = gameService.findPlayerById(1);
        assertTrue(player != null);
    }

    @Test
    void shouldTestGetAndSetOfGame(){
        Player player = playerService.findById(1);

        Player player2 = playerService.findById(2);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);

        Game gameTest = new Game();
        gameTest.setCode("CODES");
        gameTest.setCreator(player);
        gameTest.setGameType(ge);
        gameTest.setInProgress(false);
        gameTest.setJugadores(4);
        gameTest.setName("Game for Test");
        gameTest.setOcaBoard(new OcaBoard());
        gameTest.setParchisBoard(new ParchisBoard());
        gameTest.setPlayers(listPlayer);
        gameTest.setPrivacity(Privacity.PUBLIC);
        gameTest.setStarted(false);
        gameTest.setWinner(player);

        gameService.save(gameTest);

        assertTrue(gameTest.getNumberOfPlayers() != 0);
        
        Integer beforeAdd = gameTest.getPlayers().size();
        gameTest.addPlayer(player2);
        Integer afterAdd = gameTest.getPlayers().size();
        assertTrue(beforeAdd < afterAdd );

        assertThat(gameTest.getStarted() == false);
        assertThat(gameTest.getOcaBoard() != null);
        assertThat(gameTest.isInProgress() == false);
        assertThat(gameTest.getPrivacity().equals(Privacity.PUBLIC));
        assertThat(gameTest.getParchisBoard() != null);
        assertThat(gameTest.getWinner() != null);
        assertThat(gameTest.getGameType() != null);
        assertThat(gameTest.getJugadores() != null);
        assertThat(gameTest.getCreator() != null);
    }

    @Test
    void shouldAddPlayerIfListNotExist(){
        Player player = playerService.findById(1);

        Game game = new Game();
        game.addPlayer(player);
        assertThat(game.getPlayers().size() >= 1);
        
    }











    

   


    
}
