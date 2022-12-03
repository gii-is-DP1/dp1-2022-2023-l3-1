package org.springframework.samples.parchisoca.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParchisBoardController {
	
	@Autowired
	ParchisBoardService parchisBoardService;

	@Autowired
	PlayerService playerService;

	@Autowired
	ParchisDiceService parchisDiceService;

	private final String PARCHISBOARD = "boards/parchisBoard";
	private final String GAMES_FINISHED = "games/GameFinished";
    private final String LOOSER = "games/GameLooser";

	@GetMapping({"/parchisBoard"})
	public String welcome(Map<String, Object> model, HttpServletResponse response) {	    
		response.addHeader("Refresh","2");
		model.put("parchisBoard", parchisBoardService.findById(1));
	    return "boards/parchisBoard";
	}

	// Generates an oca board 
    @GetMapping({"boards/parchisBoard/{parchisBoardId}"})
    public ModelAndView board(@PathVariable("parchisBoardId") int parchisBoardId, HttpServletResponse response) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);
        
        ParchisBoard newParchisBoard = parchisBoardService.findById(parchisBoardId);
        List<ParchisPiece> pieces = newParchisBoard.getPieces();
		List<Player> players = newParchisBoard.getPlayers();
        List<ParchisDice> dices = parchisBoardService.findParchisDiceByPlayer(currentPlayer, newParchisBoard);
		Integer number = parchisDiceService.roll2Dices(dices);
        ModelAndView mav = new ModelAndView(PARCHISBOARD);
        Integer turn = newParchisBoard.getTurn();

        Player player  = players.get(turn);

        mav.addObject("parchisBoard", newParchisBoard);
        mav.addObject("pieces", pieces);
        
        if (newParchisBoard.getGame().getWinner() != null) {
            mav = new ModelAndView(LOOSER);
            return mav;
        } else if (!parchisBoardService.isActualPlayer(player)) {
            response.addHeader("Refresh", "2");
            mav.addObject("number", number);
            mav.addObject("error", "It is not your turn");
            return mav;
        } else {
            mav.addObject("number", number);
            mav.addObject("error", "Roll dice!");
            return mav;
        }
        
        
    }

}