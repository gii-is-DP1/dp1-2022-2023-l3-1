package org.springframework.samples.parchisoca.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.parchisoca.player.Player;

import java.util.Optional;


public interface UserRepository extends  CrudRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE u.username=?1")
    public Optional<User> getByUsername(@Param("username") String username);

}
