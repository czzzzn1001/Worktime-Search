package main;
/**
 * Explanation: Implementation of InputFileReader
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This file holds a utility function getWorkEntriesFromCSV() that is used by REPL, tests to create an array of WorkEntry objects.
 * <p><b>You should not edit this file.</b></p> 
 * 
 * @author cs21a
 * @version 1.0
 */
public class InputFileReader {
	
	/**
	 * CSV files will be delimited with commas 
	 */
	public static final String INPUT_LINE_DELIMETER = ",";

	/**
	 * Gets the Work Entry objects from a CSV file using the helper method createWorkEntry(). 
	 * @param csvPath path to the CSV file 
	 * @return array of WorkEntry objects that were parsed from the file or NULL if an error occurred 
	 * @see #createWorkEntry(String)
	 */
	public static WorkEntry[] getWorkEntriesFromCSV(String csvPath) {
		List<WorkEntry> workEntries = new LinkedList<WorkEntry>();
		Scanner fileRdr = null;
		try {
			// loop over input file and use createWorkEntry() to parse WorkEntry objects into workEntries
			// on windows, data files seem to be stored with UTF-8 char encoding
			fileRdr = new Scanner(new File(csvPath), "UTF-8");
			while (fileRdr.hasNextLine()) {
				workEntries.add(createWorkEntry(fileRdr.nextLine()));
			}
		}
		// thrown if Scanner cannot be created properly 
		catch (FileNotFoundException e) {
			System.err.println("error with " + csvPath + ": " + e.getMessage());
			return null;
		}
		// thrown by Date constructor and if createWorkEntry() doesn't have enough comma separated values to parse from 
		catch (IllegalArgumentException e) {
			System.err.println("Input file has error on line #: " + (workEntries.size()+1) + "\nerror: " + e.getMessage());
			return null;
		}
		// thrown if time spent value cannot be correctly parsed i.e. something of the form "1.0 h" can't be found 
		catch (StringIndexOutOfBoundsException e) {
			System.err.println("Input file has invalid time spent on line #" + (workEntries.size()+1) + "\nerror: not matching format of \"<number> h\".");
			return null;
		}
		// thrown if time spent value is not a double 
		catch (InputMismatchException e) {
			System.err.println("Input file has invalid time spent on line #" + (workEntries.size()+1) + "\nerror: worktime is not a valid floating point number.");
			return null;
		} 
		finally {
			if (fileRdr != null) {
				fileRdr.close();
			}
		}
		WorkEntry[] arr = new WorkEntry[workEntries.size()];
		return workEntries.toArray(arr); // get array from LL 
	}

	/**
	 * Parse a WorkEntry object from an input line 
	 * @param line a comma separated value 
	 * @return a WorkEntry object 
	 */
	private static WorkEntry createWorkEntry(String line) {
		String[] arr = line.split(INPUT_LINE_DELIMETER); // split on "," 
		if (arr.length != 3 && arr.length != 4) { // throw exception if not enough values to parse 
			throw new IllegalArgumentException("Not the correct number of comma separated values (accepted values: 3, 4), received: " + arr.length);
		}
		for (int i = 0; i < arr.length; i++) { // if any of delimited values have spaces on the ends => remove them (now "grading " is the same as "grading")
			arr[i] = arr[i].trim();
		}
		WorkEntry entry = null;

		String desc = ""; // check to see if there's a description (CSV line could have only 3 values) 
		if (arr.length == 4) {
			desc = arr[3];
		}
		
		// will throw exception if Double.parseDouble() encounters a non-double value or if indexOf(" ") returns an invalid index causing substring to throw OOB
		entry = new WorkEntry(arr[0], Double.parseDouble(arr[1].substring(0, arr[1].indexOf(" "))), arr[2], desc);

		return entry;
	}
	
}
