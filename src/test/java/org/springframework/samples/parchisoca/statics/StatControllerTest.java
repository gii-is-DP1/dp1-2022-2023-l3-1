package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.Stat;
import org.springframework.samples.parchisoca.statistic.StatController;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers=StatController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class StatControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private StatService statService;

    @MockBean
    private PlayerService playerService;

    private Player playerTest1 = new Player();
    private Player playerTest2 = new Player();

    @BeforeEach
    void setUp(){
        playerTest1 = playerService.findById(1);
        playerTest2 = playerService.findById(2);
    }

    @WithMockUser
    @Test
    public void testPlayerStats() throws Exception {
        when(statService.findStatsByPlayer(playerTest1)).thenReturn(new Stat());
        mockMvc.perform(get("/stats/playerStats")).
            andExpect(status().isOk()). 
            andExpect(view().name("/stats/PlayerStats")).
            andExpect(model().attributeExists("stats")).
            andExpect(model().attributeExists("ratio"));
    }

    // @Test
    // public void testFriendStats() throws Exception {
    //     // Arrange
    //     when(playerService.findPlayersByUsername("pajaro")).thenReturn(playerTest2);
    //     when(playerService.findById(1)).thenReturn(playerTest1);
    //     Stat playerStats = new Stat();
    //     when(statService.findStatsByPlayer(playerTest1)).thenReturn(playerStats);
    
    //     // Act
    //     mockMvc.perform(get("/stats/{playerId}", 1)).
    //     andExpect(view().name("/stats/PlayerStats")).
    //     andExpect(model().attributeExists("message")).
    //     andExpect(model().attributeExists("friends"));
    // }

}
