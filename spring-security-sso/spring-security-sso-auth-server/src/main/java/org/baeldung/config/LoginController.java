package org.baeldung.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
//	@RequestMapping("/login")
//	public String moon() {
//		return "login";
//	}

	@RequestMapping("/sun")
	public String sun() {
		return "sun";
	}
	
}
