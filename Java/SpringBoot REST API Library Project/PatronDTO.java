package com.cst438;

public class PatronDTO {
	public int patron_id;
	public String name;
	public float fines;
	
	@Override
	public String toString() {
		return "PatronDTO [patron_id=" + patron_id + ", name=" + name + ", fines=" + fines + "]";
	}
}
