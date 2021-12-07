package dinhhieumvc.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.RatingModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.MicropostService;
import dinhhieumvc.service.RatingService;
import dinhhieumvc.service.UserService;

@Controller
@EnableWebMvc
public class RatingsController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("ratingService")
	RatingService ratingService;
	
	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	private static final Logger logger = LoggerFactory.getLogger(RatingsController.class);
	
	@PostMapping(value = "/microposts/{id}/rating")
	public String create(@PathVariable Integer id,@RequestParam Integer star, Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("rating");
		
		MicropostModel micropost = new MicropostModel();
		micropost.setId(id);
		micropost.setMicropostRating(star);
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		micropostService.rating(user, micropost, star);
		
		MicropostModel condition = micropostService.findMicropost(id,request);
		condition.setTotalRating(micropostService.getMicropostRatingScore(user,condition));
		model.addAttribute("micropost",condition);
		
		RatingModel ratingModel = new RatingModel();
		ratingModel.setUserId(user.getId());
		ratingModel.setMicropostId(id);
		
		List<RatingModel> ratings = ratingService.findAllRatingUser(ratingModel);
		model.addAttribute("ratings", ratings);
		
		//return "microposts/_rating :: copy";
		return "rating/_rating_show :: copy";
	}
	
	@GetMapping(value = "/microposts/{id}/users/rating")
	public String showRatingUser(@PathVariable Integer id, Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("rating");
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		RatingModel ratingModel = new RatingModel();
		ratingModel.setUserId(user.getId());
		ratingModel.setMicropostId(id);
		
		List<RatingModel> ratings = ratingService.findAllRatingUser(ratingModel);
		model.addAttribute("ratings", ratings);
		
		return "rating/_rating_show :: copy";
	}

}
