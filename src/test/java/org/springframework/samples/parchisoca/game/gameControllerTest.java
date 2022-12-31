package org.springframework.samples.parchisoca.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//Añadiremos esta etiqueta a la clase para poder lanzar el test de forma aislada.
    //estas propiedades excluye las propiedades de seguridad de spring
@WebMvcTest(controllers=GameController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class gameControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private OcaBoardService ocaBoardService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private ParchisBoardService parchisBoardService;
   
    @WithMockUser
    @Test
    public void testGameListing() throws Exception {
        mockMvc.perform(get("/games/list")).
            andExpect(status().isOk()).
            andExpect(view().name("/games/GamesListing")).
            andExpect(model().attributeExists("games"));
    
    }

    @WithMockUser
    @Test
    public void testGameCreate() throws Exception {
        mockMvc.perform(get("/games/create")).
            andExpect(status().isOk()).
            andExpect(view().name("/lobbys/createLobby")).
            andExpect(model().attributeExists("game")).
            andExpect(model().attributeExists("gameTypes"));
    }

    @WithMockUser
    @Test
    public void testLobby() throws Exception {
        mockMvc.perform(get("/games/lobbys")).
            andExpect(status().isOk()).
            andExpect(view().name("games/GamePublic")).
            andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testgamesPlayed() throws Exception {
        mockMvc.perform(get("/games/admin/lobbys/played")).
            andExpect(status().isOk()).
            andExpect(view().name("games/GamesPlayed")).
            andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testGamesInProgress() throws Exception {
        mockMvc.perform(get("/games/admin/lobbys/inProgress")).
            andExpect(status().isOk()).
            andExpect(view().name("games/GamesInProgress")).
            andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testInstructionsParchis() throws Exception {
        mockMvc.perform(get("/games/instructions/parchisInstructions")).
            andExpect(status().isOk()).
            andExpect(view().name("games/ParchisInstructions"));
    }

    @WithMockUser
    @Test
    public void testInstructionsOca() throws Exception {
        mockMvc.perform(get("/games/instructions/ocaInstructions")).
            andExpect(status().isOk()).
            andExpect(view().name("games/OcaInstructions"));
    }


}
