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

    private GameType gameTypeTest1 = new GameType();
    private GameType gameTypeTest2 = new GameType();
    private Game gameTest1 = new Game();
    private Game gameTest2 = new Game();

    @BeforeEach
    void setup() {
        List<Player> listPlayer = new ArrayList<>();
        Optional<Player> player1 = playerService.findPlayerById(1);
        listPlayer.add(player1.get());
        Optional<Player> player2 = playerService.findPlayerById(2);
        listPlayer.add(player2.get());
        gameTest1.setPlayers(listPlayer);
        gameTest2.setPlayers(listPlayer);

        gameTypeTest1.setId(1);
        gameTypeTest1.setName("PARCHIS");
        gameTest1.setId(12);
        gameTest1.setWinner(player1.get());
        gameTest1.setName("PartidaTest");
        gameTest1.setJugadores(2);
        gameTest1.setCode("ABCDF");
        gameTest1.setPrivacity(Privacity.PUBLIC);
        gameTest1.setGameType(gameTypeTest1);

        gameTypeTest2.setId(2);
        gameTypeTest2.setName("OCA");
        gameTest2.setId(13);
        gameTest2.setWinner(player1.get());
        gameTest2.setName("PartidaTest2");
        gameTest2.setJugadores(2);
        gameTest2.setCode("ABCDG");
        gameTest2.setPrivacity(Privacity.PUBLIC);
        gameTest2.setGameType(gameTypeTest2);


    }

    @Test
	@Transactional
	void shouldCreateNewGame() {
        gameService.save(gameTest1);
		Game game = gameService.findGameByCode(gameTest1.getCode());
        List<Game> gameLs = new ArrayList<>();
        gameLs.add(game); 

		assertTrue(gameLs.size() == 1);
	}

    @Test
    void shouldFindGameByCode(){
        gameService.save(gameTest1);
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
        gameTest.setGameType(gameTypeTest1);
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

    @Test
    public void selectGameTest() {
        String res1 = gameService.selectGame(gameTypeTest1, gameTest1);
        assertTrue(!res1.equals(null));

        String res2 = gameService.selectGame(gameTypeTest2, gameTest2);
        assertTrue(!res2.equals(null));

    }
 
}
