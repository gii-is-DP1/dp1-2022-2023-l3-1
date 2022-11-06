package org.springframework.samples.parchisoca.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public GameType getGameType(String name) {
        return repository.findGameType(name);
    }

    @Transactional(readOnly = true)
    public List<GameType> findAllGameTypes() {
        return repository.getAllGameTypes();
    }

}


