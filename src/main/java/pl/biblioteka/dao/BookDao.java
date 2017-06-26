package pl.biblioteka.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.biblioteka.entity.Book;

public interface BookDao extends CrudRepository<Book, Long>{

	@Query("SELECT b FROM Book b WHERE b.title = ?1")
	Book findByTitle(@Param("title") String title);
	
	
	@Query("SELECT b FROM Book b WHERE b.isbn = ?1")
	Book findByIsbn(@Param("isbn") String isbn);


}
