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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);
        
        OcaBoard newOcaBoard = ocaBoardService.findById(ocaBoardId);
        List<OcaPiece> pieces = newOcaBoard.getPieces();
        OcaDice dice = ocaBoardService.findOcaDiceByPlayer(currentPlayer, newOcaBoard);
        Integer number = dice.getNumber();
        ModelAndView mav = new ModelAndView(OCABOARD);
        Integer turn = newOcaBoard.getTurn();

        OcaPiece ocaPiece  = pieces.get(turn);
        Player piecePlayer = ocaPiece.getPlayer();

        mav.addObject("ocaBoard", newOcaBoard);
        mav.addObject("pieces", pieces);
        
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


    // Exits from the game with that code
    @GetMapping("games/lobby/{code}/exit")
    public String exitPlayerGame(@PathVariable("code") String code, ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.findById(id);
        
        ModelAndView mav = new ModelAndView("redirect:/boards/ocaBoard/"+ocaBoardId);
        OcaBoard currentOcaBoard = ocaBoardService.findById(ocaBoardId);
        OcaDice dice = ocaBoardService.findOcaDiceByPlayer(currentPlayer, currentOcaBoard);

        List<OcaPiece> pieces = currentOcaBoard.getPieces();
        Integer turn = currentOcaBoard.getTurn();
        OcaPiece ocaPiece  = pieces.get(turn);
        Player piecePlayer = ocaPiece.getPlayer();

        if(!ocaBoardService.isActualPlayer(piecePlayer)){
            return mav;
        }

        dice.rollDice();  
        Integer penalization = ocaPiece.getPenalizationTurn();
        
        if (penalization !=0) {
            ocaPiece.setPenalizationTurn(penalization-1);
            ocaPieceService.save(ocaPiece);
        } else {
            ocaBoardService.actualPosition(currentOcaBoard, ocaPiece);
            if (ocaPiece.getPosition().equals(63)) {
                ModelAndView res = new ModelAndView(GAMES_FINISHED);
                statService.increaseWonGames(currentPlayer);
                Player winner  = ocaPiece.getPlayer();
                Game game = currentOcaBoard.getGame();
                game.setInProgress(false);
                game.setWinner(winner);
                gameService.save(game);
                res.addObject("game", game);
                return res;
            }
            
        }
        ocaBoardService.nextTurn(currentOcaBoard);
        ocaBoardService.save(currentOcaBoard);
        return mav;
    }

    
}