package org.springframework.samples.parchisoca.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerRepository;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PlayerService playerService;

    private final String PLAYER_NOTIFICATIONS = "notifications/myNotifications";

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value="/myNotifications")
    public ModelAndView playerNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

        ModelAndView mav = new ModelAndView(PLAYER_NOTIFICATIONS);
        List<Notification> notifications = notificationService.findNotificationsByPlayer(currentPlayer);

        mav.addObject("notifications", notifications);
        return mav;
    }

    @GetMapping(value="/{notificationId}/delete")
    public String deleteNotification(@PathVariable("notificationId") Integer notificationId) {
        Notification notification = notificationService.findById(notificationId);
        Player player = playerService.findById(notification.getPlayer().getId());

        playerService.deleteNotification(player, notification);
        notificationService.deleteNotification(notification);

        return "redirect:/notifications/myNotifications";
    }
    
}
