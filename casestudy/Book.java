package com.java.casestudy;

import java.io.Serializable;

public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String isbn, bookName;
	
	// Constructor
	public Book(String _isbn, String _bookName) {
		this.isbn = _isbn;
		this.bookName = _bookName;
	}
	
	// Getter and Setter
	public String getIsbn() {
		return this.isbn;
	}
	
	public String getBookName() {
		return this.bookName;
	}
	
	public boolean setBookName(String _bookName) {
		this.bookName = _bookName;
		return true;
	}
	
}
