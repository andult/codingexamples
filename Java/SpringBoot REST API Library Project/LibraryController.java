package com.cst438;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LibraryController {

	@Autowired
	BookRepository bookRepository;		//connects to book table
	
	@Autowired
	PatronRepository patronRepository;	//connects to patron table
	
	//ADD BOOK TO PATRON---------------------------------------------
	@PutMapping("/book/{book_id}/checkout/{patron_id}")
	public int addBookToPatron(@PathVariable("book_id") int book_id, @PathVariable("patron_id") int patron_id) {
		Patron p = patronRepository.findById(patron_id);
		Book b = bookRepository.findById(book_id);
		
		if(p != null && p.getFines() == 0.0) {
			if(b != null) {
				b.setCheckout_patron_id(patron_id);
				b.setCheckout_date(new java.sql.Date(System.currentTimeMillis()).toString());
				bookRepository.save(b);
				return 0;
			}
			else {
				throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "Book doesn't exists or error was encountered.");
			}
		}
		else if (p != null && p.getFines() > 0.0) {
			return -3;
		}
		else {
			throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "Patron doesn't exists or error was encountered.");
		}
		
	}
	
	//RETURN BOOK TO LIBRARY-----------------------------------------
	@PutMapping("/book/{book_id}/return")
	public int returnBook(@PathVariable("book_id") int book_id) {
		Book b = bookRepository.findById(book_id);
		if(b != null) {
			b.setCheckout_patron_id(null);
			bookRepository.save(b);
		}
		else {
			throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "Book doesn't exists or error was encountered.");
		}
		
		return 0;
	}
	
	//LIST BOOKS UNDER PATRON----------------------------------------
	@GetMapping("/patron/{patron_id}/checkouts")
	public List<Book> listBooks(@PathVariable("patron_id") int patron_id){
		Patron p = patronRepository.findById(patron_id);
		
		if(p != null) {
			List<Book> books = bookRepository.findBookByPatron(p.getPatron_id());			
			return books;
		}
		else {
			throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "Patron doesn't exists or error was encountered.");
		}
	}
	
	//RETURN PATRON INFO---------------------------------------------
	@GetMapping("/patron/{patron_id}")
	public Patron getPatron(@PathVariable("patron_id") int patron_id) {
		Patron p = patronRepository.findById(patron_id);
		
		if(p != null) {
			return p;
		}
		else {
			throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "Patron doesn't exists or error was encountered.");
		}	
	}
	
}
