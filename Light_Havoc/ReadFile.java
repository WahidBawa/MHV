import java.io.*;
import java.util.*;

public class ReadFile{ // this class will be used to read in a file
	private String lines = "";
	private String currLine;
	public ReadFile(String f) throws FileNotFoundException{ //this is the constructor
		File file = new File(f); // this will open a file using the passed in directory
        Scanner text = new Scanner(file); // creates a scanner object which will be used to read the lines of the file
		currLine = text.nextLine();
	}

	public double[] getArray(){ // this method will be used to get the file information as a String array
		String[] s;
		s = currLine.split(" "); // returns the array after splitting the lines String
		String[] variable_name = s[0].split(","); // returns the array after splitting the lines String
		System.out.println(Arrays.toString(s));
		return new double[] {Double.parseDouble(variable_name[0]), Double.parseDouble(variable_name[1]), Double.parseDouble(s[1]), Double.parseDouble(s[2])};
	}
	
}