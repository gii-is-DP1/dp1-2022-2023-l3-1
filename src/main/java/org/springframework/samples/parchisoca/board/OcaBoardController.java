package org.springframework.samples.parchisoca.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.samples.parchisoca.statistic.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OcaBoardController {

    @Autowired
    private OcaBoardService ocaBoardService;

    @Autowired
    private GameService gameService;

    @Autowired
    private OcaPieceService ocaPieceService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private StatService statService;

    private final String OCABOARD = "boards/ocaBoard";
    private final String GAMES_FINISHED = "games/GameFinished";
    private final String LOOSER = "games/GameLooser";

    // Generates an oca board 
    @GetMapping({"boards/ocaBoard/{ocaBoardId}"})
    public ModelAndView board(@PathVariable("ocaBoardId") int ocaBoardId, HttpServletResponse response){
        
        

        Player currentPlayer= playerService.getCurrentPlayer();
        
        OcaBoard newOcaBoard = ocaBoardService.findById(ocaBoardId);
        List<OcaPiece> pieces = newOcaBoard.getPieces();
        OcaDice dice = ocaBoardService.findOcaDiceByPlayerAndBoard(currentPlayer, newOcaBoard);
        Integer number = dice.getNumber();
        ModelAndView mav = new ModelAndView(OCABOARD);
        Integer turn = newOcaBoard.getTurn();

        OcaPiece ocaPiece  = pieces.get(turn);
        Player piecePlayer = ocaPiece.getPlayer();

        mav.addObject("ocaBoard", newOcaBoard);
        mav.addObject("pieces", pieces);
        
        mav = checkMav(mav, response, newOcaBoard, currentPlayer, piecePlayer, number);
        return mav;
        
    }


    

    // Exits from the game with that code
    @GetMapping("games/lobby/{code}/exit")
    public String exitPlayerGame(@PathVariable("code") String code, HttpServletResponse response) {

        Player currentPlayer = playerService.getCurrentPlayer();

        Game currentGame = gameService.findGameByCode(code);
        List<Player> ls = currentGame.getPlayers();

        ls.remove(currentPlayer);
        currentGame.setPlayers(ls);
        gameService.save(currentGame);

        return "redirect:/games/lobbys";

    }
   
    // Calls the function that rolls the dice
    @GetMapping({"boards/ocaBoard/{ocaBoardId}/dice"})
    public ModelAndView rollDice(@PathVariable("ocaBoardId") int ocaBoardId, HttpServletResponse response, HttpServletRequest req){

        Player currentPlayer = playerService.getCurrentPlayer();    
    
        ModelAndView mav = new ModelAndView("redirect:/boards/ocaBoard/" + ocaBoardId);
        OcaBoard currentOcaBoard = ocaBoardService.findById(ocaBoardId);
        OcaDice dice = ocaBoardService.findOcaDiceByPlayerAndBoard(currentPlayer, currentOcaBoard);

        List<OcaPiece> pieces = currentOcaBoard.getPieces();
        Integer turn = currentOcaBoard.getTurn();
        OcaPiece ocaPiece  = pieces.get(turn);
        Player piecePlayer = ocaPiece.getPlayer();

        if(!ocaBoardService.isActualPlayer(piecePlayer)) {
            return mav;
        }

        dice.rollDice();  
        Integer penalization = ocaPiece.getPenalizationTurn();
        
        if (penalization != 0) {
            ocaPiece.setPenalizationTurn(penalization-1);
            ocaPieceService.save(ocaPiece);
        }else{
            ocaBoardService.makeMovement(currentOcaBoard, ocaPiece);
            mav = checkFinished(mav, ocaPiece, currentPlayer, currentOcaBoard);
        }
       
        
        ocaBoardService.nextTurn(currentOcaBoard);
        ocaBoardService.save(currentOcaBoard);
        return mav; 

    }

    


    private ModelAndView checkMav(ModelAndView mav, HttpServletResponse response, OcaBoard newOcaBoard, Player currentPlayer, Player piecePlayer, Integer number) {

        if (newOcaBoard.getGame().getWinner() != null) {
            statService.increaseLostGames(currentPlayer);
            mav = new ModelAndView(LOOSER);
            return mav;
        } else if (!ocaBoardService.isActualPlayer(piecePlayer)){
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
    
    private ModelAndView checkFinished(ModelAndView mav, OcaPiece ocaPiece, Player currentPlayer, OcaBoard currentOcaBoard) {

        if (ocaPiece.getPosition().equals(63)) {
            ModelAndView finishMav = new ModelAndView(GAMES_FINISHED);
            statService.increaseWonGames(currentPlayer);
            Player winner = ocaPiece.getPlayer();
            Game game = currentOcaBoard.getGame();
            game.setInProgress(false);
            game.setWinner(winner);
            gameService.save(game);
            finishMav.addObject("game", game);
            return finishMav;
        }else{
            return mav;
        }
    }

}