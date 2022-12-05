package org.springframework.samples.parchisoca.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/stats")
public class StatController {

    private final String PLAYER_STATS = "/stats/PlayerStats";

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

}
