package org.springframework.samples.parchisoca.statics;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.samples.parchisoca.statistic.AchievementRepository;
import org.springframework.samples.parchisoca.statistic.AchievementService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementTest {
    @Autowired
    AchievementService as;

    @Autowired
    AchievementRepository ar;

    @Test
    public void testAchievementPropierties(){
        testConstraints();
    }

    private void testConstraints() {
        Achievement a = new Achievement();
        a.setId(4);
        a.setBadgeImage("https://bit.ly/proGamer");
        a.setDescription("Si ganas 50 o más partidas es que eres todo un crack.");
        a.setName("a");
        a.setThreshold(30.0);

        assertThrows(ConstraintViolationException.class,() -> ar.save(a),
        "You are not constraining achivement name can not be less than 3 ");

        Achievement a2 = new Achievement();
        a2.setId(6);
        a2.setBadgeImage("https://bit.ly/proGamer");
        a2.setDescription("Si ganas 50 o más partidas es que eres todo un crack.");
        a2.setName("Un nombre muy largo para obtener un error de ConstraintViolationException en la clase Achievement Test de nuestro proyecto");
        a2.setThreshold(30.0);

        assertThrows(ConstraintViolationException.class,() -> ar.save(a2),
        "You are not constraining achivement name can not be more than 50");

        assertTrue(!a.getDescription().isBlank());
        assertTrue(!a2.getBadgeImage().isEmpty());
        assertTrue(a.getThreshold() == 30.0 );
        assertTrue(!a2.getActualDescription().isBlank());
        
    }



    
}
