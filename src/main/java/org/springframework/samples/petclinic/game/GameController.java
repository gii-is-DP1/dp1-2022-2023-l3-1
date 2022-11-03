package org.springframework.samples.petclinic.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {
    
    private final String GAMES_LISTING_VIEW = "/games/GamesListing";
    private final String GAME_CREATE_VIEW = "games/CreateGameForm";
    private final String GAME_INSTRUCTIONS_VIEW = "games/GameInstruction";
    private final String GAME_INSTRUCTIONS_VIEW1 = "games/GameInstructionOca";
    private GameService service;

    @Autowired
    public GameController(GameService service){
        this.service = service;
    }
    @GetMapping("/list")
    public ModelAndView showGames(){
        ModelAndView result = new ModelAndView(GAMES_LISTING_VIEW);
        result.addObject("games", service.getGames());
        //result.addObject("jugadores", service.getJugadores());
        return result;
        
    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        ModelAndView result = new ModelAndView(GAME_CREATE_VIEW);
        result.addObject("game", new Game()); 
        result.addObject("gameTypes", service.findAllGameTypes());
        return result;
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
