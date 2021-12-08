package dinhhieu.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dinhhieu.entity.VaccinedPeople;
import dinhhieu.service.VaccinedPeopleService;

@Controller
public class VaccinedPeopleController {

	@Autowired
	private VaccinedPeopleService vaccinedPeopleService;
	
	@RequestMapping("/")
	public ModelAndView home() {
		List<VaccinedPeople> listVaccinedPeople = vaccinedPeopleService.listAll();
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("listVaccinedPeople", listVaccinedPeople);
		return mav;
	}
	
	@RequestMapping("/new")
	public String newPeopleForm(Map<String, Object> model) {
		VaccinedPeople people = new VaccinedPeople();
		model.put("people", people);
		return "new_people";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePeople(@ModelAttribute("people") VaccinedPeople people) {
		vaccinedPeopleService.save(people);
		return "redirect:/";
	}
	
	@RequestMapping("/edit")
	public ModelAndView editPeopleForm(@RequestParam long id) {
		ModelAndView mav = new ModelAndView("edit_people");
		VaccinedPeople people = vaccinedPeopleService.get(id);
		mav.addObject("people", people);
		
		return mav;
	}
	
	@RequestMapping("/delete")
	public String deletePeopleForm(@RequestParam long id) {
		vaccinedPeopleService.delete(id);
		return "redirect:/";		
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		List<VaccinedPeople> result = vaccinedPeopleService.search(keyword);
		ModelAndView mav = new ModelAndView("search");
		mav.addObject("result", result);
		
		return mav;		
	}	
}
