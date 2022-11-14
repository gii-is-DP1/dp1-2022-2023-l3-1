package org.springframework.samples.parchisoca.Oca;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxesOcaRepository extends CrudRepository<BoxesOca, Integer> {
    
}
