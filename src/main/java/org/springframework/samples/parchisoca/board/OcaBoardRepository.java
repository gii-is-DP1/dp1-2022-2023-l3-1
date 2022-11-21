package org.springframework.samples.parchisoca.board;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface OcaBoardRepository extends CrudRepository<OcaBoard,Integer> {
    
    @Query("SELECT d FROM OcaDice d WHERE d.player =?1 AND d.ocaBoard =?2")
    OcaDice getOcaDiceByPlayer(@Param("player") Player player, @Param("ocaBoard") OcaBoard ocaBoard);

}
