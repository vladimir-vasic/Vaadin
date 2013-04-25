package rs.codecentric.photo_album;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView printWelcome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Spring security allows you");
		modelAndView.setViewName("welcome");
		return modelAndView;
	}
}