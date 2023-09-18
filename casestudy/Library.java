package com.java.casestudy;

import java.util.*;
import java.io.Serializable;

public class Library implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Set<Book> library = new HashSet<Book>();
	
	public void registerBook(Book _bookObj) {
		library.add(_bookObj);
	}
	
	public Book retrieveBookGivenIsbn(String _isbn) {
		Book book = null;
		
		for (Book i_book : library) {
			if (i_book.getIsbn().equals(_isbn)) {
				book = i_book;
				break;
			}
		}
		
		return book;
	}
	
	public boolean updateBookDetails(String _isbn, String _updatedBookName) {
		boolean updated = false;
		
		Book book = this.retrieveBookGivenIsbn(_isbn);
		if (book != null) updated = book.setBookName(_updatedBookName);
		
		return updated;
	}
	
	public boolean deleteBook(String _isbn) {
		boolean deleted = false;

		Book book = this.retrieveBookGivenIsbn(_isbn);
		if (book != null) deleted = library.remove(book);
		
		return deleted;
	}
	
}
