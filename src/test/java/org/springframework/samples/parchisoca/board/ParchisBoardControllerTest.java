package org.springframework.samples.parchisoca.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.junit.jupiter.api.Test;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



@WebMvcTest(controllers=ParchisBoardController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)

public class ParchisBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
	private ParchisBoardService parchisBoardService;

    @MockBean
	private PlayerService playerService;

	@MockBean
	private ParchisDiceService parchisDiceService;

    @MockBean
	private ParchisPieceService parchisPieceService;
    
    @MockBean 
    private GameService gameService;



    @WithMockUser
    @Test
    public void testWelcome() throws Exception {
        when(parchisBoardService.findById(1)).thenReturn(new ParchisBoard());
        mockMvc.perform(get("/parchisBoard")).
        andExpect(status().isOk()).
        andExpect(model().attributeExists("parchisBoard"));
    }

    @WithMockUser
    @Test
    public void testBoard() throws Exception {
        mockMvc.perform(get("/boards/parchisBoard/{parchisBoardId}", 1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testRollDice() throws Exception {
        mockMvc.perform(get("/boards/parchisBoard/{parchisBoardId}/dice", 1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testDiceSelection() throws Exception {
        mockMvc.perform(get("/boards/parchisBoard/{parchisBoardId}/diceSelection", 1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testPieceSelection() throws Exception {
        mockMvc.perform(get("/boards/parchisBoard/{parchisBoardId}/dice/{diceId}/pieceSelection", 1,1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testPieceAction() throws Exception {
        mockMvc.perform(get("/boards/parchisBoard/{parchisBoardId}/dice/{diceId}/pieceSelection/piece/{parchisPieceId}", 1,1,1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testExitPlayerGame() throws Exception {
        String code = "ASDFG";
        mockMvc.perform(get("/games/lobby/parchis/{code}/exit", code)).
        andExpect(status().isOk());
    }




    
    
}
