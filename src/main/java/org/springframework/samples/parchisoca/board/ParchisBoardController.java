package org.springframework.samples.parchisoca.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
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
    private final String DICE_SELECTION = "boards/parchisBoardDiceSelection";
    private final String PIECE_SELECTION = "boards/parchisBoardPieceSelection";

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
        Game game = newParchisBoard.getGame();
        List<ParchisPiece> pieces = newParchisBoard.getPieces();
		List<Player> players = game.getPlayers();
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

    @GetMapping({"boards/parchisBoard/{parchisBoardId}/dice"})
    public ModelAndView rollDice(@PathVariable("parchisBoardId") int parchisBoardId
    ,HttpServletResponse response, HttpServletRequest req){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

        ModelAndView mav = new ModelAndView("redirect:/boards/parchisBoard/"+parchisBoardId+"/diceSelection");
        // ModelAndView mav = new ModelAndView("redirect:/boards/parchisBoard/"+parchisBoardId);
        ParchisBoard currentParchisBoard = parchisBoardService.findById(parchisBoardId);
       
        
        List<ParchisPiece> pieces = currentParchisBoard.getPieces();

        Game game = currentParchisBoard.getGame();
        Integer turn = currentParchisBoard.getTurn();
        List<Player> players = game.getPlayers();
        Player player = players.get(turn);
        ParchisDice dice1 = player.getParchisDice().get(0);
        ParchisDice dice2 = player.getParchisDice().get(1);
        dice1.rollDice();
        dice2.rollDice();
        
        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("dice1", dice1);
        mav.addObject("dice1", dice1);


        
        return mav;
    }
    @GetMapping({"boards/parchisBoard/{parchisBoardId}/diceSelection"})
    public ModelAndView diceSelection(@PathVariable("parchisBoardId") int parchisBoardId
    ,HttpServletResponse response, HttpServletRequest req){

        ModelAndView mav = new ModelAndView(DICE_SELECTION);


        ParchisBoard currentParchisBoard = parchisBoardService.findById(parchisBoardId);
        List<ParchisPiece> pieces = currentParchisBoard.getPieces();        
        Game game = currentParchisBoard.getGame();
        Integer turn = currentParchisBoard.getTurn();
        List<Player> players = game.getPlayers();
        Player player = players.get(turn);
        ParchisDice dice1 = player.getParchisDice().get(0);
        ParchisDice dice2 = player.getParchisDice().get(1);
        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("dice1", dice1);
        mav.addObject("dice2", dice2);
       
        return mav;
    }

    @GetMapping({"boards/parchisBoard/{parchisBoardId}/pieceSelection"})
    public ModelAndView pieceSelection(@PathVariable("parchisBoardId") int parchisBoardId
    ,HttpServletResponse response, HttpServletRequest req){

        ModelAndView mav = new ModelAndView(PIECE_SELECTION);
        ParchisBoard currentParchisBoard = parchisBoardService.findById(parchisBoardId);
        List<ParchisPiece> pieces = currentParchisBoard.getPieces();        
        Game game = currentParchisBoard.getGame();
        Integer turn = currentParchisBoard.getTurn();
        List<Player> players = game.getPlayers();
        Player player = players.get(turn);
        ParchisDice dice1 = player.getParchisDice().get(0);
        ParchisDice dice2 = player.getParchisDice().get(1);
        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("dice1", dice1);
        mav.addObject("number1", dice1.getNumber());
        mav.addObject("number2", dice2.getNumber());

        return mav;
 
    }
}