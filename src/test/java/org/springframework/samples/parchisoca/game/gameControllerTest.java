package org.springframework.samples.parchisoca.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

//Añadiremos esta etiqueta a la clase para poder lanzar el test de forma aislada.
@WebMvcTest(controllers=GameController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
    //estas propiedades excluye las propiedades de seguridad de spring
public class gameControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private OcaBoardService ocaBoardService;

    @MockBean
    private PlayerService playerService;
    
    // @WithMockUser
    // @Test
    // public void testGameListing() throws Exception {
    //     mockMvc.perform(get("/games/list")).
    //     andExpect(status().isOk()).
    //     andExpect(view().name("/games/GamesListing"))
    //     .andExpect(model().attributeExists("games"));
    
    // }


    // @WithMockUser
    // @Test
    // public void testGetLobby() throws Exception{
    //     mockMvc.perform(get("/games/lobbys")).
    //         andExpect(status().isOk()).
    //         andExpect(view().name("games/GamePublic"));
    // }

    
}
