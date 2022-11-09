package org.springframework.samples.parchisoca.game;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {

    private final String GAMES_LISTING_VIEW = "/games/GamesListing";
    private final String GAME_CREATE_VIEW = "games/CreateGameForm";
    private final String GAME_INSTRUCTIONS_VIEW = "games/GameInstruction";
    private final String GAME_INSTRUCTIONS_VIEW1 = "games/GameInstructionOca";
    private final String LOBBY = "/lobbys/createLobby";
    private final String PUBLIC_GAMES = "games/GamePublic";

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;


    @GetMapping("/list")
    public ModelAndView showGames(){
        ModelAndView result = new ModelAndView(GAMES_LISTING_VIEW);
        result.addObject("games", gameService.getGames());
        //result.addObject("jugadores", service.getJugadores());
        return result;

    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        ModelAndView result = new ModelAndView(LOBBY);
        result.addObject("game", new Game());
        result.addObject("gameTypes", gameService.findAllGameTypes());
        return result;
    }

    @PostMapping("/create")
    public String saveGame(@Valid Game game, BindingResult result, ModelMap modelMap) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.getById(id);

        if (result.hasErrors()) {
            modelMap.addAttribute("game", game);
            return LOBBY;
        }else{
            game.addPlayer(currentPlayer);
            game.setCreator(currentPlayer);
            this.gameService.save(game);

        }

        return "redirect:/games/lobby/"+game.getCode();
    }


    @GetMapping("/lobbys")
    public ModelAndView publicGames(){
        ModelAndView result = new ModelAndView(PUBLIC_GAMES);
        result.addObject("games", gameService.findPublicGames());
        return result;
    }

    @GetMapping("/lobby/{code}")
    public String lobby(@PathVariable("code") String code, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.getById(id);
        Game currentGame = gameService.findGameByCode(code);

        if (!currentGame.getPlayers().contains(currentPlayer)) {
            currentGame.addPlayer(currentPlayer);
            return "redirect:/welcome";
        } else {
            return "redirect:/error";
        }
    }


    @GetMapping("/instructions")
    public ModelAndView instructions(){
        ModelAndView result = new ModelAndView(GAME_INSTRUCTIONS_VIEW);
        return result;
    }

    @GetMapping("/instructions/oca")
    public ModelAndView instructionsOca(){
        ModelAndView result = new ModelAndView(GAME_INSTRUCTIONS_VIEW1);
        return result;
    }



}
