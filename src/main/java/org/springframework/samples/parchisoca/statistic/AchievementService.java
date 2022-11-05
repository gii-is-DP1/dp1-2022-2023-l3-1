package org.springframework.samples.parchisoca.statistic;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AchievementService  {
    
    AchievementRepository repo;

    @Autowired
    public AchievementService(AchievementRepository repo){
        this.repo=repo;
    }

    List<Achievement> getAchievements(){
        return repo.findAll();
    }

    public void deleteAchievementByid(int id){
        repo.deleteById(id);
    }

    public Achievement getById(int id){
        return repo.findById(id).get();
    }

    public void save(Achievement achievement){
        repo.save(achievement);
    }
}
