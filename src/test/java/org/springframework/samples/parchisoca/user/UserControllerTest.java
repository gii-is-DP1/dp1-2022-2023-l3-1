package org.springframework.samples.parchisoca.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers=UserController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PlayerService playerService;

    @WithMockUser
    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/users/new")).
            andExpect(status().isOk()).
            andExpect(view().name("players/createPlayerForm")).
            andExpect(model().attributeExists("player"));
    
    }

    @WithMockUser
    @Test
    public void testPostInitCreationForm() throws Exception {
        mockMvc.perform(post("/users/new")
		.with(csrf()))
		.andExpect(status().isOk());
	}
   
}
