package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.parchisoca.statistic.Achievement;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player,Integer> {
    
    List<Player> findAll();
    @Query("SELECT DISTINCT player FROM Player player, IN (player.user) AS user WHERE user.username LIKE :username%")
    public Collection<Player> findByUsername(@Param("username") String username);

    @Query("SELECT P FROM Player P WHERE P.firstName = ?1")
    public Collection<Player> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT p FROM Player p WHERE p.lastName = ?1")
    public Collection<Player> findByLastName(@Param("lastName") String lastName);


    @Query("SELECT p FROM Player p LEFT JOIN p.achievements o WHERE p.firstName LIKE :firstName%")
    Set<Achievement> findUserAchievements(@Param("firstName") String firstName);

}
