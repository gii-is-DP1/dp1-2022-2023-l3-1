package org.springframework.parchis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxesParchisRepository extends CrudRepository<BoxesParchis, Integer> {
    
}
