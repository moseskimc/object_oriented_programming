package edu.brandeis.cs12b.pa9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;

public class MyLibrary extends Library{
	private int numberOfFloors;
	private Floor[] floors;
	private List<Book> books = new ArrayList<Book>();
	private List<Book> checkedBooks = new ArrayList<Book>();
	private JSONArray JLibrary = new JSONArray();

	
	//we need a constructor that doesn't accept any parameters because we will use such 
	//constructor when we construct a library from a JSONArray object!
	public MyLibrary(){
		
	}
	
	
	public MyLibrary(int numberOfFloors, int[] numberOfCasesOnFlr, int[][] numberOfShelvesOnFlrCs, int[][][] shelfCapacity){
		this.numberOfFloors = numberOfFloors;
		floors = new Floor[numberOfFloors];
		
		//since each floor can have different number of cases we record that information
		//initializing every floor, every case on a floor, and every shelf on a shelf.
		for (int i = 0; i < floors.length; i++){
			floors[i] = new Floor(numberOfCasesOnFlr[i]);
			for (int j = 0; j < numberOfCasesOnFlr[i]; j ++){
				floors[i].setCase(numberOfShelvesOnFlrCs[i][j]); 
				for (int k = 0; k < numberOfShelvesOnFlrCs[i][j]; k++){
					floors[i].getCase(j).setShelf(shelfCapacity[i][j][k]);
				}
			}
		}
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>(){
			int counter = 0;
			@Override
			public boolean hasNext() {
				try {
					return books.get(counter)!=null;
				}
				catch (Exception e){
					return false;
				}
			}

			@Override
			public String next() {
				if (hasNext()){
					String temp = books.get(counter).getTitle();
					counter++;
					return temp;
				}
				return null;	
			}	
		};	
	}
	
	@Override
	public int getNumberOfFloors() {
		return this.numberOfFloors;
	}
	
	@Override
	public int getCasesOnFloor(int floor) {
		return floors[floor].getNumber();
	}
	
	@Override
	public int getShelvesInCase(int floor, int cas) {
		return floors[floor].getCase(cas).getNumber();
	}
	
	@Override
	public int getCapacityOfShelf(int floor, int cas, int shelf) {
		return floors[floor].getCase(cas).getShelf(shelf).getCapacity();
	}
	
	@Override
	public BookLocation addNewBook(String title) {
		Book temp = null;
		int flrind = -1;
		int caseind = -1;
		int shlfind = -1;
		
		//We search for the next available shelf on floor i and case j.
		for (int i = 0; i < floors.length; i ++){
			for (int j = 0; j < floors[i].getNumber(); j++){
				if (shelfVacantAt(i,j) != -1){
					flrind = i;
					caseind = j;
					shlfind = (shelfVacantAt(i,j));
					temp = new Book(title,i,j,shlfind);
					j = floors[i].getNumber();	
				}	
			}
		}

		//we update shelf availability and add book to the available shelf and our inventory!
		try {
			floors[flrind].getCase(caseind).getShelf(shlfind).setBook(title, flrind, caseind, shlfind);
			books.add(temp);
			return temp.getLocation();
		}
		catch (Exception e){
			System.out.println("There's no more room!");
		}
		
		return null;
	}

	
	//returns -1 if there is no vacancy and the index of the book array of shelf object Shelf[i][j].
	public int shelfVacantAt(int i, int j){
		
		for (int k = 0; k < floors[i].getCase(j).getNumber() ; k++){		
			if (floors[i].getCase(j).getShelf(k).availableSpots() > 0) {
				return k;
			}
		}
		return -1;
	}
	
	@Override
	public BookLocation getLocationOfBook(String title) {
		Book temp = new Book(title);
		
		for (Book book: books){
			if (book.getTitle().compareTo(title) == 0){
				temp = book;
			}
		}
		
		try{
			temp.getLocation();
		}
		catch (Exception e){
			return null;
		}
		return temp.getLocation();	
	}
	
	@Override
	public Set<String> getBooksAt(BookLocation loc) {
		return floors[loc.getFloor()].getCase(loc.getCas()).getShelf(loc.getShelf()).getBooks();
	}
	
	@Override
	public boolean checkOut(String title) {
		//first find the book, then assign true to the boolean variable isChecked
		//then add the book to the checked list.
		
		boolean flag = false;
		
		//Get the location
		BookLocation temp = getLocationOfBook(title);
		
		//now we check it out by finding the book in the corresponding shelf object.
		Book temp2  = floors[temp.getFloor()].getCase(temp.getCas()).getShelf(temp.getShelf()).findBook(title);
		
		temp2.checkOut();
		
		for (Book book: books){
			if (book.getTitle().compareTo(title) == 0){
				book.checkOut();
				checkedBooks.add(book);
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public void checkIn(String title) {
		
		//we can't check in any book. We check in books that are in circulation. So we first
		//make sure the book belongs to the library
		
		Book temp = new Book(title);
		boolean isInCirc = false;
		
		for (Book book: books){
			if (book.getTitle().compareTo(title) == 0){
				isInCirc = true;
				temp = book;
			}
		}
		
		//If it is in circulation we check it back in.
		if (isInCirc){
			BookLocation temp1 = getLocationOfBook(title);
			Book temp2  = floors[temp1.getFloor()].getCase(temp1.getCas()).getShelf(temp1.getShelf()).findBook(title);
			temp2.checkIn();
			
			temp.checkIn();
			checkedBooks.remove(temp);
		}
		else {
			System.out.println("Library does not own this book!");
		}
		
	}
	
	@Override
	public void writeToFile(File f) {			
		OutputStream os = null;
		try {
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			os.write(makeJSON().toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JSONArray makeJSON(){
		for (int i = 0; i < floors.length; i++) {
			JLibrary.put(floors[i].getJSON());
		}
		return JLibrary;
	}
	
		
	public Floor getFloor(int index){
		return floors[index];
	}
	
	//This method is used to add to our inventory "books" one book at a time.
	public void addBook(Book book){
		books.add(book);
	}
	
	//Accepts as parameter a JSONArray representing a library and builds
	//the structure of each floor.
	public void makeFloors(JSONArray array){
		floors = new Floor[array.length()];
		this.numberOfFloors = array.length();
		for (int i = 0; i < array.length(); i++){
			floors[i] = new Floor((JSONArray) array.get(i));
		}
	}
	
	//This method is used after the above method makeFloors is called on a newly
	//constructed library (second constructor w/o parameter) in order to add all
	//of the books to the inventory.
	public void addBooks(){
		ArrayList<Book> temp = new ArrayList<>();
		
		for (int i = 0; i < floors.length; i++){
			temp.addAll(floors[i].getBooks());
		}
		
		books = temp;
	}
	
	
	
	
}
