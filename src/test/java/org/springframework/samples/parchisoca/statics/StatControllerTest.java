package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.StatController;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @WithMockUser
    @Test
    public void testPlayerStats() throws Exception {
        mockMvc.perform(get("/stats/playerStats")).
            andExpect(status().isOk());
    }

    
}
