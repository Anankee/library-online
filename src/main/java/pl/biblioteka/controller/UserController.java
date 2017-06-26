package pl.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.biblioteka.entity.ChangePassword;
import pl.biblioteka.service.BookService;
import pl.biblioteka.service.UserService;

import javax.validation.Valid;

/**
 * Created by papi on 16.06.17.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;


    @GetMapping("myBooks")
    public ModelAndView getMyBooks(){
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("login", auth.getName());
        mav.addObject("userBooks", userService.getUserBooks(auth.getName()));
        return mav;
    }

    @GetMapping("myBooks/{id}drop{isbn}")
    public ModelAndView postMyBooks(@PathVariable("id") Long id, @PathVariable("isbn") String isbn){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.dropBook(bookService.findBook(id), userService.findByLogin(auth.getName()), isbn);
        return new ModelAndView("redirect:/myBooks");
    }

    @GetMapping("myProfile")
    public ModelAndView getMyProfile(){
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("login", auth.getName());
        mav.addObject("user", userService.findByLogin(auth.getName()));
        mav.addObject("changePassword", new ChangePassword());
        return mav;
    }

    @PostMapping("myProfile")
    public ModelAndView postMyProfile(@ModelAttribute @Valid ChangePassword changePassword, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("login", auth.getName());
        mav.addObject("user", userService.findByLogin(auth.getName()));
        if (bindingResult.hasErrors()){
            return mav;
        } else {
            switch (userService.changePassword(auth.getName(), changePassword)){
                case ERROR:
                    bindingResult.addError(new FieldError("changePassword", "oldPassword", "Hasło się nie zgadza"));
                    bindingResult.addError(new FieldError("changePassword", "confirmNewPassword", "Hasła nie pasują"));
                    break;
                case PASSWORD:
                    bindingResult.addError(new FieldError("changePassword", "oldPassword", "Hasło się nie zgadza"));
                    break;
                case PASSWORD_CONFIRM:
                    bindingResult.addError(new FieldError("changePassword", "confirmNewPassword", "Hasła nie pasują"));
                    break;
                case PASSED:
                    return new ModelAndView("redirect:/myProfile");
            }
        }
        return mav;
    }

}
