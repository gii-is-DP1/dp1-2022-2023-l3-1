package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.Oca.BoxesOca;
import org.springframework.samples.parchisoca.Oca.BoxesOcaService;
import org.springframework.samples.parchisoca.Oca.SpecialBoxesOca;
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

    @Autowired
    BoxesOcaService boService;

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
        List<BoxesOca> ls = initBoxes();
        oca.setBoxes(ls);
        ocaService.save(oca);
        ocaPieceService.save(piece);
        return oca;

    }

    public List<BoxesOca>  initBoxes() {
        List<BoxesOca> normalBoxesOca = new ArrayList<BoxesOca>(63);
        for (int i=0; i<63; i++){
            BoxesOca res = new BoxesOca();
            if(i==5 || i==9 || i==14 || i==18 || i== 23 || i==27 
            || i== 32 || i==36 || i==41 || i==45 || i==50 || i==54 || i== 59){
                res.setSpecialBoxOca(SpecialBoxesOca.OCA);
            } else if (i==6 || i==12) {
                res.setSpecialBoxOca(SpecialBoxesOca.BRIDGE);;
            } else if (i==26 || i==53) {
                res.setSpecialBoxOca(SpecialBoxesOca.DICES);
            } else if (i==19) {
                res.setSpecialBoxOca(SpecialBoxesOca.HOSTAL);
            } else if (i==31) {
                res.setSpecialBoxOca(SpecialBoxesOca.WELL);
            } else if (i==42) {
                res.setSpecialBoxOca(SpecialBoxesOca.LABYRINTH);
            } else if (i==58) {
                res.setSpecialBoxOca(SpecialBoxesOca.DEATH);
            } else if (i==63) {
                res.setSpecialBoxOca(SpecialBoxesOca.GOAL);
            } else {
                res.setSpecialBoxOca(SpecialBoxesOca.NORMAL);
            }
            normalBoxesOca.add(res);
            boService.save(res);


        }
        return normalBoxesOca;
    }



}