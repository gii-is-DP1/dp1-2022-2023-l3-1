package org.springframework.samples.parchisoca.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
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

@Controller
public class OcaBoardController {

    @Autowired
    OcaBoardService ocaService;

    @Autowired
    GameService gameService;

    @Autowired
    OcaPieceService ocaPieceService;

    @Autowired
    PlayerService playerService;

    @GetMapping({"boards/ocaBoard/{id}"})
    public String board(@PathVariable("id") int id, Map<String, Object> model, HttpServletResponse response){
        // response.addHeader("Refresh","2");
        OcaBoard newOcaBoard = ocaService.findById(id);
        model.put("ocaBoard", newOcaBoard); 
        return "boards/ocaBoard";
    }

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


    public  OcaBoard initBoard(){

        OcaBoard oca = new OcaBoard();
        OcaPiece piece = new OcaPiece();
        piece.setColour(Colour.RED);
        piece.setOcaBoard(oca); 
        oca.addPiece(piece);
        ocaService.save(oca);
        ocaPieceService.save(piece);
        return oca;

    }
}