package org.springframework.samples.petclinic.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableroRepository extends CrudRepository<board,Integer> {
    
}
