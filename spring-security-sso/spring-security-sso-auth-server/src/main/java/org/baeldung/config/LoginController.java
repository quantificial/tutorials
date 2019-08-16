package org.baeldung.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/ologin")
	public String ologin() {
		return "ologin";
	}

	@RequestMapping("/sun")
	public String sun() {
		return "sun";
	}
	
}
