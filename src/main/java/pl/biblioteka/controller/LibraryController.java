package pl.biblioteka.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.biblioteka.entity.Book;
import pl.biblioteka.service.BookService;
import pl.biblioteka.service.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping
public class LibraryController {
	
	@Autowired
	private BookService bookServiceImpl;

	@Autowired
	private UserService userService;

	@GetMapping("booksList")
	public ModelAndView getBooksList() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("booksList", bookServiceImpl.bookList());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals("anonymousUser") || auth.getName().equals("admin")){
			mav.addObject("userBooks", new ArrayList<Book>());
		} else {
			mav.addObject("userBooks", userService.getUserBooks(auth.getName()));
		}
		mav.addObject("login", auth.getName());
		return mav;
	}

	@GetMapping("booksList/{id}and{isbn}")
	public ModelAndView postBookList(@PathVariable("id") Long id, @PathVariable("isbn") String isbn){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userService.mergeBook(bookServiceImpl.findBook(id), userService.findByLogin(auth.getName()), isbn);
		return new ModelAndView("redirect:/booksList");
	}

	@GetMapping("addBook")
	public ModelAndView getAddBook() {
		ModelAndView mav = new ModelAndView("addBook");
		mav.addObject("book", new Book());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("login", auth.getName());
		return mav;
	}

	@PostMapping("addBook")
	public ModelAndView postAddBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView("addBook");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("login", auth.getName());
		if (bindingResult.hasErrors()) {
			return mav;
		} else {
			if (bookServiceImpl.save(book) == true){
				return new ModelAndView("redirect:/addBook?succes");
			} else {
				bindingResult.addError(new FieldError("book", "isbn", "Już istnieje książka z takim ISBNem."));
				return mav;
			}
			
		}
	}
	
	
	@GetMapping("edit/{id}")
	public ModelAndView getEdit(@PathVariable("id") Long id){
		ModelAndView mav = new ModelAndView("editBook");
		Book book = bookServiceImpl.findBook(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("login", auth.getName());
		mav.addObject("book", book);
		return mav;
	}
	
	@PostMapping("edit")
	public ModelAndView postEdit(@ModelAttribute @Valid Book book, BindingResult bindingResult){
		ModelAndView mav = new ModelAndView("editBook");		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("login", auth.getName());
		if (bindingResult.hasErrors()) {
			return mav;
		} else {
			bookServiceImpl.save(book);			
			return new ModelAndView("redirect:/editBook?succes");
		}
	}
	
	@GetMapping("users")
	public ModelAndView getUsers(){
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("login", auth.getName());
		mav.addObject("usersList", userService.findAll());
		return mav;
	}
}
