package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.parchisoca.user.Authorities;
import org.springframework.samples.parchisoca.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.parchisoca.badWord.BadWords;
import org.springframework.samples.parchisoca.badWord.BadWordsService;
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
    List<Player> findPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Player> findPlayerById(int id) throws DataAccessException {
        return playerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Player findPlayersByUsername(String username) throws DataAccessException {
        return playerRepository.getByUsername(username);
    }

    @Transactional(readOnly = true)
    public Collection<Player> findPlayerByFirstName(String firstName) throws DataAccessException {
        return playerRepository.getByFirstName(firstName);
    }

    @Transactional(readOnly = true)
    public Collection<Player> findPlayerByLastName(String lastName) throws DataAccessException {
        return playerRepository.getByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public Player findById(int id) {
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
        authoritiesService.saveAuthorities(player.getUser().getId(), "player", player.getUser().getUsername());
    }

    @Transactional
    public List<Player> getUserAchievments(String firstName){
        return playerRepository.getUserAchievements(firstName);
    }

    @Transactional
    public Integer getUserIdByName(String name){
        return playerRepository.getIdByName(name);
    }

    @Transactional
    public Player getUserAchievement(int id){
        return playerRepository.getUserAchievementsId(id);
    }

    public void save(Player player) { playerRepository.save(player); }

}
