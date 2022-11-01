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
    private GameService service;

    @Autowired
    public GameController(GameService service){
        this.service = service;
    }
    @GetMapping("/")
    public ModelAndView showGames(){
        ModelAndView result = new ModelAndView(GAMES_LISTING_VIEW);
        result.addObject("games", service.getGames());
        return result;
        
    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        ModelAndView result = new ModelAndView(GAME_CREATE_VIEW);
        result.addObject("game", new Game()); //necesaria para represetar los atributos de la partida
        result.addObject("gameType", service.findAllGameTypes());
        return result;
    }
}
