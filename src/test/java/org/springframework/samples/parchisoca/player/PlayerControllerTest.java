package org.springframework.samples.parchisoca.player;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.badWord.BadWordsService;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.notification.NotificationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//Añadiremos esta etiqueta a la clase para poder lanzar el test de forma aislada.
    //estas propiedades excluye las propiedades de seguridad de spring
@WebMvcTest(controllers=PlayerController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private BadWordsService badWordsService;

    @MockBean
    private NotificationService notificationService;

    Player p = new Player();


    @WithMockUser
    @Test
    public void testInitFindForm() throws Exception {
        mockMvc.perform(get("/players/find")).
            andExpect(status().isOk()).
            andExpect(view().name("players/findPlayer")).
            andExpect(model().attributeExists("player"));
    
    }

    @Test
	@WithMockUser
	void testDeletePlayer() throws Exception {
		mockMvc.perform(get("/players/{playerId}/delete",1)).andExpect(status().is3xxRedirection());
	}

    // @Test
	// @WithMockUser
	// void testShowPlayers() throws Exception {
	// 	mockMvc.perform(get("/players/list")).
    //     andExpect(status().isOk()).
    //     andExpect(view().name("players/playersListing")).
    //     andExpect(model().attributeExists("number")).
    //     andExpect(model().attributeExists("size")).
    //     andExpect(model().attributeExists("pages")).
    //     andExpect(model().attributeExists("totalPages")).
    //     andExpect(model().attributeExists("totalElements")).
    //     andExpect(model().attributeExists("data")).
    //     andExpect(model().attributeExists("list"));


	// }

    @Test
	@WithMockUser
	void testCreatePlayerAdmin() throws Exception {
		mockMvc.perform(get("/admin/players/create")).
        andExpect(status().isOk()).
        andExpect(model().attributeExists("player"));
	}

    @Test
	@WithMockUser
	void testProcessAdminCreationForm() throws Exception {
        mockMvc.perform(
            post("/admin/players/create")
                .param("name", "Prueba")
                .with(csrf())
        ).andExpect(status().isOk()).andExpect(view().name("players/createPlayerForm"));
        
	}

    // @Test
	// @WithMockUser
	// void testProcessAdminCreationForm1() throws Exception {
    //     when(badWordsService.checkPlayerBadWords(p)).thenReturn(true);
    //     mockMvc.perform(
    //         post("/admin/players/create")
    //             .with(csrf())).
    //             andExpect(status().isOk()).
    //             andExpect(view().name("players/createPlayerForm"));
        
	// }

    
    @Test
	@WithMockUser
	void testCreatePlayer() throws Exception {
		mockMvc.perform(get("/players/create")).
        andExpect(status().isOk()).
        andExpect(model().attributeExists("player"));
	}

    //Añadir post del Create Player


    @Test
	@WithMockUser
	void testShowPlayer() throws Exception {
		mockMvc.perform(get("/players/{playerId}",1)).
        andExpect(status().isOk()).
        andExpect(view().name("players/playerProfile"));
	}

    @Test
    @WithMockUser
    void testEditLoggedPlayer() throws Exception {
        Integer playerId = 1;
        when(playerService.getUserIdByName(anyString())).thenReturn(playerId);
        when(playerService.findById(anyInt())).thenReturn(new Player());
        mockMvc.perform(get("/players/{playerId}/edit",playerId))
        .andExpect(status().isOk()).
        andExpect(model().attributeExists("player")).
        andExpect(view().name("players/editPlayer"));
    }

    @Test
    @WithMockUser
    void testEditLoggedPlayerNotEqual() throws Exception {
        Integer playerId = 1;
        String MESSAGE = "message";
        when(playerService.getUserIdByName(anyString())).thenReturn(3);
        when(playerService.findById(anyInt())).thenReturn(new Player());
        mockMvc.perform(get("/players/{playerId}/edit",playerId))
        .andExpect(status().isOk()).
        andExpect(model().attributeExists(MESSAGE)).
        andExpect(view().name("players/editPlayer"));
    }

    @Test
    @WithMockUser
    void testEditPlayerAdmin() throws Exception {
        when(playerService.findById(anyInt())).thenReturn(new Player());
        mockMvc.perform(get("/admin/{playerId}/edit",1)).
        andExpect(status().isOk()).
        andExpect(model().attributeExists("player")).
        andExpect(view().name("players/editPlayer"));
    }


    



    
    
    
}
