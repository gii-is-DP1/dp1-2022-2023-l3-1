package org.springframework.samples.parchisoca.board;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ParchisBoardRepository extends CrudRepository<ParchisBoard, Integer> {
    
}
