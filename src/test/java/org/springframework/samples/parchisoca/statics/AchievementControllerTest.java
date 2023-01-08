package org.springframework.samples.parchisoca.statics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.parchisoca.configuration.SecurityConfiguration;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.statistic.AchievementController;
import org.springframework.samples.parchisoca.statistic.AchievementService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers=AchievementController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class AchievementControllerTest {

    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AchievementService achievementService;

    @MockBean
    private PlayerService playerService;

    
    @WithMockUser
    @Test
    void testShowAchievements() throws Exception {
        mockMvc.perform(get("/statistics/achievements/")).
            andExpect(status().isOk()).
            andExpect(view().name("/achievements/AchievementsListing")).
            andExpect(model().attributeExists("achievements"));
    }

    @WithMockUser
    @Test
    void testShowUserAchievements() throws Exception {
        when(playerService.getUserIdByName(anyString())).thenReturn(1);
        when(playerService.getUserAchievement(anyInt())).thenReturn(new Player());
        mockMvc.perform(get("/statistics/achievements/user")).
            andExpect(status().isOk()).
            andExpect(view().name("/achievements/AchievementsListingUser")).
            andExpect(model().attributeExists("players"));
    }
    
    @Test
    @WithMockUser
    void testDeleteAchievements() throws Exception {
        mockMvc.perform(get("/statistics/achievements/{id}/delete", 1))
        .andExpect(view().name("/achievements/AchievementsListing"));
    }

    @Test
    @WithMockUser
    void testEditAchievements() throws Exception {
        when(achievementService.getById(anyInt())).thenReturn(new Achievement());
            mockMvc.perform(get("/statistics/achievements/{id}/edit", 1))
            .andExpect(view().name("/achievements/createOrUpdateAchievementForm")).
            andExpect(model().attributeExists("achievement"));
    }

    @Test
    @WithMockUser
    void testCreateAchievements() throws Exception {
        mockMvc.perform(get("/statistics/achievements/new")).
        andExpect(status().isOk()).
        andExpect(view().name("/achievements/createOrUpdateAchievementForm")).
        andExpect(model().attributeExists("achievement"));
    }

    @Test
    @WithMockUser
    void testAccessDenied() throws Exception {
        mockMvc.perform(get("/accessdenied")).
        andExpect(status().isOk());
    }




    


}