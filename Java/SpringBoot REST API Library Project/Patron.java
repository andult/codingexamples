package com.cst438;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patrons")
public class Patron {

	@Id
	private int patron_id;
	private String name;
	private float fines;
	
	public Patron() {
		super();
	}	
	
	public int getPatron_id() {
		return patron_id;
	}
	public void setPatron_id(int patron_id) {
		this.patron_id = patron_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getFines() {
		return fines;
	}
	public void setFines(float fines) {
		this.fines = fines;
	}

	@Override
	public String toString() {
		return "Patron [patron_id=" + patron_id + ", name=" + name + ", fines=" + fines + "]";
	}	
}
