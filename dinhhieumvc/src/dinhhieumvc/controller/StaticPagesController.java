package dinhhieumvc.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.CommentService;
import dinhhieumvc.service.MicropostService;

@Controller
public class StaticPagesController {
	
	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	@Autowired
	@Qualifier("commentService")
	CommentService commentService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request, @RequestParam(name = "page", required = false) Optional<Integer> page) {
		
		MicropostModel micropost = new MicropostModel();
		model.addAttribute("micropost", micropost);
		
		if (request.getSession().getAttribute("user") != null) {
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			model.addAttribute("user", user);
			MicropostModel condition = new MicropostModel();
			
			condition.setPage(page.orElse(1));
			condition.setUserId(user.getId());
			Page<MicropostModel> microposts = micropostService.paginate(condition);
			model.addAttribute("microposts", microposts);
		}
		return "static_pages/home";
	}

}
