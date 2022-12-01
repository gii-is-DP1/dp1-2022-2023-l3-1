package org.springframework.samples.parchisoca.board;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParchisBoardController {
	
	@Autowired
	ParchisBoardService parchisBoardService;

	@GetMapping({"/parchisBoard"})
	public String welcome(Map<String, Object> model, HttpServletResponse response) {	    
		response.addHeader("Refresh","2");
		model.put("parchisBoard", parchisBoardService.findById(1).get());
	    return "boards/parchisBoard";
	}

}