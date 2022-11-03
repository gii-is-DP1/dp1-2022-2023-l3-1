package org.springframework.samples.petclinic.player;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;
    
    private final String  PLAYERS_LISTING_VIEW="/players/PlayersListing";

    private final String CREATE_PLAYERS = "/players/createPlayerForm";

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping("/")
    public ModelAndView showPlayers(){
        ModelAndView result = new ModelAndView(PLAYERS_LISTING_VIEW);
        result.addObject("players", playerService.getPlayers());
        return result;

    } 

    @GetMapping("/create")
    public ModelAndView createPlayer(){
        ModelAndView result = new ModelAndView(CREATE_PLAYERS);
        result.addObject("player", new Player());
        return result;
    }

    @PostMapping(value = "/create")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return CREATE_PLAYERS;
		}
		else {
			//creating owner, user and authorities
			this.playerService.savePlayer(player);
			return "redirect:/welcome";
		}
	}

    

}
