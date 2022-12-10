package org.springframework.samples.parchisoca.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Notification findById(Integer notificationId) {
        Notification result = notificationRepository.getById(notificationId);
        return result;
    }
    
    @Transactional
    public void sendNotification(Player player, String message) {
        List<Notification> playerNotifications = player.getNotifications();
        Notification newNotification = new Notification();
        newNotification.setPlayer(player);
        newNotification.setText(message);
        notificationRepository.save(newNotification);
        playerNotifications.add(newNotification);
    }

    @Transactional
    public void sendFriendRequest(Player player, String message, Integer playerId) {
        List<Notification> playerNotifications = player.getNotifications();
        Notification newNotification = new Notification();
        newNotification.setFriendRequest(true);
        newNotification.setPlayer(player);
        newNotification.setText(message);
        newNotification.setSender(playerId);
        notificationRepository.save(newNotification);
        playerNotifications.add(newNotification);
    }

    @Transactional
    public void initNotifications(Player player) {
        List<Notification> notifications = new ArrayList<>();
        player.setNotifications(notifications);
    }

    @Transactional
    public List<Notification> findNotificationsByPlayer(Player player) {
        List<Notification> result = notificationRepository.getNotificationsByPlayer(player);
        return result;
    }

    @Transactional
    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }
}
