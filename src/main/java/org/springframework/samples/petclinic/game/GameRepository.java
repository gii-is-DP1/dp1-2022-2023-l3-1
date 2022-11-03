package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game,Integer> {

    List<Game> findAll();

    @Query("SELECT p FROM GameType p")

    List<GameType> getAllGameTypes();

    //GameType findByName(String name);
    
    @Query("SELECT p FROM GameType p WHERE p.name=?1")
    GameType findGameType(String nombre);

    // @Query("SELECT p FROM GAME_PLAYERS p ")
    // Set<Player> getJugadores();





    
}
