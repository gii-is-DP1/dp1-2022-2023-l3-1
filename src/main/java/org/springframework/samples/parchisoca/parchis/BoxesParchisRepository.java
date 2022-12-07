package org.springframework.samples.parchisoca.parchis;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxesParchisRepository extends CrudRepository<BoxesParchis, Integer> {
    
    @Query("SELECT b from BoxesParchis b WHERE b.positionBoard = ?1 AND b.parchisBoard = ?2")
    public BoxesParchis getBoxByPosition(Integer positionBoard, ParchisBoard parchisBoard);

}
