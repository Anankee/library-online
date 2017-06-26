package pl.biblioteka.controller;

import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.biblioteka.entity.User;
import pl.biblioteka.service.UserService;

@Controller
@RequestMapping
public class StartController {

	@Autowired
	private UserService userServiceImpl;



	@GetMapping()
	public ModelAndView redirect() {
		return new ModelAndView("redirect:/home");
	}

	@GetMapping("register")
	public ModelAndView getRegister() {
		ModelAndView modelAndView = new ModelAndView("register");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		modelAndView.addObject("user", new User());
		modelAndView.addObject("login", auth.getName());
		return modelAndView;
	}

	@PostMapping("register")
	public ModelAndView postRegister(@ModelAttribute @Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("register");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		modelAndView.addObject("login", auth.getName());

		if (bindingResult.hasErrors()) {
			return modelAndView;
		} else {
			switch (userServiceImpl.checkUser(user)) {
			case LOGIN:
				bindingResult.addError(new FieldError("user", "login", "Podany login jest już zajęty"));
				return modelAndView;
			case PASSWORD:
				bindingResult.addError(new FieldError("user", "confirmPassword", "hasła się nie zgadzają"));
				return modelAndView;
			case EMAIL:
				bindingResult.addError(new FieldError("user", "email", "Podany adres e-mail jest już zajęty"));
				return modelAndView;
			case LOGIN_PASSWORD:
				bindingResult.addError(new FieldError("user", "login", "Podany login jest już zajęty"));
				bindingResult.addError(new FieldError("user", "confirmPassword	", "Hasła się nie zgadzają"));
				return modelAndView;
			case EMAIL_PASSWORD:
				bindingResult.addError(new FieldError("user", "email", "Podany adres e-mail jest już zajęty"));
				bindingResult.addError(new FieldError("user", "confirmPassword", "Hasła się nie zgadzają"));
				return modelAndView;
			case LOGIN_EMAIL:
				bindingResult.addError(new FieldError("user", "login", "Podany login jest już zajęty"));
				bindingResult.addError(new FieldError("user", "email", "Podany adres e-mail jest już zajęty"));
				return modelAndView;
			case ALL:
				bindingResult.addError(new FieldError("user", "login", "Podany login jest już zajęty"));
				bindingResult.addError(new FieldError("user", "email", "Podany adres e-mail jest już zajęty"));
				bindingResult.addError(new FieldError("user", "confirmPassword", "Hasła się nie zgadzają"));
				return modelAndView;
			case PASSED:
				userServiceImpl.save(user);
				return new ModelAndView("redirect:/home");
			default:
				return new ModelAndView("redirect:/home");
			}
		}

	}

	@GetMapping("home")
	public ModelAndView getHome(@PathParam("error") String error) {
		ModelAndView modelAndView = new ModelAndView("home");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		modelAndView.addObject("login", auth.getName());
		return modelAndView;
	}
}
