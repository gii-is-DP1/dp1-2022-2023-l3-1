package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.ParchisBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParchisBoardController {
	
	@Autowired
	ParchisBoardService parchisBoardService;

	  @GetMapping({"/parchisBoard"})
	  public String welcome(Map<String, Object> model, HttpServletResponse response) {	    
		//response.addHeader("Refresh", "1");
		model.put("parchisBoard", parchisBoardService.findById(1).get());
	    return "parchisBoard";
	  }

}