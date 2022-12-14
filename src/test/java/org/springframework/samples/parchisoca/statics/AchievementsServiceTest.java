package org.springframework.samples.parchisoca.statics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.statistic.AchievementRepository;
import org.springframework.samples.parchisoca.statistic.AchievementService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementsServiceTest {

    @Autowired(required = false)
    AchievementService as;

    @Autowired(required = false)
    AchievementRepository ar;


    @Test
    void shouldFindByIdAchievements(){
        Achievement achievement = as.getById(1);
        List<Achievement> ls = new ArrayList<>();
        ls.add(achievement);
        assertThat(ls.size() == 1);
    }

    @Test
    void shouldCreateNewAchievement(){
        Achievement a = new Achievement();
        a.setBadgeImage("https://bit.ly/proGamer");
        a.setDescription("Si ganas 50 o más partidas es que eres todo un crack.");
        a.setName("Excelso");
        a.setThreshold(30.0);

        as.save(a);
        Achievement achievement = as.getById(a.getId());
        List<Achievement> ls = new ArrayList<>();
        ls.add(achievement);
        assertThat(ls.size() == 1);
        assertTrue(a.toString().equals(a.getName()));
    }

    @Test
    void shouldDeleteAchievement(){
        Achievement a = new Achievement();
        a.setBadgeImage("https://bit.ly/proGamer");
        a.setDescription("Si ganas 50 o más partidas es que eres todo un crack.");
        a.setName("Excelso");
        a.setThreshold(30.0);
        as.save(a);

        
        Achievement eliminado = as.getById(a.getId());
        as.deleteAchievementByid(a.getId());

        List<Achievement> ls = as.getAchievements();
         
       

        assertTrue(!ls.contains(eliminado));
    }

    
}
