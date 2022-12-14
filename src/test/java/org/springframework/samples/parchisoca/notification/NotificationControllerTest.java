package org.springframework.samples.parchisoca.notification;

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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

//Añadiremos esta etiqueta a la clase para poder lanzar el test de forma aislada.
    //estas propiedades excluye las propiedades de seguridad de spring
@WebMvcTest(controllers=NotificationController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private PlayerService playerService;

    Notification notificationTest = new Notification();
    Player playerTest = new Player();

    @BeforeEach
    void setUp() {
        notificationTest.setPlayer(playerTest);
        notificationService.save(notificationTest);
    }

    @WithMockUser
    @Test
    public void testGameListing() throws Exception {
        mockMvc.perform(get("/notifications/myNotifications")).
            andExpect(status().isOk()).
            andExpect(view().name("notifications/myNotifications")).
            andExpect(model().attributeExists("notifications"));
    }

    @Test
    @WithMockUser
    void testDeleteNotification() throws Exception {
        when(notificationService.findById(anyInt())).thenReturn(notificationTest);
        when(playerService.findById(anyInt())).thenReturn(notificationTest.getPlayer());
        mockMvc.perform(post("/notifications/{notificationId}/delete", 1).with(csrf()))
            .andExpect(status().isOk());
    }
    
}
