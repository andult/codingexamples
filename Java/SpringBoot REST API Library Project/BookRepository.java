package com.cst438;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends CrudRepository <Book, Integer> {
	public Book findById(int id);
	
	@Query("select b from Book b where b.checkout_patron_id=:checkout_patron_id")
	public List<Book> findBookByPatron(
			@Param("checkout_patron_id") int checkout_patron_id);
	
	@SuppressWarnings("unchecked")
	Book save(Book b);
}
