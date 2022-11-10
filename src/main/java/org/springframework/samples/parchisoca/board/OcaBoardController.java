package org.springframework.samples.parchisoca.board;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OcaBoardController {

    @Autowired
    OcaBoardService ocaService;
    @Autowired
    GameService gameService;

    // @GetMapping({"/ocaBoard"})
    // public String board(Map<String, Object> model, HttpServletResponse response){
    //     response.addHeader("Refresh","2");
    //     model.put("ocaBoard", ocaService.findById(1));
    //     return "boards/ocaBoard";
    // }

    @GetMapping({"boards/ocaBoard/{id}"})
    public String board(@PathVariable("id") int id, Map<String, Object> model, HttpServletResponse response){
        // response.addHeader("Refresh","2");
        OcaBoard newOcaBoard = ocaService.findById(id);
        model.put("ocaBoard", newOcaBoard); 
        return "boards/ocaBoard";
    }
}