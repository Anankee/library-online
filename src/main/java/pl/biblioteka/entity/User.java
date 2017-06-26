package pl.biblioteka.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Entity
@Table(name = "USER")
public class User extends AbstractPersistable<Long> {

	@Column(name = "NAME")
	@NotEmpty(message = "To pole nie może byc puste")
	private String name;

	@NotEmpty(message = "To pole nie może byc puste")
	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "LOGIN", unique = true)
	@NotEmpty(message = "To pole nie może byc puste")
	@Size(min = 4, message = "To pole musi posiadac minimum 4 znaki")
	private String login;

	@Column(name = "PASSWORD")
	@NotEmpty(message = "To pole nie może byc puste")
	@Size(min = 4, message = "To pole musi posiadac minimum 4 znaki")
	private String password;

	@Transient
	private String confirmPassword;

	@Column(name = "EMAIL", unique = true)
	@NotEmpty(message = "To pole nie może byc puste")
	@Email
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_BOOK",
	joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
	inverseJoinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")})
	private List<Book> book;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public Long getId() {
		return super.getId();
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", login=" + login + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", email=" + email + "]";
	}
	
	
}
