package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player,Integer> {

    List<Player> findAll();

    public Page<Player> findAll(Pageable pageable);

    @Query("SELECT p FROM Player p WHERE p.user.username=?1")
    public Player getByUsername(@Param("username") String username);

    @Query("SELECT p FROM Player p WHERE p.firstName=?1")
    public Collection<Player> getByFirstName(@Param("firstName") String firstName);

    @Query("SELECT p FROM Player p WHERE p.lastName=?1")
    public Collection<Player> getByLastName(@Param("lastName") String lastName);

    @Query("SELECT p FROM Player p LEFT JOIN p.achievements o WHERE p.user.username=?1")
    List<Player> getUserAchievements(@Param("username") String username);

    @Query("SELECT p FROM Player p LEFT JOIN p.achievements o WHERE p.id=?1")
    Player getUserAchievementsId(int id);

    @Query("SELECT p.id FROM Player p WHERE p.user.username=?1")
    Integer getIdByName(String name);

    @Query("SELECT p FROM Player p WHERE p.id=?1")
    Player getPlayerById(int id);
}
