package org.springframework.samples.parchisoca.piece;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParchisPieceRepository extends CrudRepository<ParchisPiece, Integer> {

}