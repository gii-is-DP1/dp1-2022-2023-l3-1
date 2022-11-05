package org.springframework.samples.petclinic.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcaBoardRepository extends CrudRepository<OcaBoard,Integer> {
    
}
