package com.java.casestudy;

import java.util.*;
import java.io.*;

public class Main {
	
	static void validate(String _isbn) throws BookNotFoundException {
		if (!isValidIsbn(_isbn)) 
			throw new BookNotFoundException("Invalid ISBN.");
	}
	
	static void validate(Book _bookObj) throws BookNotFoundException {
		if (_bookObj == null) 
			throw new BookNotFoundException("No Book Details Were Found");
	}
	
	static void validate(boolean _flag) throws BookNotFoundException {
		if (!_flag) 
			throw new BookNotFoundException("No Book Details Were Found");
	}
	
	static boolean isValidIsbn(String _isbn) {
		/* 
		 * ISBN is a 10 digit unique number
		 * All the digits can take any value between 0 to 9
		 * But the last digit sometimes may take value equal to 10 (written as 'X')
		 * 
		 * The ISBN number is valid when
		 * 1*digit_1 + 2*digit_2 + 3*digit_3 + 4*digit_4 + 5*digit_5 + 
		 * 6*digit_6 + 7*digit_7 + 8*digit_8 + 9*digit_9 + 10*digit_10
		 * is divisible by 11
		 * The digits are taken from right to left
		*/
		
		int n = _isbn.length();
		if (n != 10) return false;
		
		// Compute weighted sum of first 9 digits
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			int digit = _isbn.charAt(i) - '0';
			if (0 > digit || 9 < digit) return false;
			sum += digit * (10 - i);
		}
		
		char last = _isbn.charAt(9);
		if (last != 'X' && (last < '0' || last > '9')) return false;
		
		// If last digit is 'X', add 10 to sum, else add its value
		sum += last == 'X' ? 10 : (last - '0');
		
		// Return true if weighted sum of digits is divisible by 11
		return (sum % 11 == 0);
	}
	
	public static void serialize(Library _library, String _fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(_fileName);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			
			objOut.writeObject(_library);
			
			objOut.close();
			fileOut.close();
		}
		catch (IOException e) {
			System.out.println(_fileName + ": Data Serialization Failed.");
		}
	}
	
	public static Library deserialize(String _fileName) {
		Library library = null;
		
		try {
			FileInputStream fileIn = new FileInputStream(_fileName);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			
			library = (Library)objIn.readObject();
			
			objIn.close();
			fileIn.close();
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println(_fileName + ": File Not Found.");
		}
		
		return library;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String fileName = "records.ser";
		String t_isbn, t_bookName;
		boolean status = true;

		Book book = null;
		Library library = new Library();
		
		Library t_library = deserialize(fileName);
		if (t_library != null) library = t_library;
		
		do {
			try {
				t_isbn = t_bookName = null;
			
				// Main Menu
				System.out.println("\nBOOK MANAGEMENT");
				System.out.println("1. Register\n2. Retrieve\n3. Update\n4. Delete\n5. Exit");
				System.out.print("Enter your choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
			
				switch (choice) {
				case 1:
					// Book Registration
					System.out.print("Enter ISBN: ");
					t_isbn = sc.nextLine();
				
					validate(t_isbn);
					
					System.out.print("Enter Book Name: ");
					t_bookName = sc.nextLine();
					
					book = new Book(t_isbn, t_bookName);
					library.registerBook(book);
					
					break;
				
				case 2:
					// Retrieve Book Details
					System.out.print("Enter ISBN: ");
					t_isbn = sc.nextLine();
				
					validate(t_isbn);
					
					book = library.retrieveBookGivenIsbn(t_isbn);
					
					validate(book);
					
					System.out.println("Book ISBN: " + book.getIsbn());
					System.out.println("Book Name: " + book.getBookName());

					break;
				
				case 3:
					// Updating Book Details 
					System.out.print("Enter ISBN: ");
					t_isbn = sc.nextLine();
				
					validate(t_isbn);

					System.out.print("Enter Updated Book Name: ");
					t_bookName = sc.nextLine();
					
					boolean updated = library.updateBookDetails(t_isbn, t_bookName);
					
					if (updated) System.out.println("Book Details Updated Successfully.");
					else validate(updated);
					
					break;
				
				case 4:
					// Book Removal
					System.out.print("Enter ISBN: ");
					t_isbn = sc.nextLine();
					
					validate(t_isbn);
				
					System.out.print("Do You Want To Remove The Book (Y/N): ");
					String confirm = sc.nextLine().toUpperCase();
				
					if (confirm.equals("Y")) {
						boolean deleted = library.deleteBook(t_isbn);
					
						if (deleted) System.out.println("Book Removed Successfully.");
						else validate(deleted);
					}
				
					break;
				
				case 5:
					status = false;
					break;
				
				default: break;
				}
				
			}
			catch (BookNotFoundException e) {
				System.out.println("Exception Occured: " + e.getMessage());
			}
			
		} while (status);

		serialize(library, fileName);
		sc.close();			
	}
	
}
