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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.BookmarkModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.BookmarkService;
import dinhhieumvc.service.MicropostService;

@Controller
@EnableWebMvc
public class BookmarkController {
	
	
	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	@Autowired
	@Qualifier("bookmarkService")
	BookmarkService bookmarkService;
	
	private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
	
	@PostMapping(value = "/microposts/{id}/bookmark")
	public String create(@PathVariable Integer id, Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("bookmark");
		
		MicropostModel micropost = new MicropostModel();
		micropost.setId(id);
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		BookmarkModel bookmark =micropostService.bookmark(user, micropost);
		model.addAttribute("bookmark",bookmark);
		
		MicropostModel condition = micropostService.findMicropost(id, request);
		model.addAttribute("micropost",condition);
		
		return "bookmarks/_bookmark_form:: copy";
	}
	
	@GetMapping(value = "/bookmarks")
	public String index(Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("list Bookmark");
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		BookmarkModel bookmarkModel = new BookmarkModel();
		bookmarkModel.setUserId(user.getId());
	
		List<BookmarkModel> bookmarks = bookmarkService.findAllMicropostBookmark(bookmarkModel);
		model.addAttribute("bookmarks", bookmarks);
		
		return "bookmarks/show";
	}

}
