package org.springframework.samples.parchisoca.player;



import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.parchisoca.badWord.BadWordsService;
import org.springframework.samples.parchisoca.notification.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BadWordsService badWordsService;

    @Autowired
    private NotificationService notificationService;

    private final String PLAYERS_LISTING_VIEW = "players/playersListing";
    private final String PLAYERS_FOUND_LISTING_VIEW = "players/playersFoundListing";
    private final String CREATE_PLAYERS = "players/createPlayerForm";
    private final String EDIT_PLAYER = "players/editPlayer";
    private final String LOGGED_USER_VIEW = "players/myProfile";
    private final String PLAYER_PROFILE = "players/playerProfile";
    private final String FRIEND_PROFILE = "players/friendProfile";
    private final String FIND_PLAYER_VIEW = "players/findPlayer";
    private final String PLAYER_FRIENDS = "players/myFriends";
    private final String MESSAGE = "message";
    private final String PLAYER_NOT_FOUND = "Player not found";

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping(value = "/players/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("player", new Player());
		return FIND_PLAYER_VIEW;
	}

    // Finds a player by its username
    @GetMapping("/players/find/{username}")
    public ModelAndView findPlayer(@PathVariable("username") String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Player currentPlayer = playerService.findPlayersByUsername(currentUsername);
        List<Player> currentPlayersFriends = currentPlayer.getFriends();

        ModelAndView mav = new ModelAndView();
        Player player = playerService.findPlayersByUsername(username);

        String direction;
        String message = "";
        List<Player> players = playerService.findPlayers();
        List<Player> playersFound = new ArrayList<>();

        if (player==null) {
            direction = FIND_PLAYER_VIEW;

            for (Player p: players) {
                String userToFind = p.getUser().getUsername();
                if (userToFind.contains(username)) {
                    playersFound.add(p);
                } else {
                    message = "There is no player known as '" + username+"'.";
                }
            } if (playersFound.size() > 0) {
                direction = PLAYERS_FOUND_LISTING_VIEW;
            }

        } else if (currentUsername.equals(username)) {
            direction = FIND_PLAYER_VIEW;

            message = "You can't search yourself.";

        } else if (currentPlayersFriends.contains(player)) {
            direction = FRIEND_PROFILE;
        } else {
            direction = PLAYER_PROFILE;
        }

        mav = new ModelAndView(direction);


        if (direction == PLAYERS_FOUND_LISTING_VIEW) {
            mav.addObject("players", playersFound);

        } else {
            mav.addObject("player", player);
            mav.addObject("message", message);
        }

        return mav;

    }

    // Lists all players
    @GetMapping("/players/list")
    public String showPlayers(@RequestParam Map<String, Object> params, Model model){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) -1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 2);
        Page<Player> pagePlayer = playerService.findPlayers(pageRequest);
        int totalPages = pagePlayer.getTotalPages();
        if (totalPages > 0){
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("number", pagePlayer.getNumber());
            model.addAttribute("size", pagePlayer.getSize());
            model.addAttribute("pages", pages);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalElements", pagePlayer.getTotalElements());
            model.addAttribute("data",pagePlayer.getContent());

        }
        model.addAttribute("list", pagePlayer.getContent());
        return PLAYERS_LISTING_VIEW;
    }

    //
    @GetMapping("/admin/players/create")
    public ModelAndView createPlayerAdmin(){
        ModelAndView result = new ModelAndView(CREATE_PLAYERS);
        result.addObject("player", new Player());
        return result;
    }

    @PostMapping(value = "/admin/players/create")
	public ModelAndView processAdminCreationForm(@Valid Player player, BindingResult result) {
        if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView(CREATE_PLAYERS);
            return mav;
		} else if (badWordsService.checkPlayerBadWords(player)) {
            ModelAndView mav = new ModelAndView(CREATE_PLAYERS);
            String message = "Check your language!";
            mav.addObject("message", message);
            return mav;
        } else {
			this.playerService.savePlayer(player);
			return new ModelAndView("redirect:/welcome");
		}
	}

    @GetMapping("/players/create")
    public ModelAndView createPlayer(){
        ModelAndView result = new ModelAndView(CREATE_PLAYERS);
        result.addObject("player", new Player());
        return result;
    }
    

    @PostMapping(value = "/players/create")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return CREATE_PLAYERS;
		}
		else {
			this.playerService.savePlayer(player);
			return "redirect:/welcome";
		}
	}

    @GetMapping("/players/{playerId}")
        public ModelAndView showPlayer(@PathVariable("playerId") int playerId) {
        ModelAndView mav = new ModelAndView(PLAYER_PROFILE);
        Optional<Player> player = this.playerService.findPlayerById(playerId);
        if (player.isPresent()) {
            mav.addObject(player.get());
        } else {
            mav.addObject(MESSAGE, PLAYER_NOT_FOUND);
        }
        return mav;
    }


    @GetMapping("/players/{playerId}/edit")
    public ModelAndView editLoggedPlayer(@PathVariable("playerId") int playerId, Principal principal) {
        String username = principal.getName();
        System.out.println("nombre usuario principal: " + username);
        Integer loggedId = this.playerService.getUserIdByName(username);
        if (loggedId == playerId) {
            Player player = playerService.findById(playerId);
            ModelAndView result = new ModelAndView(EDIT_PLAYER);
            result.addObject("player", player);
            return result;
        } else {
            ModelAndView result = new ModelAndView(EDIT_PLAYER);
            result.addObject(MESSAGE, PLAYER_NOT_FOUND);
            return result;
        }
    }

    @PostMapping("/players/{playerId}/edit")
    public String saveLoggedPlayer(@PathVariable("playerId") int playerId, Player player, Principal principal){
        Player playerToBeUpdated = playerService.findById(playerId);
        BeanUtils.copyProperties(player,playerToBeUpdated,"id","achievements","user");
        playerToBeUpdated.getUser().setUsername(player.getUser().getUsername());
        playerService.savePlayer(playerToBeUpdated);
            return "redirect:/logout";
    }

    @GetMapping("/admin/{playerId}/edit")
    public ModelAndView editPlayer(@PathVariable("playerId") int playerId){
        Player player = playerService.findById(playerId);
        ModelAndView result=new ModelAndView(EDIT_PLAYER);
        result.addObject("player", player);
        return result;
    }

    @PostMapping("/admin/{playerId}/edit")
    public String savePlayer(@PathVariable("playerId") int playerId, Player player) {
        Player playerToBeUpdated = playerService.findById(playerId);
        BeanUtils.copyProperties(player,playerToBeUpdated,"id","achievements","user");
        playerToBeUpdated.getUser().setUsername(player.getUser().getUsername());
        playerService.savePlayer(playerToBeUpdated);
        return "redirect:/players/{playerId}";
    }

    @GetMapping("/players/{playerId}/delete")
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayerById(playerId);
        return "redirect:/list";
    }

    @GetMapping("/players/myProfile")
    public ModelAndView showLoggedUser(Principal principal) {
        ModelAndView mav = new ModelAndView(LOGGED_USER_VIEW);
        String username = principal.getName();
        Integer id = this.playerService.getUserIdByName(username);
        Optional<Player> player = this.playerService.findPlayerById(id);
        if (player.isPresent()) {
            mav.addObject(player.get());
        } else {
            mav.addObject(MESSAGE, PLAYER_NOT_FOUND);
        }
        return mav;
    }

    @GetMapping("/players/myFriends")
    public ModelAndView showPlayersFriends() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer playerId = this.playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(playerId);

        List<Player> friends = currentPlayer.getFriends();
        
        ModelAndView mav = new ModelAndView(PLAYER_FRIENDS);
        mav.addObject("friends", friends);
        return mav;
    }

    @GetMapping("players/{playerId}/viewFriend")
    public ModelAndView viewPlayerProfile(@PathVariable("playerId") Integer playerId) {
        Player player = playerService.findById(playerId);
        ModelAndView mav = new ModelAndView(FRIEND_PROFILE);
        mav.addObject("player", player);
        return mav;
    }

    @GetMapping("/players/{playerId}/add")
    public String addFriend(@PathVariable("playerId") Integer playerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer currentPlayerId = this.playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(currentPlayerId);
        
        Player playerToAdd = playerService.findById(playerId);

        if (!currentPlayer.getFriends().contains(playerToAdd)) {
            playerService.makeFriends(currentPlayer, playerToAdd);
        }

        return "redirect:/players/myFriends";
    }

    // Sends a friend request to the player with the id of the path
    @GetMapping("/players/{playerId}/sendFriendRequest")
    public ModelAndView sendFriendRequest(@PathVariable("playerId") Integer playerId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer currentPlayerId = this.playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(currentPlayerId);
        
        List<Player> friends = currentPlayer.getFriends();
        ModelAndView mav = new ModelAndView(PLAYER_FRIENDS);
        mav.addObject("friends", friends);
        Player playerToAdd = playerService.findById(playerId);

        if (!currentPlayer.getFriends().contains(playerToAdd)) {
            String notificationMessage = currentPlayer.getUser().getUsername() + " sent you a friend request!";
            notificationService.sendFriendRequest(playerToAdd, notificationMessage, currentPlayerId);
            String message = "Friend request sent.";
            mav.addObject("message", message);
        }
    
        return mav;
    }

    @GetMapping("/players/{playerId}/sendGameInvitation/{code}")
    public String sendGameInvitation(@PathVariable("playerId") Integer playerId, @PathVariable("code") String code) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer currentPlayerId = this.playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(currentPlayerId);

        Player playerToInvite = playerService.findById(playerId);
        String notificationMessage = currentPlayer.getUser().getUsername() + " sent you a game invitation!";

        notificationService.sendGameInvitation(playerToInvite, notificationMessage, currentPlayerId, code);
        return "redirect:/games/lobby/" + code + "/waitRoom";

    }

    @GetMapping("/players/friends/{playerId}/delete")
    public String deleteFriend(@PathVariable("playerId") Integer playerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer currentPlayerId = this.playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(currentPlayerId);

        Player playerToDelete = playerService.findById(playerId);
        if (currentPlayer.getFriends().contains(playerToDelete)) {
            currentPlayer.getFriends().remove(playerToDelete);
            playerService.savePlayer(currentPlayer);
        }
        return "redirect:/players/myFriends";
    }

}
