package pl.biblioteka.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.biblioteka.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, Long>{
	
	
	@Query("SELECT u FROM User u WHERE u.login = ?1")
	User findByLogin(@Param("login") String login);
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(@Param("email") String email);

}
