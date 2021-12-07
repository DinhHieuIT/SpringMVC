package dinhhieumvc.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dinhhieumvc.model.BuyModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.BuyService;
import dinhhieumvc.service.MicropostService;

@Controller
@EnableWebMvc
public class BuyController {
	
	@Autowired
	@Qualifier("micropostService")
	MicropostService micropostService;
	
	@Autowired
	@Qualifier("buyService")
	BuyService buyService;
	
	private static final Logger logger = LoggerFactory.getLogger(BuyController.class);
	
	@PostMapping(value = "/microposts/{id}/buy")
	@ResponseBody
	public ResponseEntity<String> createBuy(@PathVariable Integer id,@RequestParam Integer value, Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("Add to cart " +id+" "+ value);
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		BuyModel buyModel = buyService.buy(user.getId(), id, value);
		Integer totalBuyOneMicropost = buyModel.getQuantity();

		return new ResponseEntity<String>("{\"count\" :" + totalBuyOneMicropost + "}", HttpStatus.OK);
	}
	
	@PostMapping(value = "/buy/{id}/add")
	@ResponseBody
	public ResponseEntity<String> addBuy(@PathVariable Integer id,@RequestParam Integer value, 
			Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("add Buy "+id+" "+ value);
		BuyModel buyModel = buyService.buyAdd(id, value);
		Integer totalBuyOneMicropost = buyModel.getQuantity();

		return new ResponseEntity<String>("{\"count\" :" + totalBuyOneMicropost + "}", HttpStatus.OK);
	}
	
	@PostMapping(value = "/buy/{id}/remove")
	@ResponseBody
	public ResponseEntity<String> removeBuy(@PathVariable Integer id,@RequestParam Integer value, 
			Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("Remove buy "+id+" "+ value);
		BuyModel buyModel = buyService.buyRemove(id, value);
		String totalBuyOneMicropost = String.valueOf(buyModel.getQuantity());
	    return new ResponseEntity<String>("{\"count\" :" + totalBuyOneMicropost + "}", HttpStatus.OK);
	}
	
	@GetMapping(value = "/carts")
	public String show(Locale locale, Model model, HttpServletRequest request) 
			throws Exception {
		logger.info("list carts");
		UserModel user =(UserModel) request.getSession().getAttribute("user");
		List<BuyModel> buys = buyService.findAllBuyModel(user);
		model.addAttribute("buys", buys);
		
		return "buys/show";
	}
	
	

}
