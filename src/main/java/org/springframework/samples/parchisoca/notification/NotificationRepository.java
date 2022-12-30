package org.springframework.samples.parchisoca.notification;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    
    @Query("SELECT n FROM Notification n WHERE n.id =?1")
    Notification getById(Integer notificationId);

    @Query("SELECT n FROM Notification n WHERE n.player =?1")
    Notification getNotificationByPlayer(Player player);

    @Query("SELECT n FROM Notification n WHERE n.player =?1")
    List<Notification> getNotificationsByPlayer(Player player);

    @Query("SELECT n FROM Notification n")
    List<Notification> getAll();
}
