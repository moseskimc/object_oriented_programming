package edu.brandeis.cs12b.pa9;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;

public final class LibraryFactory {


	/**
	 * Construct a new library object based on the given parameters
	 * @param floors the number of floors
	 * @param casesPerFloor the number of cases per floor
	 * @param shelvesPerCase the number of shelves per case on each floor
	 * @param shelfCapacity the capacity of each shelf
	 * @return a library object
	 */
	public static Library makeLibrary(int floors, int[] casesPerFloor, int[][] shelvesPerCase, int[][][] shelfCapacity) {
		
		Library temp = new MyLibrary(floors,casesPerFloor,shelvesPerCase,shelfCapacity);
		
		return temp;
	}
	
	/**
	 * Makes a new library from the passed file. The file will have been produced by your
	 * Library's writeToFile method.
	 * 
	 * @param f the file to read from
	 * @return the reconstructed library
	 * @throws IOException 
	 */
	public static Library makeLibraryFromFile(File f)  {
		
		InputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		
		
		int br = 0;
		try {
			br = is.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (br != -1 ){
			bas.write(br);		
			try {
				br = is.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		JSONArray library = new JSONArray(bas.toString());
		
		MyLibrary l = new MyLibrary();
		
		l.makeFloors(library);
		l.addBooks();
		
		
		try {
			bas.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return l;
	}
	
}
