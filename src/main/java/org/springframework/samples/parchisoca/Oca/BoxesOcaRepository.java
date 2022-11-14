package org.springframework.samples.parchisoca.oca;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxesOcaRepository extends CrudRepository<BoxesOca, Integer> {
    
}
