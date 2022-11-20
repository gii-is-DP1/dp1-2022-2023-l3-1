package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.dice.OcaDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.oca.BoxesOca;
import org.springframework.samples.parchisoca.oca.BoxesOcaService;
import org.springframework.samples.parchisoca.oca.SpecialBoxesOca;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
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
    OcaBoardService ocaBoardService;

    @Autowired
    GameService gameService;

    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    OcaDiceService ocaDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    BoxesOcaService boService;

    private final String OCABOARD = "boards/ocaBoard";
    private final String GAMES_FINISHED = "games/GameFinished";
    
    // @GetMapping({"boards/ocaBoard/{ocaBoardId}"})
    // public String board(@PathVariable("ocaBoardId") int ocaBoardId, Map<String, Object> model, HttpServletResponse response){
    //     OcaBoard newOcaBoard = ocaBoardService.findById(ocaBoardId);
    //     model.put("ocaBoard", newOcaBoard); 
    //     return "boards/ocaBoard";
    // }

    @GetMapping({"boards/ocaBoard/{ocaBoardId}"})
    public ModelAndView board(@PathVariable("ocaBoardId") int ocaBoardId, HttpServletResponse response){
        response.addHeader("Refresh", "1");
        OcaBoard newOcaBoard = ocaBoardService.findById(ocaBoardId);
        List<OcaPiece> pieces = newOcaBoard.getPieces();
        Integer number = newOcaBoard.getOcaDice().getNumber();
        ModelAndView mav = new ModelAndView(OCABOARD);
        mav.addObject("ocaBoard", newOcaBoard);
        mav.addObject("pieces", pieces);
        mav.addObject("number", number);
        return mav;
    }


    // Leaving the game with that code
    @GetMapping("games/lobby/{code}/exit")
    public String exitPlayerGame(@PathVariable("code") String code, ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = playerService.getUserIdByName(username);
        Player currentPlayer = playerService.getById(id);

        Game currentGame = gameService.findGameByCode(code);
        List<Player> ls = currentGame.getPlayers();

        ls.remove(currentPlayer);
        currentGame.setPlayers(ls);
        gameService.save(currentGame);

        return "redirect:/games/lobbys";

    }

    // // Calls the function that rolls the dice
    // @GetMapping({"boards/ocaBoard/{ocaBoardId}/dice"})
    // public ModelAndView rollDice(@PathVariable("ocaBoardId") int ocaBoardId, HttpServletResponse response){
    //      ModelAndView mav = new ModelAndView(OCABOARD);
    //     OcaBoard currentOcaBoard = ocaBoardService.findById(ocaBoardId);
    //     OcaDice dice = currentOcaBoard.getOcaDice();
    //     dice.rollDice();

    //     List<OcaPiece> pieces = currentOcaBoard.getPieces();
    //     Integer turn = currentOcaBoard.getTurn();
    //     OcaPiece ocaPiece  = pieces.get(turn);
    //     Player piecePlayer = ocaPiece.getPlayer();

    //     if(ocaBoardService.isActualPlayer(piecePlayer)){
    //         mav.addObject("ocaBoard", currentOcaBoard);
    //         mav.addObject("ocaPiece", ocaPiece);
    //         mav.addObject("pieces", pieces);
    //         return mav;
    //     }
        

    //     Integer penalization = ocaPiece.getPenalizationTurn();
        
    //     if (penalization !=0) {
    //         mav.addObject("ocaBoard", currentOcaBoard);
    //         mav.addObject("ocaPiece", ocaPiece);
    //         ocaPiece.setPenalizationTurn(penalization-1);
    //         ocaPieceService.save(ocaPiece);
    //     } else {
    //         ocaBoardService.actualPosition(currentOcaBoard, ocaPiece);
    //         if (ocaPiece.getPosition().equals(63)) {
    //             mav = new ModelAndView(GAMES_FINISHED);
    //             Player winner  = ocaPiece.getPlayer();
    //             Game game = currentOcaBoard.getGame();
    //             game.setInProgress(false);
    //             game.setWinner(winner);
    //             gameService.save(game);
    //             mav.addObject("game", game);
    //             return mav;
    //         }
    //         Integer number = dice.getNumber();
    //         mav.addObject("ocaBoard", currentOcaBoard);
    //         mav.addObject("pieces", pieces);
    //         mav.addObject("number", number);
    //     }
    //     ocaBoardService.nextTurn(currentOcaBoard, turn);
    //     ocaBoardService.save(currentOcaBoard);
    //     return mav;
    // }

   
    // Calls the function that rolls the dice
    @GetMapping({"boards/ocaBoard/{ocaBoardId}/dice"})
    public ModelAndView rollDice(@PathVariable("ocaBoardId") int ocaBoardId, HttpServletResponse response){
        ModelMap model = new ModelMap();
        OcaBoard currentOcaBoard = ocaBoardService.findById(ocaBoardId);
        OcaDice dice = currentOcaBoard.getOcaDice();
        dice.rollDice();

        List<OcaPiece> pieces = currentOcaBoard.getPieces();
        Integer turn = currentOcaBoard.getTurn();
        OcaPiece ocaPiece  = pieces.get(turn);
        Player piecePlayer = ocaPiece.getPlayer();

        if(!ocaBoardService.isActualPlayer(piecePlayer)){
            model.addAttribute("ocaBoard", currentOcaBoard);
            model.addAttribute("ocaPiece", ocaPiece);
            model.addAttribute("pieces", pieces);
            return new ModelAndView("redirect:/boards/ocaBoard/"+ocaBoardId, model);
        }
        

        Integer penalization = ocaPiece.getPenalizationTurn();
        
        if (penalization !=0) {
            model.addAttribute("ocaBoard", currentOcaBoard);
            model.addAttribute("ocaPiece", ocaPiece);
            ocaPiece.setPenalizationTurn(penalization-1);
            ocaPieceService.save(ocaPiece);
        } else {
            ocaBoardService.actualPosition(currentOcaBoard, ocaPiece);
            if (ocaPiece.getPosition().equals(63)) {
                ModelAndView mav = new ModelAndView(GAMES_FINISHED);
                Player winner  = ocaPiece.getPlayer();
                Game game = currentOcaBoard.getGame();
                game.setInProgress(false);
                game.setWinner(winner);
                gameService.save(game);
                mav.addObject("game", game);
                return mav;
            }
            Integer number = dice.getNumber();
            model.addAttribute("ocaBoard", currentOcaBoard);
            model.addAttribute("pieces", pieces);
            model.addAttribute("number", number);
        }
        ocaBoardService.nextTurn(currentOcaBoard, turn);
        ocaBoardService.save(currentOcaBoard);
        return new ModelAndView("redirect:/boards/ocaBoard/"+ocaBoardId, model);
    }

    
}