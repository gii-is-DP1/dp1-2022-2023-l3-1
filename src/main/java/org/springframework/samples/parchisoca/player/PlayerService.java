package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.parchisoca.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.parchisoca.user.AuthoritiesService;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    private UserService userService;

    @Autowired
	private AuthoritiesService authoritiesService;

    @Autowired
    public PlayerService (PlayerRepository playerRepo){
        this.playerRepository = playerRepo;
    }

    @Transactional(readOnly = true)
    List<Player> getPlayers(){
        return playerRepository.findAll();
    }

     @Transactional(readOnly = true)
     public Optional<Player> findPlayerById(int id) throws DataAccessException{
        return playerRepository.findById(id);
     }

     @Transactional(readOnly = true)
     public Collection<Player> findPlayerByUsername(String username) throws DataAccessException{
        return playerRepository.findByUsername(username);
     }

    @Transactional(readOnly = true)
    public Player getById(int id){
        return playerRepository.findById(id).get();
    }

    @Transactional
	public void savePlayer(Player player) {
		//creating owner
		playerRepository.save(player);
		//creating user
		userService.saveUser(player.getUser());
        //creating authorities
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}
}
