package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileProcessing {
	private static String outputPath = "out.txt";                                           // Default output file path
	private static String embeddingsPath = "";                                              // Default embeddings file path
	
	// Method to get the current value of the output file path
	public static String getOutputPath() {
	    return outputPath;                                                                  // Return the value of the outputPath variable
	}

	// Method to set a new value for the output file path.
	public static void setOutputPath(String path) {
	    outputPath = path;                                                                  // Assign the provided path to the outputPath variable
	}

	// Method to get the current value of the embeddings file path
	public static String getEmbeddingsPath() {
	    return embeddingsPath;                                                              // Return the value of the embeddingsPath variable
	}

	// Method to set a new value for the embeddings file path
	public static void setEmbeddingsPath(String path) {
	    embeddingsPath = path;                                                              // Assign the provided path to the embeddingsPath variable
	}

	// Method to check if a given file path is valid
	public static boolean isValidFilePath(String filePath) {                              
	    return Files.exists(Paths.get(filePath));                                           // Check if the file exists at the given path and return the result
	}
	
	// Method to get a file path
	public static String filePath(Scanner text, String msg, String defPath) {        
		System.out.print(ConsoleColour.BLUE);                                               // Set console text color to blue
		System.out.println(msg + ": ");                                                     // Display the msg
		System.out.println(ConsoleColour.RESET);                                            // Reset color
		String fPath = text.nextLine().trim();                                              // Read the input and remove spaces
		while (fPath.isEmpty()) {                                                           // Loop if the input is empty
	        System.out.println(ConsoleColour.RED);                                          // Change console color to red
	        System.out.println("Please enter a valid file path:");                          // Ask the user to provide a valid file path
	        System.out.println(ConsoleColour.RESET);                                        // Reset the console color
	        fPath = text.nextLine().trim();                                                 // Read and trim the input
	    }
		return fPath;                                                                       // Return the valid file path
	}
	
    // Method checks if file path is valid
	public static boolean checkPrintPath(String filePath) {                                 
        try {
            Path path = Paths.get(filePath);                                                // This line creates a Path object that represents the file path
            
            if (!Files.exists(path)) {                                                      // Check if the file exists
                System.out.println(ConsoleColour.RED);                                      // Set the console color to red for error messages
                System.out.println("File not found at: " + path.toAbsolutePath());          // Display the absolute path where the file was expected
                System.out.println(ConsoleColour.RESET);                                    // Reset the console color
                return false;                                                               // Return false as the file does not exist
            }

           
            if (!Files.isReadable(path)) {                                                  // Check if the file is readable
                System.out.println(ConsoleColour.RED);                                      // Set the console color to red for error messages
                System.out.println("File is not accessible for reading at: " + path.toAbsolutePath());  // Display error message if file is not readable
                System.out.println(ConsoleColour.RESET);                                    // Reset the console color
                return false;                                                               // Return false as the file is not accessible
            }
            return true;                                                                    // Return true if the file exists and is readable
        } catch (InvalidPathException e) {                                                  // Catch exception if the path syntax is incorrect
            System.out.println(ConsoleColour.RED);                                          // Set the console color to red for error messages
            System.out.println("Provided file path is invalid: " + filePath);               // Display the error message if the file path is invalid
            System.out.println(ConsoleColour.RESET);                                        // Reset the console color
            return false;                                                                   
        }
    }
    
	// Method to validate content of a line
	public static boolean validateLine(String line, int lineNumber) {                        
        
	    if (line == null || line.trim().isEmpty()) {                                         // Check if the line is null or empty after trimming whitespace
	        System.out.println(ConsoleColour.RED);                                           // Set console text color to red
	        System.out.println("Line " + lineNumber + " is empty or null.");                 // Display error message that the line is empty or null
	        System.out.println(ConsoleColour.RESET);                                         // Reset console text color
	        return false;
	    }
	    
	    String[] parts = line.trim().split("\\s+");                                         // Split the line by commas, ignoring spaces after commas	    
	    
	    if (parts.length != VecOperations.getFeaturesCount() + 1) {                          // Check if the number of elements in the line is correct
	        System.out.println(ConsoleColour.RED);                                           // Set console text color to red for error messages
	        System.out.println("Line " + lineNumber + 
	        		" contains an incorrect number of elements: " + parts.length);           // Display error message about the incorrect number of elements
	        System.out.println(ConsoleColour.RESET);                                         // Reset console text color
	        return false;
	    }

	    
	    if (parts[0].trim().isEmpty()) {                                            // Check if the first element is a valid word (only alphabetic characters)
	        System.out.println(ConsoleColour.RED);                                           // Set console text color to red for error messages
	        System.out.println("The first element in line " + lineNumber + 
	        		" is not a word: " + parts[0]);                                          // Display error message that the first element is not a word
	        System.out.println(ConsoleColour.RESET);                                         // Reset console text color
	        return false;
	    }

	    
	    for (int i = 1; i < parts.length; i++) {                                             // Verify that the remaining elements are valid numbers
	        try {
	            Double.parseDouble(parts[i]);                                                // Attempt to parse each element as a double number
	        } catch (NumberFormatException e) {                                              // Handle exception if parsing fails
	            System.out.println(ConsoleColour.RED);                                       // Set console text color to red for error messages
	            System.out.println("Line " + lineNumber + 
	            	" contains an invalid number at position " + (i + 1) + ": " + parts[i]); // Display error message about the invalid number
	            System.out.println(ConsoleColour.RESET);                                     // Reset console text color
	            return false;                                                               
	        }
	    }

	    return true;                                                                         
	}
	
	// Method to parse the data from the embeddings file
	public static boolean parse(String path) {                                               
		FileReader fReader = null;                                                           // Declare FileReader object to read from the file
	    BufferedReader reader = null;                                                        // Declare BufferedReader object to buffer the file reading
	    int lCount = 0;                                                                      // Initialize counter for total lines
	    int valLine = 0;                                                                     // Initialize counter for valid lines
		try {
			fReader = new FileReader(path);                                                  // Сreate a new FileReader object to read the file
	        reader = new BufferedReader(fReader);                                            // Сreate a BufferedReader object to read text from the FileReader
			String line = null;                                                              // Initialize a variable to store each line read from the file
			while ((line = reader.readLine()) != null) {                                     // Read lines until the end of the file
				lCount++;                                                                    // Increment total lines counter
				if (!validateLine(line, valLine)) {                                          // Check if the line format is correct
					System.out.println("Incorrect data format in line: " + line);            // Print error message if line is invalid
					continue;                                                                // Skip invalid lines
				} else
					valLine++;                                                               // Increment valid line counter 
			} 
			reader.close();                                                                  // Close the reader
			
			double[][] vectors = VecOperations.getVectors();                                 // Retrieve existing vectors array			           
	    	String[] words = VecOperations.getWords();                                       // Retrieve existing words array

			words = new String[valLine];                                                     // Initialize words array
			vectors = new double[valLine][VecOperations.getFeaturesCount()];                 // Initialize vectors array
 
			fReader = new FileReader(path);                                                  // Create a new FileReader object to read the file
	        reader = new BufferedReader(fReader);                                            // Create a BufferedReader object to read text from the FileReader
			int index = 0;                                                                   // Initialize index
			int progress = 0;                                                                // Initialize progress
			while ((line = reader.readLine()) != null && index < valLine) {                  // Loop reads each line from the BufferedReader
				String[] parts = line.split("\\s+");                                            // Split the line by commas
				words[index] = parts[0].trim();                                              // Get the word
				for (int i = 1; i <= VecOperations.getFeaturesCount(); i++) {                // Loop through the vector values
					vectors[index][i - 1] = Double.parseDouble(parts[i].trim());             // Parse and store each vector value
				}
				index++;                                                                     // Increment index
				progress++;                                                                  // Increment progress
				System.out.print(ConsoleColour.YELLOW);                                      // Set console output color to yellow
				Output.printProgress(progress, lCount);                                      // Print progress bar
				System.out.print(ConsoleColour.RESET);                                       // Reset color
			}
			
			reader.close();                                                                  // Close the reader
	        
	        VecOperations.setWords(words);                                                   // Set the words array in VectorOperations
	        VecOperations.setVectors(vectors);                                               // Set the vectors array in VectorOperations
	        VecOperations.setVecCount(words.length);                                         // Set the vector count in VectorOperations
	        
		} catch (FileNotFoundException e) {                                                  // Catch block for file not found errors
			System.out.println(ConsoleColour.RED);                                           // Set the console color to red
			System.err.println("Error: file not found. Please check the file path and try again."); // Print error message
			System.out.println(ConsoleColour.RESET);                                         // Reset console color
			return false;                                                                    // Return false if file not found
		} catch (IOException e) {                                                            // Catch block for i/o errors
			System.out.println(ConsoleColour.RED);                                           // Set the console color to red
			System.err.println("Error reading file: " + e.getMessage());                     // Print error message for IO exceptions
			System.out.println(ConsoleColour.RESET);                                         // Reset color
			return false;                                                                    // Return false if there is an IO error
		} catch (NumberFormatException e) {                                                  // Catch block for number format errors
			System.out.println(ConsoleColour.RED);                                           // Set the console color to red
			System.err.println("Error: file contains invalid number format.");               // Print error message
			System.out.println(ConsoleColour.RESET);                                         // Reset console color
			return false;                                                                    // Return false if there is a number format error
		} finally {
			try {
				if (reader != null) {                                                        // Check if reader is not null
					reader.close();                                                          // Close the reader
				}
			} catch (IOException e) {                                                        // Catch block for errors while closing the reader
				System.out.println(ConsoleColour.RED);                                       // Set the console color to red
				System.err.println("Error closing file reader: " + e.getMessage());          // Print error message
				System.out.println(ConsoleColour.RESET);                                     // Reset color
			}
			try {
	            if (fReader != null) {                                                       // Check if FileReader is not null
	                fReader.close();                                                         // Close the FileReader
	            }
	        } catch (IOException e) {                                                        // Catch block for errors while closing FileReader
	            System.out.println(ConsoleColour.RED);                                       // Set the console color to red
	            System.err.println("Error closing FileReader: " + e.getMessage());           // Print error message
	            System.out.println(ConsoleColour.RESET);                                     // Reset color
	        }
		}
		
   
    	return true;                                                                         // Return true if parsing is successful
	} 
	
	
}
