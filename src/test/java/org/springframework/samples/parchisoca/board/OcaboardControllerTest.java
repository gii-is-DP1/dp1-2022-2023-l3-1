package org.springframework.samples.parchisoca.board;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
//Añadiremos esta etiqueta a la clase para poder lanzar el test de forma aislada.
    //estas propiedades excluye las propiedades de seguridad de spring
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

    Game g = new Game();

    @BeforeEach
    void setUp(){
        g.setCode("ASDFG");
        g.setName("GameTest");

    }

       

}
