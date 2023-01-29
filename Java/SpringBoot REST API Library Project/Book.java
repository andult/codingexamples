package com.cst438;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {

	@Id
	private int book_id;
	private String title;
	private String author;
	private String checkout_date;
	private Integer checkout_patron_id;
	
	public Book() {
		super();
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCheckout_date() {
		return checkout_date;
	}

	public void setCheckout_date(String checkout_date) {
		this.checkout_date = checkout_date;
	}

	public Integer getCheckout_patron_id() {
		return checkout_patron_id;
	}

	public void setCheckout_patron_id(Integer checkout_patron_id) {
		this.checkout_patron_id = checkout_patron_id;
	}

	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", title=" + title + ", author=" + author + ", checkout_date="
				+ checkout_date + ", checkout_patron_id=" + checkout_patron_id + "]";
	}
	
}