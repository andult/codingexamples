package com.cst438;

public class BookDTO {
	public int book_id;
	public String title;
	public String author;
	public String checkout_date;
	public Integer checkout_patron_id;
	
	@Override
	public String toString() {
		return "BookDTO [book_id=" + book_id + ", title=" + title + ", author=" + author + ", checkout_date="
				+ checkout_date + ", checkout_patron_id=" + checkout_patron_id + "]";
	}
}
