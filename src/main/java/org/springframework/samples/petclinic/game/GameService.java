package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    GameRepository repository;

    @Autowired
    public GameService(GameRepository repository ){
        this.repository = repository;
    }
    
    List<Game> getGames(){
        return repository.findAll();
    }
}
