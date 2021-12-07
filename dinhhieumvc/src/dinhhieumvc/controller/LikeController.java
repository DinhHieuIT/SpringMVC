package dinhhieumvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.LikeService;
import dinhhieumvc.service.MicropostService;
import dinhhieumvc.service.UserService;

@Controller
@EnableWebMvc
public class LikeController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("likeService")
	LikeService likeService;
	
	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
	
	@PostMapping(value = "/microposts/{id}/like")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> create(@PathVariable Integer id, Locale locale, Model model, HttpServletRequest request) 
	throws Exception {
		logger.info("like user");
		
		MicropostModel micropost = new MicropostModel();
		micropost.setId(id);
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		likeService.like(user, micropost);
		
		return new ResponseEntity<String>("{\"result\" : \"OK\"}", HttpStatus.OK);
	}
	
}
