package org.springframework.samples.parchisoca.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.junit.jupiter.api.Test;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers=OcaBoardController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class OcaboardControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OcaBoardService ocaBoardService;

    @MockBean
    private GameService gameService;

    @MockBean
    private OcaPieceService ocaPieceService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private StatService statService;

  

    @WithMockUser
    @Test
    public void testBoard() throws Exception {
        mockMvc.perform(get("/boards/ocaBoard/{ocaBoardId}",1)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testExitPlayerGame() throws Exception {
        String code = "ASDFG";
        mockMvc.perform(get("/games/lobby/{code}/exit",code)).
        andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testRollDice() throws Exception {
        mockMvc.perform(get("/boards/ocaBoard/{ocaBoardId}/dice",1)).
        andExpect(status().isOk());
    }






       

}
