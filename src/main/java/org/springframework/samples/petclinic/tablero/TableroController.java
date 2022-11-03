package org.springframework.samples.petclinic.tablero;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tablero")
public class TableroController {

    private static final String BOARD_VIEEW = "board";


    @GetMapping("/oca")
    public ModelAndView createProduct(){
        ModelAndView result = new ModelAndView(BOARD_VIEEW);
        result.addObject("tablero", new TableroOca()); //necesaria para represetar los atributos del producto
        return result;
    }


}