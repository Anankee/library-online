package pl.biblioteka.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.biblioteka.dao.BookDao;
import pl.biblioteka.dao.UserDao;
import pl.biblioteka.entity.Book;
import pl.biblioteka.entity.ChangePassword;
import pl.biblioteka.entity.User;
import pl.biblioteka.enums.ChangePasswordEnum;
import pl.biblioteka.enums.UserEnum;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public User findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Override
	public UserEnum checkUser(User user) {
		boolean login, email;
		boolean pass = user.getPassword().equals(user.getConfirmPassword());
		if (userDao.findByLogin(user.getLogin()) == null){
			login = true;
		} else {
			login = false;
		}

		if (email = userDao.findByEmail(user.getEmail()) == null){
			email = true;
		} else {
			email = false;
		}

		if (pass && login && email) {
			System.out.println("--passed--");
			return UserEnum.PASSED;
		} else if (!pass && login && email) {
			System.out.println("--hasło--");
			return UserEnum.PASSWORD;
		} else if (pass && !login && email) {
			System.out.println("--login--");
			return UserEnum.LOGIN;
		} else if (pass && login && !email) {
			System.out.println("--email--");
			return UserEnum.EMAIL;
		} else if (!pass && !login && email) {
			System.out.println("--login-password--");
			return UserEnum.LOGIN_PASSWORD;
		} else if (!pass && login && !email) {
			System.out.println("--email-password--");
			return UserEnum.EMAIL_PASSWORD;
		} else if (pass && !login && !email) {
			System.out.println("--login-password--");
			return UserEnum.LOGIN_PASSWORD;
		} else {
			System.out.println("--all--");
			return UserEnum.ALL;
		}
	}

	@Override
	public List<User> getAllUsers() {
		 return (List<User>) userDao.findAll();
	}

	@Override
	public void mergeBook(Book book, User user, String isbn){
		List<Book> list = user.getBook();
		if (isbn.equals(book.getIsbn())) {
			if (list.contains(book)) {
				System.out.println("jest");
				//do nothing
			} else {
				System.out.println("dodane");
				book.setAmount(book.getAmount() - 1);
				bookDao.save(book);
				list.add(book);
				user.setBook(list);
				save(user);
			}
		}
	}

	@Override
	public List<Book> getUserBooks(String login) {
		return userDao.findByLogin(login).getBook();

	}

	@Override
	public void dropBook(Book book, User user, String isbn) {
		book.setAmount(book.getAmount() + 1);
		bookDao.save(book);
		List<Book> userBooks = user.getBook();
		userBooks.remove(book);
		user.setBook(userBooks);
		userDao.save(user);
	}

	@Override
	public ChangePasswordEnum changePassword(String login, ChangePassword changePassword) {
		User user = userDao.findByLogin(login);
		boolean equals = changePassword.getOldPassword().equals(user.getPassword());
		boolean confirmPass = changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword());
		if (equals == true && confirmPass == true){
			user.setPassword(changePassword.getNewPassword());
			userDao.save(user);
			return ChangePasswordEnum.PASSED;
		} else if (!equals && confirmPass){
			return ChangePasswordEnum.PASSWORD;
		} else if (equals && !confirmPass){
			return ChangePasswordEnum.PASSWORD_CONFIRM;
		} else {
			return ChangePasswordEnum.ERROR;
		}
	}

	@Override
	public List<User> findAll() {
		Iterable<User> findAll = userDao.findAll();
		List<User> usersList = new ArrayList<>();
		findAll.forEach(usersList::add);
		return usersList;
	}
}
