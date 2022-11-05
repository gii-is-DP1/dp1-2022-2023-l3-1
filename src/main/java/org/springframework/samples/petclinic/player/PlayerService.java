package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.user.AuthoritiesService;

@Service
public class PlayerService {
    
    private PlayerRepository playerRepo;

    @Autowired
    private UserService userService;

    @Autowired
	private AuthoritiesService authoritiesService;

    @Autowired
    public PlayerService (PlayerRepository playerRepo){
        this.playerRepo = playerRepo;
    }

    @Transactional(readOnly = true)
    List<Player> getPlayers(){
        return playerRepo.findAll();
    }
    
    @Transactional(readOnly = true)
    public Player getById(int id){
        return playerRepo.findById(id).get();
    }

    @Transactional
	public void savePlayer(Player player) {
		//creating owner
		playerRepo.save(player);		
		//creating user
		userService.saveUser(player.getUser());
        //creating authorities
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}		
}
