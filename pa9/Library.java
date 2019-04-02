package edu.brandeis.cs12b.pa9;

import java.io.File;
import java.util.Set;

/**
 * You should extend this class in order to provide your own implementation.
 * 
 * This class' iterator() method should return an iterator over all the books
 * it has, including checked out books.
 *
 */
public abstract class Library implements Iterable<String> {
	
	/**
	 * Returns the number of floors this library has
	 * @return the number of floors
	 */
	public abstract int getNumberOfFloors();
	
	/**
	 * Returns the number of cases on the nth floor (floors start at 0)
	 * @param floor 
	 * @return the number of cases
	 */
	public abstract int getCasesOnFloor(int floor);
	
	/**
	 * Returns the number of shelves in a given case on a given floor
	 * @param floor
	 * @param cas
	 * @return the number of shelves
	 */
	public abstract int getShelvesInCase(int floor, int cas);
	
	/**
	 * Returns the capacity of the nth shelf at the particular floor and case
	 * @param floor
	 * @param cas
	 * @param shelf
	 * @return the shelf capacity
	 */
	public abstract int getCapacityOfShelf(int floor, int cas, int shelf);

	
	/**
	 * Adds a new book and returns its location. If there is no space for the new book,
	 * return null.
	 * @param title the title of the book to add
	 * @return the location of the book or null
	 */
	public abstract BookLocation addNewBook(String title);
	
	/**
	 * Return the location of the book with the given title, or null if
	 * it is not in the library
	 * @param title the title of the book to lookup
	 * @return the book's location
	 */
	public abstract BookLocation getLocationOfBook(String title);
	
	/**
	 * Return the set of all books at the given location, or null if the location is invalid
	 * @param loc the location to list the books at (only checked in books should be listed)
	 * @return the set of books, or null
	 */
	public abstract Set<String> getBooksAt(BookLocation loc);
	
	
	/**
	 * Checks out a book
	 * @param title the book to check out
	 * @return true if the book can be checked out
	 */
	public abstract boolean checkOut(String title);
	
	/**
	 * marks a book as checked in
	 * @param title the book
	 */
	public abstract void checkIn(String title);
	
	
	/**
	 * Writes the contents of the library to the passed file
	 * in a format compatible with the LibraryFactory's makeLibraryFromFile method.
	 * @param f the file to write the library to
	 */
	public abstract void writeToFile(File f);
	
}
