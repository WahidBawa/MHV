import java.io.*;
import java.util.*;

public class ReadFile{ // this class will be used to read in a file
	private String lines = "";
	public ReadFile(String f) throws FileNotFoundException{ //this is the constructor
		File file = new File(f); // this will open a file using the passed in directory
        Scanner text = new Scanner(file); // creates a scanner object which will be used to read the lines of the file
		String currLine; // this is the current line
        while (text.hasNextLine()) { // while there is a line to be read in the file
            currLine = text.nextLine();
            // the if statement is to get rid of long spaces in ascii art
            if (!currLine.equals("                                                                                                                ")){
	            lines += currLine + "\n"; // this will store the lines in a String seperated by \n
            }
        }
	}

	public String[] getArray(){ // this method will be used to get the file information as a String array
		return lines.split("\n"); // returns the array after splitting the lines String
	}
	
	// public static void main(String[] args){
	// 	// for (int i = 0; i < 200; i++) {
	// 	while(true){
	// 		try {
	// 			System.out.println(Arrays.toString(new ReadFile("tmp.tmp").getArray()));
	// 		} catch (Exception e) {}
	// 	}
	// 	// }
	// }
}