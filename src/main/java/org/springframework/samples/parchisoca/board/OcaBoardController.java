package org.springframework.samples.parchisoca.board;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OcaBoardController {

    @Autowired
    OcaBoardService ts;

    @GetMapping({"/ocaBoard"})
    public String board(Map<String, Object> model, HttpServletResponse response){
        response.addHeader("Refresh","2");
        model.put("ocaBoard", ts.findById(1));
        return "boards/ocaBoard";
    }

}