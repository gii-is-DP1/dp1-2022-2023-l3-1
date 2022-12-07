package org.springframework.samples.parchisoca.statistic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends CrudRepository<Stat,Integer> {
    
    @Query("SELECT s FROM Stat s WHERE s.player =?1")
    public Stat getStatsByPlayer(Player player);

}
