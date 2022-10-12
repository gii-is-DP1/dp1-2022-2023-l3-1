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
}
