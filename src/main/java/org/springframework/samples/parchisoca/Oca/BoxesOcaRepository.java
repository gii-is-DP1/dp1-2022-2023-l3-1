package org.springframework.samples.parchisoca.Oca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxesOcaRepository extends CrudRepository<BoxesOca, Integer> {
    
    @Query("SELECT b from BoxesOca b WHERE b.ocaBoard.id = ?1 AND b.positionBoard = ?2 ")
    public BoxesOca getBoxByPosition(Integer ocaBoardId, Integer position);

}
