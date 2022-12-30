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
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
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

    @Autowired
	ParchisPieceService parchisPieceService;

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
        
        Player currentPlayer = playerService.getCurrentPlayer();
        
        ParchisBoard newParchisBoard = parchisBoardService.findById(parchisBoardId);
        Game game = newParchisBoard.getGame();
        List<ParchisPiece> pieces = newParchisBoard.getPieces();
		List<Player> players = game.getPlayers();

        ModelAndView mav = new ModelAndView(PARCHISBOARD);
        Integer turn = newParchisBoard.getTurn();

        Player player  = players.get(turn);

        mav.addObject("parchisBoard", newParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("players", players);

        List<ParchisPiece> piecesCurrentPlayer = parchisPieceService.findParchisPiecesByPlayerParchisBoard(currentPlayer, newParchisBoard);

        mav = checkMav(mav, response, piecesCurrentPlayer, game, newParchisBoard, player, currentPlayer);
        
        return mav;
        
    }

    private ModelAndView checkMav(ModelAndView mav, HttpServletResponse response, List<ParchisPiece> piecesCurrentPlayer, 
        Game game, ParchisBoard newParchisBoard, Player player, Player currentPlayer) {

        if (piecesCurrentPlayer.stream().allMatch(x->x.getInGoal() == true) ){
            game.setWinner(currentPlayer);
            mav = new ModelAndView(GAMES_FINISHED);
            return mav;
        }
        
        if (newParchisBoard.getGame().getWinner() != null) {
            mav = new ModelAndView(LOOSER);
            return mav;
        }else if(!parchisBoardService.isActualPlayer(player)) {
            response.addHeader("Refresh", "2"); 
            mav.addObject("error", "It is not your turn");
            return mav;
        } else {
           
            mav.addObject("error", "Roll dice!");
            return mav;
        }
    }

    @GetMapping({"boards/parchisBoard/{parchisBoardId}/dice"})
    public ModelAndView rollDice(@PathVariable("parchisBoardId") int parchisBoardId
    ,HttpServletResponse response, HttpServletRequest req){

       

        ModelAndView mav = new ModelAndView("redirect:/boards/parchisBoard/"+parchisBoardId+"/diceSelection");
        ParchisBoard currentParchisBoard = parchisBoardService.findById(parchisBoardId);
       
        
        List<ParchisPiece> pieces = currentParchisBoard.getPieces();

        Game game = currentParchisBoard.getGame();
        Integer turn = currentParchisBoard.getTurn();
        List<Player> players = game.getPlayers();
        Player player = players.get(turn);

        Player currentPlayer = playerService.getCurrentPlayer();
        if (!currentPlayer.equals(player)){
            mav = new ModelAndView("redirect:/boards/parchisBoard/"+ parchisBoardId);
        }

        List<ParchisDice> dices = parchisDiceService.findDiceByParchisBoardPlayer(currentParchisBoard,player);
        ParchisDice dice1 = dices.get(0);
        ParchisDice dice2 = dices.get(1);
        dice1.rollDice();
        dice2.rollDice();
        parchisDiceService.save(dice1);
        parchisDiceService.save(dice2);

        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("dice1", dice1);
        mav.addObject("dice2", dice2);


        
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
        List<ParchisDice> dices = parchisDiceService.findDiceByParchisBoardPlayer(currentParchisBoard,player);
        ParchisDice dice1 = dices.get(0);
        ParchisDice dice2 = dices.get(1);
        
        if(dice1.getNumber() == null && dice2.getNumber() == null){
            mav.setViewName("redirect:/boards/parchisBoard/"+ parchisBoardId);
            parchisBoardService.nextTurn(currentParchisBoard, game, turn);
            parchisBoardService.save(currentParchisBoard);
            return mav;
        }
  
        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("dice1", dice1);
        mav.addObject("dice2", dice2);
       
        return mav;
    }

    @GetMapping({"boards/parchisBoard/{parchisBoardId}/dice/{diceId}/pieceSelection"})
    public ModelAndView pieceSelection(@PathVariable("parchisBoardId") int parchisBoardId,@PathVariable("diceId") int diceId
    ,HttpServletResponse response, HttpServletRequest req){

        ModelAndView mav = new ModelAndView(PIECE_SELECTION);
        ParchisBoard currentParchisBoard = parchisBoardService.findById(parchisBoardId);
        List<ParchisPiece> pieces = currentParchisBoard.getPieces();        
        Game game = currentParchisBoard.getGame();
        Integer turn = currentParchisBoard.getTurn();
        List<Player> players = game.getPlayers();
        Player player = players.get(turn);
        List<ParchisPiece> piecesPlayer = parchisPieceService.findParchisPiecesByPlayerParchisBoard(player, currentParchisBoard) ;
        
        ParchisDice dice = parchisDiceService.findById(diceId);

        if (game.getWinner() == player){
            mav.setViewName("redirect:/boards/parchisBoard/"+ parchisBoardId);
            return mav;
        } 
        mav.addObject("dice", dice);
        mav.addObject("parchisBoard", currentParchisBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("piece1", piecesPlayer.get(0));
        mav.addObject("piece2", piecesPlayer.get(1));
        mav.addObject("piece3", piecesPlayer.get(2));
        mav.addObject("piece4", piecesPlayer.get(3));

        return mav;
 
    }

    @GetMapping({"boards/parchisBoard/{parchisBoardId}/dice/{diceId}/pieceSelection/piece/{parchisPieceId}"})
    public ModelAndView pieceAction(@PathVariable("parchisBoardId") int parchisBoardId,@PathVariable("diceId") int diceId, @PathVariable("parchisPieceId") int parchisPieceId
    ,HttpServletResponse response, HttpServletRequest req){

        parchisBoardService.findById(parchisBoardId);
    
        ModelAndView mav = new ModelAndView("redirect:/boards/parchisBoard/{parchisBoardId}/diceSelection");
        ParchisDice dice  = parchisDiceService.findById(diceId);
       
        ParchisBoard parchisBoard = parchisBoardService.findById(parchisBoardId);
        ParchisPiece piece = parchisPieceService.findById(parchisPieceId);
        parchisBoardService.movementPiece(parchisBoard, piece, dice);

        dice.setNumber(null);
        parchisDiceService.save(dice);

        mav = checkAddMovement(piece, dice, mav, parchisBoardId, diceId);

        return mav;
    }

    private ModelAndView checkAddMovement(ParchisPiece piece, ParchisDice dice, ModelAndView mav, int parchisBoardId, int diceId) {

        if (piece.getJustAte()) {
            dice.setNumber(20);
            parchisDiceService.save(dice);
            mav = new ModelAndView("redirect:/boards/parchisBoard/"+parchisBoardId+"/dice/"+diceId+"/pieceSelection");
            piece.setJustAte(false);
            parchisPieceService.save(piece);
           
        }
        if (piece.getJustInGoal()){
            dice.setNumber(10);
            parchisDiceService.save(dice);
            mav = new ModelAndView("redirect:/boards/parchisBoard/"+parchisBoardId+"/dice/"+diceId+"/pieceSelection");
            piece.setJustInGoal(false);
            parchisPieceService.save(piece);
        } 
        return mav;
        
    }
}