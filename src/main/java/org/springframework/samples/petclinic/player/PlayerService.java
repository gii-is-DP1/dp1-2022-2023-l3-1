package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    PlayerRepository repo;

    public PlayerService (PlayerRepository repo){
        this.repo = repo;
    }

    List<Player> getPlayers(){
        return repo.findAll();
    }
    
    public Player getById(int id){
        return repo.findById(id).get();
        
    }
}
