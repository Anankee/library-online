package pl.biblioteka.service;

import java.util.List;

import pl.biblioteka.entity.Book;

public interface BookService {
	
	boolean save(Book book);
	List<Book> bookList();
	Book findBook(Long id);
	void delete(Long id);
}
