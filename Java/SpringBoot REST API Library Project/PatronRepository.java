package com.cst438;

import org.springframework.data.repository.CrudRepository;

public interface PatronRepository extends CrudRepository <Patron, Integer>{
	public Patron findById(int patron_id);
		
}
