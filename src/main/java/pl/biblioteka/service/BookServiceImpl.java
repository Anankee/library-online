package pl.biblioteka.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.biblioteka.dao.BookDao;
import pl.biblioteka.entity.Book;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	@Override
	public boolean save(Book book) {
		
		
		if (bookDao.findByIsbn(book.getIsbn()) == null) {
			bookDao.save(book);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Book> bookList() {
		Iterable<Book> findAll = bookDao.findAll();
		List<Book> list = new ArrayList<>();
		findAll.forEach(list::add);
		return list;
	}

	@Override
	public Book findBook(Long id) {
		return bookDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		bookDao.delete(id);
	}
}
