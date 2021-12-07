package dinhhieumvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.service.SearchMicropostService;

@Controller
@EnableWebMvc
public class SearchController {
	
	@Autowired
	@Qualifier("searchmicropostService")
	SearchMicropostService searchmicropostService;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@GetMapping(value = "/search", produces = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public List<MicropostModel> showSearch(@RequestParam String term, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		logger.info("find MicropostModel with given keyword from Micropost in the database");
		List<MicropostModel> microposts = searchmicropostService.findAllMicropostSearch(term);
		logger.info("return microposts");
		return microposts;
	}

}
