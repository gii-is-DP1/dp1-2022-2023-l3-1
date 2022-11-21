package org.springframework.samples.parchisoca.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game,Integer> {

    List<Game> findAll();

    @Query("SELECT g FROM Game g WHERE g.id =?1")
    Game getGameById(int id);

    @Query("SELECT p FROM GameType p")
    List<GameType> getAllGameTypes();
    
    @Query("SELECT p FROM GameType p WHERE p.name=?1")
    GameType findGameType(String nombre);

    @Query("SELECT g FROM Game g WHERE g.privacity LIKE 'PUBLIC' ")
    List<Game> getPublicGames();

    @Query("SELECT g FROM Game g WHERE g.privacity LIKE 'PUBLIC' AND g.inProgress LIKE 'TRUE'")
    List<Game> getPublicGamesNotFinished();

    @Query("SELECT g FROM Game g WHERE g.inProgress LIKE 'FALSE'")
    List<Game> getGamesFinished();

    @Query("SELECT g FROM Game g WHERE g.inProgress LIKE 'TRUE'")
    List<Game> getGamesInProgress();
    
    @Query("SELECT g FROM Game g WHERE g.code=?1")
    Game getGameByCode(String code);

}
