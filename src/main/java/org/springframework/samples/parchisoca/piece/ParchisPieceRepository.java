package org.springframework.samples.parchisoca.piece;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface ParchisPieceRepository extends CrudRepository<ParchisPiece, Integer> {

    @Query("SELECT p from ParchisPiece p WHERE p.player = ?1 AND p.parchisBoard = ?2")
    public List<ParchisPiece> getParchisPiecesByPlayerParchisBoard(Player player, ParchisBoard parchisBoard);
}