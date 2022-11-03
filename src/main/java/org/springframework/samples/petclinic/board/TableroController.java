package org.springframework.samples.petclinic.board;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableroController {


    @Autowired
    TableroService ts;

    // @GetMapping("/oca")
    // public ModelAndView createProduct(){
    //     ModelAndView result = new ModelAndView(BOARD_VIEW);
    //     result.addObject("ocaBoard", ts.findById(1)); //necesaria para represetar los atributos del producto
    //     return result;
    // }

    @GetMapping({"/oca"})
    public String board(Map<String, Object> model, HttpServletResponse response){
        model.put("now", new Date());
        model.put("ocaBoard", ts.findById(1));
        return "boards/board";
    }


}