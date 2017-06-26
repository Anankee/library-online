package pl.biblioteka.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;

@Entity
@Table(name = "BOOK")
public class Book extends AbstractPersistable<Long> {

	@NotEmpty(message = "To pole nie może być puste")
	@Column(name = "AUTHOR")
	private String author;

	@NotEmpty(message = "To pole nie może być puste")
	@Column(name = "TITLE")
	private String title;

	@NotEmpty(message = "To pole nie może być puste")
	@Column(name = "PUBLISHING")
	private String publishing;

	@NotEmpty(message = "To pole nie może być puste")
	@Column(name = "ISBN", unique = true)
	private String isbn;

	@Min(0)
	@Column(name = "AMOUNT")
	private Long amount;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "book")
	private Set<User> user;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	public String toString() {
		return "Book [id = " + getId() + " author=" + author + ", title=" + title + ", publishing=" + publishing + ", isbn=" + isbn
				+ ", amount=" + amount + "]";
	}

}
