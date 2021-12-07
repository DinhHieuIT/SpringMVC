package dinhhieumvc.controller;

import java.util.Locale;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dinhhieumvc.interceptor.Flash;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.MicropostService;
import dinhhieumvc.service.UserService;

@Controller
@EnableWebMvc
public class UsersController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;

	@Resource
	Flash flash;

	@GetMapping(value = { "/users/add", "/signup" })
	public String add(Locale locale, Model model) {
		UserModel userModel = new UserModel();
		model.addAttribute("user", userModel);
		return "users/add";
	}

	@PostMapping(value = "/accountFacebook")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> create(@RequestParam String uid, @RequestParam String name, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
		UserModel user = userService.findUserByUid(uid);
		if (user == null) {
			UserModel userModel = new UserModel();
			userModel.setUid(uid);
			userModel.setName(name);
			user = userService.addUserFacebook(userModel);
			// Them vao session gia tri user nay
		}
		request.getSession().setAttribute("user", user);
		//return "redirect: " + request.getContextPath() + "/users/" + user.getId();
		return new ResponseEntity<String>("{\"result\" : \"OK\"}", HttpStatus.OK); 
	}

	@PostMapping(value = "/users")
	public String create(@ModelAttribute("user") @Validated UserModel userModel, BindingResult bindingResult,
			Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			logger.info("Returning register.jsp page, validate failed");
			return "users/add";
		}

		UserModel user = userService.addUser(userModel);
		// Them vao session gia tri user nay
		request.getSession().setAttribute("user", user);
		return "redirect: " + request.getContextPath() + "/users/" + user.getId();
	}

	@GetMapping(value = "/users/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("user", userService.findUser(id));
		return "users/edit";
	}

	// Show user List
	@GetMapping(value = "/users")
	public String index(@RequestParam(name = "page", required = false) Optional<Integer> page, Locale locale,
			Model model, HttpServletRequest request) {
		// List<UserModel> userModels = userService.findAllUser();
		UserModel userModel = new UserModel();
		userModel.setPage(page.orElse(1));
		Page<UserModel> users = userService.paginate(userModel);
		model.addAttribute("users", users);
		return "users/index";
	}

	@PutMapping(value = "/users/{id}")
	public String update(@ModelAttribute("user") @Validated UserModel userModel, BindingResult bindingResult,
			Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			logger.info("Returning edit.jsp page, validate failed");
			return "users/edit";
		}
		UserModel user = userService.editUser(userModel);
		// Add message to flash scope
		flash.success("user.update.success");
		flash.keep();
		return "redirect: " + request.getContextPath() + "/users/" + user.getId();
	}

	// Show user information
	@GetMapping(value = "/users/{id}")
	public String show(@PathVariable Integer id, @RequestParam(name = "page", required = false) Optional<Integer> page,
			Model model, HttpServletRequest request) {

		UserModel condition = new UserModel();
		condition.setId(id);
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		condition.setCurrentUserId(user.getId());

		UserModel userModel = userService.getUserInfo(condition);
		model.addAttribute("user", userModel);

		MicropostModel micropostModel = new MicropostModel();
		micropostModel.setUserId(userModel.getId());
		micropostModel.setPage(page.orElse(1));
		Page<MicropostModel> microposts = micropostService.paginate(micropostModel);
		model.addAttribute("microposts", microposts);
		return "users/show";
	}

	@DeleteMapping(value = "/users/{id}")
	@ResponseBody
	public ResponseEntity<String> destroy(@PathVariable Integer id, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Deleting user: " + id);
		userService.deleteUser(new UserModel(id));
		return new ResponseEntity<String>("{\"result\" : \"OK\", \"id\" : " + id + "}", HttpStatus.OK);
	}

}
