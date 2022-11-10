package org.springframework.samples.parchisoca.board;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Controller;
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

    // @GetMapping({"/ocaBoard"})
    // public String board(Map<String, Object> model, HttpServletResponse response){

    //     //response.addHeader("Refresh","2");
    //     OcaBoard ocaBoard = ocaService.findById(1);
    //     model.put("ocaBoard", ocaBoard);
    //     return "boards/ocaBoard";
    // }

    @GetMapping({"boards/ocaBoard/{id}"})
    public String board(@PathVariable("id") int id, Map<String, Object> model, HttpServletResponse response){
        // response.addHeader("Refresh","2");
        OcaBoard newOcaBoard = ocaService.findById(id);
        model.put("ocaBoard", newOcaBoard); 
        return "boards/ocaBoard";
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