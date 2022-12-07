package org.springframework.samples.parchisoca.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/stats")
public class StatController {

    private final String PLAYER_STATS = "/stats/PlayerStats";
    private final String PLAYER_FRIENDS = "players/myFriends";

    @Autowired
    private StatService statService;

    @Autowired
    private PlayerService playerService;
    
    // Returns stats from the current user
    @GetMapping("/playerStats")
    public ModelAndView playerStats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

        Stat currentPlayerStats = statService.findStatsByPlayer(currentPlayer);
        Double ratio = currentPlayerStats.ratio(currentPlayerStats.getWonGames(), currentPlayerStats.getLostGames());
        ModelAndView mav = new ModelAndView(PLAYER_STATS);
        mav.addObject("stats", currentPlayerStats);
        mav.addObject("ratio", ratio);

        return mav;
    }

    // Returns stats from a friend
    @GetMapping("/{playerId}")
    public ModelAndView friendStats(@PathVariable("playerId") Integer playerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Player currentPlayer = playerService.findPlayersByUsername(username);
        List<Player> friends = currentPlayer.getFriends();

        Player player = playerService.findById(playerId);
        ModelAndView mav = new ModelAndView();

        if (!friends.contains(player)) {
            mav.setViewName(PLAYER_FRIENDS);
            String message = "This player is not your friend.";
            mav.addObject("message", message);
            mav.addObject("friends", friends);
        } else {
            Stat playerStats = statService.findStatsByPlayer(player);
            if (playerStats == null) {
                mav.setViewName(PLAYER_FRIENDS);
                String message = "There aren`t stats for this player yet.";
                mav.addObject("message", message);
                mav.addObject("friends", friends);
            } else {
                Double ratio = playerStats.ratio(playerStats.getWonGames(), playerStats.getLostGames());
                mav.setViewName(PLAYER_STATS);
                mav.addObject("stats", playerStats);
                mav.addObject("ratio", ratio);
            }
        }
        return mav;        
    }

}
