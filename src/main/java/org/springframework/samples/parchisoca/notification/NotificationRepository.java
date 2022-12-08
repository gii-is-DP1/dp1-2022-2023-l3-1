package org.springframework.samples.parchisoca.notification;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    
    @Query("SELECT n FROM Notification n WHERE n.player =?1")
    Notification getNotificationByPlayer(Player player);
}
