package pl.biblioteka.service;

import java.util.List;

import pl.biblioteka.entity.Book;
import pl.biblioteka.entity.ChangePassword;
import pl.biblioteka.entity.User;
import pl.biblioteka.enums.ChangePasswordEnum;
import pl.biblioteka.enums.UserEnum;

public interface UserService {
	void save(User user);
	User findByLogin(String login);
	UserEnum checkUser(User user);
	List<User> getAllUsers();
	void mergeBook(Book book, User user, String isbn);
	List<Book> getUserBooks(String login);
	void dropBook(Book book, User user, String isbn);
	ChangePasswordEnum changePassword(String login, ChangePassword changePassword);
	List<User> findAll();
}
