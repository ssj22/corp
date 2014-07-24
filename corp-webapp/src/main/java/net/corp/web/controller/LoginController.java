package net.corp.web.controller;

import net.corp.core.vo.RegisterVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/reg", method=RequestMethod.POST)
	public String showMain(@RequestBody RegisterVO register) {
		System.out.println(register.getFname());
		return "Done";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String showNew(ModelMap model) {
		return "index.html";
	}
	
	
}
