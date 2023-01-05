package org.springframework.samples.parchisoca.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ParchisBoardServiceTest {

    @Autowired
    ParchisBoardService parchisBoardService;

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    ParchisDiceService parchisDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    GameService gameService;

    Player player = null;

    Player player2 = null;

    Player player3 = null;

    Player player4 = null;

    Game game = null;

    ParchisBoard parchisBoardTest = null;

    @BeforeEach
    void setUp(){
        player = playerService.findById(1);
        player2 = playerService.findById(1);
        player3 = playerService.findById(1);
        player4 = playerService.findById(1);


        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        game = gameService.findGameById(1);
        game.setPlayers(playerList);
        gameService.save(game);

        ParchisPiece parchisPiece = parchisPieceService.findById(1);
        parchisPiece.setFinishPosition(63);
        parchisPiece.setInGoal(false);
        parchisPieceService.save(parchisPiece);


        List<ParchisPiece> parchisPieceList = new ArrayList<>();
        parchisPieceList.add(parchisPiece);


        ParchisDice parchisDice1 = new ParchisDice();
        parchisDice1.setNumber(0);
        parchisDice1.setParchisBoard(parchisBoardTest);
        parchisDice1.setPlayer(player);
        parchisDiceService.save(parchisDice1);

        List<ParchisDice> parchisDiceList = new ArrayList<>();
        parchisDiceList.add(parchisDice1);

        parchisBoardTest = parchisBoardService.findById(1);
        parchisBoardTest.setParchisDices(parchisDiceList);
        parchisBoardTest.setPieces(parchisPieceList);
        parchisBoardService.save(parchisBoardTest);

    }

    @Test
	@Transactional
	void shouldCreateNewParchisBoard() {

        ParchisBoard p = new ParchisBoard();
        p.setBackground("resources/images/ParchisBoard.png");
        p.setWidth(950);
        p.setHeight(800);

        parchisBoardService.save(p);
        ParchisBoard newP = parchisBoardService.findById(p.getId());
        List<ParchisBoard> ls = new ArrayList<>();
        ls.add(newP);
        assertTrue(ls.size()==1);
        assertTrue(newP.getWidth() == 950);
	}

    @Test
    void shouldFindBoardById(){
        ParchisBoard p = parchisBoardService.findById(1);
        assertThat(p.getId()==1);
    }

    @Test
    void ShouldInitBoard(){
        ParchisBoard parchisBoard = parchisBoardService.initBoard(game);
        assertThat(parchisBoard != null);
        assertThat(parchisBoard.getPieces() != null);
        assertThat(parchisBoard.getCasillasParchis() != null);
    }

    @Test
    void shouldNextTurn(){
        Player playerTestFirstCondition = parchisBoardService.nextTurn(parchisBoardTest, game, 3);
        assertThat(playerTestFirstCondition != null);

        Player playerTestSecondCondition = parchisBoardService.nextTurn(parchisBoardTest, game, 1);
        assertThat(playerTestSecondCondition != null);
    }

    @Test
    void shouldFindParchisDiceByPlayer(){
        List<ParchisDice> parchisDiceList = parchisBoardService.findParchisDiceByPlayer(player, parchisBoardTest);
        assertThat(parchisDiceList != null);
    }

    @Test
    void shouldMovementPieceFirstBranch(){
        ParchisPiece parchisPiece = parchisBoardTest.getPieces().get(0);
        ParchisDice parchisDice = parchisBoardTest.getParchisDices().get(0);

        ParchisPiece parchisPieceTest = parchisBoardService.movementPiece(parchisBoardTest, parchisPiece, parchisDice);
        assertThat(parchisPieceTest != null);

    }

    // @Test
    // void shouldMovementPieceSecondBranch(){
    //     ParchisPiece parchisPiece2 = parchisPieceService.findById(1);
    //     parchisPiece2.setFinishPosition(63);
    //     parchisPiece2.setInGoal(true);
    //     parchisPiece2.setPosition(10);
    //     parchisPieceService.save(parchisPiece2);

    //     List<ParchisPiece> parchisPieceList = new ArrayList<>();
    //     parchisPieceList.add(parchisPiece2);
    //     parchisBoardTest.setPieces(parchisPieceList);

    //     ParchisPiece parchisPiece = parchisBoardTest.getPieces().get(0);
    //     ParchisDice parchisDice = parchisBoardTest.getParchisDices().get(0);

    //     ParchisPiece parchisPieceTest = parchisBoardService.movementPiece(parchisBoardTest, parchisPiece, parchisDice);
    //     assertThat(parchisPieceTest != null);
    // }


    
}
