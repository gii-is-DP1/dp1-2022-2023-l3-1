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
    public PlayerService(PlayerRepository playerRepo) {
        this.playerRepository = playerRepo;
    }

    @Transactional(readOnly = true)
    List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Player> findPlayerById(int id) throws DataAccessException {
        return playerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Player findPlayersByUsername(String username) throws DataAccessException {
        return playerRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Collection<Player> findPlayerByFirstName(String firstName) throws DataAccessException {
        return playerRepository.findByFirstName(firstName);
    }

    @Transactional(readOnly = true)
    public Collection<Player> findPlayerByLastName(String lastName) throws DataAccessException {
        return playerRepository.findByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public Player getById(int id) {
        return playerRepository.findById(id).get();
    }

    @Transactional
    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
        userService.saveUser(player.getUser());
        authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
    }

    @Transactional
    public List<Player> getUserAchievments(String firstName){
        return playerRepository.findUserAchievements(firstName);
    }

    @Transactional
    public Integer getUserIdByName(String name){
        return playerRepository.findIdByName(name);
    }

    @Transactional
    public Player getUserAchievement(int id){
        return playerRepository.findUserAchievementsId(id);
    }

}
