package org.springframework.samples.parchisoca.dice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParchisDiceRepository extends CrudRepository<ParchisDice, Integer> {

}
