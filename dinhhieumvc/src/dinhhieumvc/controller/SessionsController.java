package dinhhieumvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.UserService;

@Controller
@EnableWebMvc
@EnableWebSecurity
public class SessionsController {
	private static final Logger logger = LoggerFactory.getLogger(SessionsController.class);
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping(value = "/login")
	public String add(Locale locale,Model model, HttpServletRequest request) {
		return "sessions/login";
	}
	
	@PostMapping(value = "/login")
	public String add(@RequestParam("email") String email, @RequestParam("password") String password, Locale locale,
			Model model, HttpServletRequest request) {
		UserModel user = userService.findUserByEmail(email);
		logger.info(""+ user);
		if(user != null && passwordEncoder.matches(password, user.getPassword())) {
				request.getSession().setAttribute("user",user);
				return "redirect: " + request.getContextPath() + "/home";
		}
		return "sessions/login";
	}

}
