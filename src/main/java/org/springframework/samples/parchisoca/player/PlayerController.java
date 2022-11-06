package org.springframework.samples.parchisoca.player;


import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final String MESSAGE = "message";
    private final String PLAYER_NOT_FOUND = "Jugador no encontrado";

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping(value = "/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("player", new Player());
		return "players/findPlayer";
	}

    //Falta la evaluacion del resultado del find


    @GetMapping("list")
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

    @GetMapping("/{playerId}")
        public ModelAndView showPlayer(@PathVariable("playerId") int playerId){
        ModelAndView mav = new ModelAndView("players/playerProfiles");
        Optional<Player> player = this.playerService.findPlayerById(playerId);
        if(player.isPresent()){
            mav.addObject(player.get());
        }else{
            mav.addObject(MESSAGE, PLAYER_NOT_FOUND);
        }
        return mav;
    }

    @GetMapping()
    public String findPlayers(Player player, BindingResult result, ModelMap modelMap){
        String view = "players/playersListing";
        if(player.getUser().getUsername() == ""){
            Iterable<Player> results = playerService.getPlayers();
            modelMap.addAttribute("players", results);
            return view;
        }
        Collection<Player> results = playerService.findPlayerByUsername(player.getUser().getUsername());
        if(results.isEmpty()){
            result.rejectValue("username", "notFound", "not found");
            return "players/findPlayer";
        }else if(results.size() == 1){
            player = results.iterator().next();
            return "redirect:/players/" + player.getId();
        }else{
            modelMap.put("selections", results);
            return view;
        }
    }


}
