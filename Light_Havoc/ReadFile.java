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
		String[] variable_name = currLine.split(","); // returns the array after splitting the lines String
		return new double[] {Double.parseDouble(variable_name[0]), Double.parseDouble(variable_name[1])};
	}
	
	// public static void main(String[] args){
	// 	// for (int i = 0; i < 200; i++) {
	// 	while(true){
			// try {
			// 	System.out.println(Arrays.toString(new ReadFile("tmp.tmp").getArray()));
			// } catch (Exception e) {}
	// 	}
	// 	// }
	// }
}