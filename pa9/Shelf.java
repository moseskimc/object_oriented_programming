package edu.brandeis.cs12b.pa9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;

public class Shelf {
	private int capacity;
	private int numberOfBooks = 0;
	private Book[] books;
	private int counter = 0;
	private JSONArray JShelf = new JSONArray();
	
	
	public Shelf(int capacity){
		this.capacity = capacity;
		books = new Book[capacity];
	}
	
	
	public Shelf(JSONArray array){
		this.capacity = array.getInt(0);
		books = new Book[getCapacity()];
		
		for (int i = 0; i < books.length; i++){
			books[i] = new Book( array.getJSONObject(i+1));
		}
		
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	
	public int availableSpots(){
		return capacity - numberOfBooks;
	}
	
	public void setBook(String title, int i, int j, int k){
		books[counter] = new Book(title,i,j,k);
		counter++;
		numberOfBooks++;
		
	}
	
	public Book findBook(String title){
		for (int i = 0; i < books.length; i++){
			if (books[i].getTitle().compareTo(title)==0){
				return books[i];
			}
		}
		
		return null;
	}
	
	
	public Set<String> getBooks(){
		
		Set<String> temp = new HashSet<String>();
		
		for (int i = 0; i < books.length; i++){
			if (books[i].isAvailable()){
				temp.add(books[i].getTitle());
			}
			
		}
		
		return temp;
	}
	
	public int getNumberOfBooks(){
		return numberOfBooks;
	}
	
	public JSONArray getJSON(){
		//to keep track of the shelf capacity we make our first entry in our
		//JSONArray object an integer representing it.
		
		JShelf.put(capacity);
		
		for (int i = 0; i < books.length; i++){
			JShelf.put(books[i].getJSON());
		}
		
		return JShelf;
	}

	//we write a method that returns a list of all book the shelf.
	public ArrayList<Book> getShelfBooks() {		
		return new ArrayList<Book>(Arrays.asList(books));
	}
	
	
	
}
