package edu.brandeis.cs12b.pa9;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Book implements Comparable<Book> {
	private String title;
	private BookLocation location;
	private boolean isChecked = false;
	
	public Book(String title){
		this.title = title;
	}
	
	public Book(JSONObject o){
		this.title = o.getString("title");
		
		String temp = o.get("location").toString();
		temp.charAt(0);
		String i = String.valueOf(temp.charAt(2));
		String j = String.valueOf(temp.charAt(6));
		String k = String.valueOf(temp.charAt(10));
		
		setLocation(Integer.parseInt(i),Integer.parseInt(j),Integer.parseInt(k));
		
		
	}
	
	public Book(String title, int i , int j, int k){
		this.title = title;
		setLocation(i,j,k);
	}
	
	
	
	
	public String getTitle(){
		return title;
	}
	
	public void setLocation(int i, int j, int k){
		this.location = new BookLocation(i,j,k);
	}
	
	
	public BookLocation getLocation(){
		return location;
	}
	
	
	public void checkOut(){
		isChecked = true;
	}
	
	public void checkIn(){
		isChecked = false;
	}
	
	public boolean isAvailable(){
		return !isChecked;
	}

	@Override
	public int compareTo(Book o) {
		
		return getLocation().compareTo(o.getLocation());
	
	}

	public JSONObject getJSON() {
		
		JSONObject jBook = new JSONObject();
		List<String> coord = new ArrayList<>();
		
		coord.add(String.valueOf(location.getFloor()));
		coord.add(String.valueOf(location.getCas()));
		coord.add(String.valueOf(location.getShelf()));
		
		jBook.put("location", coord);
		jBook.put("title", title);
		
		
		
		
		return jBook;
	}
	
	
	
	
}
