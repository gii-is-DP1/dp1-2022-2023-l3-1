package org.springframework.samples.parchisoca.statistic;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService  {
    
    AchievementRepository repo;

    @Autowired
    public AchievementService(AchievementRepository repo){
        this.repo=repo;
    }

    @Transactional(readOnly = true)
    List<Achievement> getAchievements(){
        return repo.findAll();
    }

    @Transactional
    public void deleteAchievementByid(int id){
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Achievement getById(int id){
        return repo.findById(id).get();
    }

    @Transactional
    public void save(Achievement achievement){
        repo.save(achievement);
    }
}
