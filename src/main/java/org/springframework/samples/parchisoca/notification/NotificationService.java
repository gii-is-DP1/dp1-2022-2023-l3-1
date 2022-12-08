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
    public NotificationService (NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Transactional
    public Notification sendNotification (Player player, String message) {
        return notificationRepository.getNotificationByPlayer(player);
    }

    @Transactional
    public void initNotifications(Player player) {
        Notification notification = new Notification();
        notification.setText("Welcome!");
        List<Notification> notis = new ArrayList<>();
        notification.setPlayer(player);
        notificationRepository.save(notification);
        notis.add(notification);
        player.setNotifications(notis);
    }

    @Transactional
    public List<Notification> findNotificationsByPlayer(Player player) {
        List<Notification> result = notificationRepository.getNotificationsByPlayer(player);
        return result;
    }
}
