package org.springframework.samples.parchisoca.board;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.player.Player;

@Repository
public interface ParchisBoardRepository extends CrudRepository<ParchisBoard, Integer> {

    @Query("SELECT d FROM ParchisDice d WHERE d.player =?1 AND d.parchisBoard =?2")
    List<ParchisDice> getParchisDiceByPlayer(@Param("player") Player player, @Param("parchisBoard") ParchisBoard parchisBoard);
    
}
