package org.springframework.samples.parchisoca.player;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    private final String PLAYERS_LISTING_VIEW= "players/playersListing";
    private final String CREATE_PLAYERS = "players/createPlayerForm";
    private final String EDIT_PLAYER = "players/editPlayer";
    private final String LOGGED_USER_VIEW = "players/myProfile";
    private final String PLAYER_PROFILES = "players/playerProfiles";
    private final String FIND_PLAYER_VIEW = "players/findPlayer";
    private final String EDIT_MY_PROFILE = "players/editMyProfile";
    private final String MESSAGE = "message";
    private final String ERROR = "error";
    private final String PLAYER_NOT_FOUND = "Player not found";

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping(value = "/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("player", new Player());
		return FIND_PLAYER_VIEW;
	}

    @GetMapping("/list")
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
        public ModelAndView showPlayer(@PathVariable("playerId") int playerId) {
        ModelAndView mav = new ModelAndView(PLAYER_PROFILES);
        Optional<Player> player = this.playerService.findPlayerById(playerId);
        if (player.isPresent()) {
            mav.addObject(player.get());
        } else {
            mav.addObject(MESSAGE, PLAYER_NOT_FOUND);
        }
        return mav;
    }

    @GetMapping("/{playerId}/edit")
    public ModelAndView editPlayer(@PathVariable("playerId") int playerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView result = null;
        Player player = playerService.getById(playerId);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            result = new ModelAndView(EDIT_PLAYER);
            result.addObject("player", player);
            return result;
        }
        String username = auth.getName();
        Integer id = this.playerService.getUserIdByName(username);
        Player loggedPlayer = this.playerService.findPlayerById(id).get();
        if ((loggedPlayer == player) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            result = new ModelAndView(EDIT_PLAYER);
            result.addObject("player", player);
            return result;
        } else {
            result.addObject(MESSAGE, PLAYER_NOT_FOUND);
            return result;
        }

    }

    @PostMapping("/{playerId}/edit")
    public String savePlayer(@PathVariable("playerId") int playerId, Player player){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            String username = auth.getName();
            Integer id = this.playerService.getUserIdByName(username);
            Player loggedPlayer = this.playerService.findPlayerById(id).get();
            Player playerToBeUpdated = playerService.getById(playerId);
            BeanUtils.copyProperties(player,playerToBeUpdated,"id","achievements", "user");
            playerService.savePlayer(playerToBeUpdated);
            if (loggedPlayer == playerToBeUpdated){
                return "redirect:/players/myProfile";
            }else{
                return "redirect:/players/{playerId}";
            }
        }else {
            Player playerToBeUpdated = playerService.getById(playerId);
            BeanUtils.copyProperties(player,playerToBeUpdated,"id","achievements", "user");
            playerService.savePlayer(playerToBeUpdated);
            return "redirect:/players/{playerId}";
        }

    }

    @GetMapping("/{playerId}/delete")
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayerById(playerId);
        return "redirect:/players/list";
    }

    @GetMapping("/myProfile")
    public ModelAndView showLoggedUser(){
        ModelAndView mav = new ModelAndView(LOGGED_USER_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = this.playerService.getUserIdByName(username);
        Optional<Player> player = this.playerService.findPlayerById(id);
        if(player.isPresent()){
            mav.addObject(player.get());
        }else{
            mav.addObject(MESSAGE, PLAYER_NOT_FOUND);
        }
        return mav;
    }

    @GetMapping()
    public String findPlayers(Player player, BindingResult result, ModelMap modelMap){
        if(player.getUser().getUsername() == "") {

            return FIND_PLAYER_VIEW;
        }
        Collection<Player> results = playerService.findPlayersByUsername(player.getUser().getUsername());
        if(results.isEmpty()){
            result.rejectValue("username", "notFound", "not found");
            return FIND_PLAYER_VIEW;
        }else if(results.size() == 1) {
            player = results.iterator().next();
            return "redirect:/players/" + player.getId();
        } else {
            modelMap.put("selections", results);
            return PLAYERS_LISTING_VIEW;
        }
    }


}
