package org.springframework.samples.parchisoca.dice;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface ParchisDiceRepository extends CrudRepository<ParchisDice, Integer> {

    @Query("SELECT d FROM ParchisDice d WHERE d.parchisBoard = ?1 AND d.player = ?2")
    public List<ParchisDice> getParchisDiceByParchisBoardPlayer(ParchisBoard parchisBoard, Player player);
}
