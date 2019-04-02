package edu.brandeis.cs12b.pa9;

public class BookLocation implements Comparable<BookLocation> {
	private int floor;
	private int shelf;
	private int cas;

	
	public BookLocation(int floor, int cas, int shelf) {
		this.floor = floor;
		this.shelf = shelf;
		this.cas = cas;
	}
	
	public BookLocation() {
		
	}
	
	
	public int getFloor() {
		return floor;
	}
	public int getShelf() {
		return shelf;
	}
	public int getCas() {
		return cas;
	}


	public void setFloor(int floor) {
		this.floor = floor;
	}


	public void setShelf(int shelf) {
		this.shelf = shelf;
	}


	public void setCas(int cas) {
		this.cas = cas;
	}

	@Override
	public int compareTo(BookLocation o) {
		// TODO Auto-generated method stub
		int value = 0;
		if (this.floor < o.getFloor()) value = -1;
		
		else{
			if (this.floor > o.getFloor()) value = 1;
			else{
				if (this.cas < o.getCas()) value = -1;
				
				else{
					if (this.cas > o.getCas()) value = 1;
					else{
						if (this.shelf < o.getShelf()) value = -1;
						
						else{
							if (this.shelf > o.getShelf()) value = 1;
							else{
								value = 0;
							}
						}
					}
				}
			}
		}
		
		
		return value;
	}
	
	@Override
	public boolean equals(Object o){
		BookLocation temp = (BookLocation) o;
		
		if (compareTo(temp)==0){
			return true;
		}
		
		return false;
	}
	
	
	
}
