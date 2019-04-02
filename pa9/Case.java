package edu.brandeis.cs12b.pa9;

import java.util.ArrayList;

import org.json.JSONArray;

public class Case {
	private int numberOfShelves;
	private Shelf[] shelves;
	private int counter = 0;
	private JSONArray JCase = new JSONArray();
	
	public Case(int numberOfShelves){
		this.numberOfShelves = numberOfShelves;
		shelves = new Shelf[numberOfShelves];
	}
	
	public Case(JSONArray array){
		this.numberOfShelves = array.length();
		shelves = new Shelf[getNumber()];
		for (int i = 0; i < array.length(); i++){
			shelves[i] = new Shelf((JSONArray) array.get(i));
		}
	}
	
	public int getNumber(){
		return this.numberOfShelves;
	}
	
	public Shelf getShelf(int index){
		return shelves[index];	
	}
	
	public void setShelf(int capacity){
		shelves[counter] = new Shelf(capacity);
		counter++;
	}
	
	public JSONArray getJSON(){
		for (int i = 0; i < shelves.length; i++){
			JCase.put(shelves[i].getJSON());
		}
		return JCase;	
	}

	public ArrayList<Book> getBooks() {
		ArrayList<Book> temp = new ArrayList<>();
		
		for (int i = 0; i < shelves.length; i ++){
			temp.addAll( shelves[i].getShelfBooks());
		}
		
		return temp;
	}
	
}
