package dinhhieumvc.controller;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dinhhieumvc.interceptor.Flash;
import dinhhieumvc.model.CommentModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.BuyService;
import dinhhieumvc.service.CommentService;
import dinhhieumvc.service.MicropostService;


@Controller
@EnableWebMvc
public class MicropostsController {
	
	private static final Logger logger = LoggerFactory.getLogger(MicropostsController.class);
	
	@Resource
	Flash flash;

	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	@Autowired
	@Qualifier("commentService")
	CommentService commentService;
	
	@Autowired
	@Qualifier("buyService")
	BuyService buyService;
	
	@PostMapping(value = "/microposts")
	public String create(@ModelAttribute("micropost") @Validated MicropostModel micropostModel,
			BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes,
			 HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			logger.info("Returning add micropost page, validate failed");
			if (request.getSession().getAttribute("user") != null) {
				return "static_pages/home";
			}
		}
 	 if (request.getSession().getAttribute("user") != null) {
			UserModel user =(UserModel) request.getSession().getAttribute("user");
			micropostModel.setUserId(user.getId());
			micropostService.addMicropost(micropostModel);	

	}
 	       
		// Add message to flash scope
		flash.success("micropost.create.success");
		flash.keep();
		return "redirect: " + request.getContextPath() + "/home";
	}
	
	@GetMapping(value = "/microposts/{id}")
	public String show(@PathVariable Integer id, Model model, @RequestParam(name = "page", required = false) Optional<Integer> page,
			HttpServletRequest request) {
		
		MicropostModel condition = micropostService.findMicropost(id,request);
		model.addAttribute("micropost",condition);
		
		CommentModel comment = new CommentModel();
		comment.setMicropost(condition);
		comment.setMicropostId(condition.getId());
		model.addAttribute("comment",comment);
		
		CommentModel commentModel = new CommentModel();
		commentModel.setPage(page.orElse(1));
		commentModel.setMicropostId(condition.getId());
		Page<CommentModel> comments = commentService.paginate(commentModel);
		model.addAttribute("comments", comments);
	
		return "microposts/show";
	}
	
	@GetMapping(value = "/microposts/{id}/comment")
	public String show(@PathVariable Integer id,Model model, final RedirectAttributes redirectAttributes,
			 HttpServletRequest request) throws Exception {
		CommentModel comment = new CommentModel();
		comment.setMicropostId(id);
		
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		comment.setUserId(user.getId());
		model.addAttribute("comment",comment);
		
		return "shared/_comment_form";
	}
	
	@PostMapping(value = "/microposts/{id}/comment")
	public String create(@PathVariable Integer id,@ModelAttribute CommentModel commentModel,
			 Model model, final RedirectAttributes redirectAttributes,
			 HttpServletRequest request) throws Exception {
		
		commentModel.setMicropostId(id);
		
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		commentModel.setUserId(user.getId());
		
		commentService.addComment(commentModel);

		// Add message to flash scope
		flash.success("micropost.create.success");
		flash.keep();
		return "redirect: " + request.getContextPath() + "/microposts/" + id;
	}
	
}
