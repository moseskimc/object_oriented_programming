package edu.brandeis.cs12b.pa9;

import java.util.ArrayList;

import org.json.JSONArray;

public class Floor {
	private int numberOfCases;
	private Case[] cases;
	private int counter = 0;
	private JSONArray JFloor = new JSONArray();
	
	public Floor(int numberOfCases){
		this.numberOfCases = numberOfCases;
		cases = new Case[numberOfCases];
	}
	
	public Floor(JSONArray array){
		this.numberOfCases = array.length();
		cases = new Case[getNumber()];
		
		for (int i = 0; i < array.length(); i++){
			cases[i] = (new Case((JSONArray) array.get(i)));
		}	
	}
	
	public int getNumber(){
		return this.numberOfCases;
	}
	
	public Case getCase(int index){
		return cases[index];
	}
	
	public void setCase(int noOfShelves){
		cases[counter] = new Case(noOfShelves);
		counter++;
	}
	
	public JSONArray getJSON(){
		for (int i = 0; i < cases.length; i++){
			JFloor.put(cases[i].getJSON());
		}
		
		return JFloor;
	}

	public ArrayList<Book> getBooks() {
		
		ArrayList<Book> temp = new ArrayList<>();
		
		for (int i = 0; i < cases.length; i++){
			temp.addAll(cases[i].getBooks());
		}
		
		return temp;
	}
	
	
}
