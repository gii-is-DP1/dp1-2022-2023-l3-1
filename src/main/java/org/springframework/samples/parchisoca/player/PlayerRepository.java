package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player,Integer> {
    
    List<Player> findAll();
    @Query("SELECT DISTINCT player FROM Player player, IN (player.user) AS user WHERE user.username LIKE :username%")
    public Collection<Player> findByUsername(@Param("username") String username);

}
