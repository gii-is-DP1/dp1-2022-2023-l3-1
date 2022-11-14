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
    public ModelAndView editPlayer(@PathVariable("playerId") int playerId){
        Player player = playerService.getById(playerId);
        ModelAndView result=new ModelAndView(EDIT_PLAYER);
        result.addObject("player", player);
        return result;
    }

    @PostMapping("/{playerId}/edit")
    public String savePlayer(@PathVariable("playerId") int playerId, Player player){
        Player playerToBeUpdated = playerService.getById(playerId);
        BeanUtils.copyProperties(player,playerToBeUpdated,"id","achievements","user");
        playerService.savePlayer(playerToBeUpdated);
        return "redirect:/players/{playerId}";
    }

    @GetMapping("/{playerId}/delete")
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayerById(playerId);
        return "redirect:/list";
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

   @Transactional(readOnly = true)
   @GetMapping("/myProfile/edit")
   public ModelAndView editMyProfile(){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username = auth.getName();
       Integer id = this.playerService.getUserIdByName(username);
       Optional<Player> playerOptional = this.playerService.findPlayerById(id);
       Player player = playerOptional.get();
       ModelAndView result=new ModelAndView(EDIT_MY_PROFILE);
       result.addObject("player", player);
       return result;
   }

   @Transactional()
   @PostMapping("/myProfile/edit")
   public ModelAndView saveMyProfile(Player player, BindingResult br){
       ModelAndView result = null;
       if (br.hasErrors()){
           result = new ModelAndView(LOGGED_USER_VIEW);
           result.addAllObjects(br.getModel());
       }else {
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           String username = auth.getName();
           Integer id = this.playerService.getUserIdByName(username);
           Optional<Player> playerOptional = this.playerService.findPlayerById(id);
           Player playerToUpdate = playerOptional.get();
           BeanUtils.copyProperties(player, playerToUpdate, "id","email","achievments", "user.id", "user.password", "user.enabled", "user.authorities");
           playerToUpdate.setId(player.getId());
           playerService.save(playerToUpdate);
           result = showLoggedUser();
       }
       return result;
   }
//    @Transactional(readOnly = true)
//    @GetMapping(value = "/myProfile/edit")
//    public String initUpdateProfile(Model model){
//        UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = authentication.getUsername();
//        Integer playerId = this.playerService.getUserIdByName(username);
//        Optional<Player> playerOptional = this.playerService.findPlayerById(playerId);
//        Player player = playerOptional.get();
//        model.addAttribute(player);
//        return EDIT_MY_PROFILE;
//    }

//    @Transactional
//    @PostMapping(value = "/myProfile/edit")
//    public String processUpdateProfile(@Valid Player player, BindingResult result){
//        if (result.hasErrors()){
//            return EDIT_MY_PROFILE;
//        }else {
//            UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = authentication.getUsername();
//            Integer playerId = this.playerService.getUserIdByName(username);
//            Optional<Player> playerOptional = this.playerService.findPlayerById(playerId);
//            Player authPlayer = playerOptional.get();
//            player.setId(authPlayer.getId());
//            this.playerService.save(player);
//            return "redirect:/players/myProfile";
//        }
//    }

//     @GetMapping(value = "/myProfile/edit")
//     public String initUpdateLoggedProfile(ModelMap model){
//         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         UserDetails ud = null;
//         if (principal instanceof UserDetails){
//             ud = ((UserDetails) principal);
//         }
//         int playerId = this.playerService.getUserIdByName(ud.getUsername());
//         Optional<Player> optionalPlayer = this.playerService.findPlayerById(playerId);
//         Player player = optionalPlayer.get();
//         model.addAttribute(player);
//         return EDIT_MY_PROFILE;
//     }
//     @Transactional
//     @PostMapping(value = "/myProfile/edit")
//     public String processUpdateLoggedProfile(@Valid Player player, BindingResult result){
//         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         UserDetails ud = null;
//         if (principal instanceof UserDetails){
//             ud = ((UserDetails) principal);
//         }
//         int oldPlayerId = this.playerService.getUserIdByName(ud.getUsername());
//         Optional<Player> optionalOldPlayer = this.playerService.findPlayerById(oldPlayerId);
//         Player oldPlayer = optionalOldPlayer.get();
//         if (result.hasErrors()){
//             return EDIT_MY_PROFILE;
//         }else {
//             player.setId(oldPlayer.getId());
//             player.setEmail(oldPlayer.getEmail());
//             player.getUser().setPassword(oldPlayer.getUser().getPassword());
//             player.getUser().setAuthorities(oldPlayer.getUser().getAuthorities());
//             player.setAchievements(oldPlayer.getAchievements());
//             this.playerService.save(player);
//             return LOGGED_USER_VIEW;
//         }
//     }

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
