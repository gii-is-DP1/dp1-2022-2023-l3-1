package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.parchisoca.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.parchisoca.notification.Notification;
import org.springframework.samples.parchisoca.notification.NotificationService;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.samples.parchisoca.user.AuthoritiesService;

@Service
public class    PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StatService statService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public PlayerService(PlayerRepository playerRepo) {
        this.playerRepository = playerRepo;
    }

    @Transactional(readOnly = true)
    public List<Player> findPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Player> findPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
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
        statService.initStats(player);
        notificationService.initNotifications(player);
        playerRepository.save(player);
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

    @Transactional
    public void makeFriends(Player player1, Player player2) {
        player1.getFriends().add(player2);
        player2.getFriends().add(player1);
        savePlayer(player1);
        savePlayer(player2);
    }

    @Transactional
    public void deleteNotification(Player player, Notification notification) {
        List<Notification> playerNotifications = notificationService.findNotificationsByPlayer(player);
        playerNotifications.remove(notification);
        player.setNotifications(playerNotifications);
        playerRepository.save(player);
    }

    public void save(Player player) {
        playerRepository.save(player); 
        }

    public Player getCurrentPlayer(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Player currentPlayer = playerRepository.getByUsername(username);
        return currentPlayer;
    }
}
