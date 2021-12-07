package dinhhieumvc.controller;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.UserService;

@Controller
@EnableWebMvc
public class RelationshipsController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(RelationshipsController.class);
	
	@GetMapping(value = "/users/{id}/following")
	public String following(@PathVariable Integer id,
			@RequestParam(name = "page", required = false) Optional<Integer> page, Locale locale, Model model,
			HttpServletRequest request) {
		UserModel condition = new UserModel();
		condition.setId(id);
		UserModel userModel = userService.getUserInfo(condition);
		model.addAttribute("user", userModel);
		
		condition.setPage(page.orElse(1));
		Page<UserModel> users = userService.following(userModel);
		model.addAttribute("users", users);
		
		model.addAttribute("follow_title", "user.follow.following");
		return "users/show_follow";
	}

	@GetMapping(value = "/users/{id}/followers")
	public String followers(@PathVariable Integer id,
			@RequestParam(name = "page", required = false) Optional<Integer> page, Locale locale, Model model,
			HttpServletRequest request) {
		UserModel condition = new UserModel();
		condition.setId(id);
		UserModel userModel = userService.getUserInfo(condition);
		model.addAttribute("user", userModel);
		
		condition.setPage(page.orElse(1));
		Page<UserModel> users = userService.followers(userModel);
		model.addAttribute("users", users);
		
		model.addAttribute("follow_title", "user.follow.followers");
		return "users/show_follow";
	}
	
	@PostMapping(value = "/follow/{id}")
	public String create(@RequestParam Integer followedId, Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("follow user");
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		UserModel follower = user;
		UserModel followed = new UserModel();
		followed.setId(followedId);
		userService.follow(follower, followed);
		model.addAttribute("user", followed);
		return "users/_unfollow :: copy";
//		return "redirect: " + request.getContextPath() + "/users/" + followedId;
	}
	
	@DeleteMapping(value="/relationships/{followedId}")
	public String destroy(@PathVariable Integer followedId, Model model, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
		logger.info("unfollow user");
		UserModel follower =(UserModel) request.getSession().getAttribute("user");
		UserModel followed = new UserModel();
		followed.setId(followedId);
		userService.unfollow(follower, followed);
		model.addAttribute("user", followed);
		return "users/_follow :: copy";
//		return "redirect: " + request.getContextPath() + "/users/" + followedId;
	}

}
