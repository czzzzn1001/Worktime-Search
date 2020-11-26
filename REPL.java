package main;
/**
 * Explanation: Implementation of REPL
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
import java.util.Scanner;

/**
 * This file runs the Work Time Analysis program by reading the input data from
 * a CSV file, loading into a Work Time Analysis tool, and then using the tool
 * to handle user input commands to list, search, and delete.
 * <p>
 * <b>You should not edit this file.</b>
 * </p>
 * 
 * @author cs21a
 * @version 2.1
 */
public class REPL {

	/**
	 * CSV file name prompt
	 */
	public static final String CSV_PROMPT = "Enter the CSV file path:";

	/**
	 * The command "line" will have a prompt of "> "
	 */
	public static final String CMD_PROMPT = ">";

	/**
	 * The command line will be stopped by the user typing "q"
	 */
	public static final String SENTINEL = "q";

	/**
	 * Error that will be printed if the user tries to delete when there are no entries to index 
	 */
	public static final String DEL_STATE_ERR = "[del] command has no entries to index";

	/**
	 * Error that will be printed if the user tries to delete an invalid index from
	 * a previously searched node
	 */
	public static final String DEL_INDEX_ERR = "[del] command received an invalid index";

	/**
	 * Main method that runs the program
	 * 
	 * @param args program arguments (not used)
	 */
	public static void main(String[] args) {

		Scanner consoleRdr = new Scanner(System.in);
		// start program by prompting for CSV input
		System.out.print(CSV_PROMPT + " ");
		String input = consoleRdr.nextLine();
		System.out.println();

		// load entries from CSV file
		WorkEntry[] entries = InputFileReader.getWorkEntriesFromCSV(input);

		// if entry loading successful, set up tool using these entries
		if (entries != null) {
			WorkTimeAnalysisTool tool = new WorkTimeAnalysisTool(entries);
			boolean running = true;
			// while the user hasn't quit, prompt for inputs and let tool parse the input
			// command
			do {
				System.out.print(CMD_PROMPT + " ");
				// convert to lowercase for convenience (is reasonable - logged activities
				// shouldn't differ by case)
				input = consoleRdr.nextLine().trim().toLowerCase();
				if (input.equals(SENTINEL)) {
					running = false;
				} else {
					try { // have tool try to parse command. if it throws an exception, REPL handles and
							// prints some err msg
						String out = tool.parse(input);
						if (out != null && !out.isEmpty()) {
							System.out.println(out);
						}
					}
					// print invalid index error if tool throws illegal argument exception
					catch (IndexOutOfBoundsException e) {
						System.out.println(DEL_INDEX_ERR);
					}
					// print invalid delete usage if tool throws illegal state exception
					catch (IllegalStateException e) {
						System.out.println(DEL_STATE_ERR);
					}
				}
			} while (running);
		}

		consoleRdr.close();
	}

}
