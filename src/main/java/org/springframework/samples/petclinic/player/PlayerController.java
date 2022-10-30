package org.springframework.samples.petclinic.player;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {
    
    private PlayerService service;
    private final String  PLAYERS_LISTING_VIEW="/players/PlayersListing";

    @Autowired
    public PlayerController(PlayerService service){
        this.service = service;
    }
    @GetMapping("/")
    public ModelAndView showPlayers(){
        ModelAndView result = new ModelAndView(PLAYERS_LISTING_VIEW);
        result.addObject("players", service.getPlayers());
        return result;

    } 
    

}
