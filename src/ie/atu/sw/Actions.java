package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Actions {
	// Method that removes the original input words from the results of a similarity check.
	public static void filter(String[] orig, double[] simValues, String[] simWords) {
	    int maxResults = simWords.length;                                                    // Determine the maximum number of results
	    double[] fValues = new double[maxResults];                                           // Create an array to store filtered values
	    String[] fWords = new String[maxResults];                                            // Create an array to store filtered words

	    int index = 0;                                                                       // Initialize index

	    for (int i = 0; i < simWords.length; i++) {                                          // Loop through all similarity words
	    	if (!Arrays.asList(orig).contains(simWords[i])) {                                // Check if simWords[i] is not in the original words list
	            fWords[index] = simWords[i];                                                 // Add simWords[i] to the filtered words array
	            fValues[index] = simValues[i];                                               // Add simValues[i] to the filtered values array
	            index++;                                                                     // Increment the index
	        }
	    }
	    
	    fWords = Arrays.copyOf(fWords, index);                                               // Resize fWords array to match the number of filtered results
	    fValues = Arrays.copyOf(fValues, index);                                             // Resize fValues array to match the number of filtered results
	    
	    System.arraycopy(fWords, 0, simWords, 0, fWords.length);                             // Copy filtered words back to the original simWords array
	    System.arraycopy(fValues, 0, simValues, 0, fValues.length);                          // Copy filtered values back to the original simValues array
	}
	
	// Method to perform arithmetic operations on word vectors
	public static void arithmetic(Scanner text) throws IOException {                         
	    Menu.menuNorm();                                                                     // Display the arithmetic menu
	    int normChoice = Utils.getUserOption(text, 1, 2);                                    // Get user choice for normalization
	    
	    boolean norm;                                                                       
	    if (normChoice == 1) {
	        norm = true;                                                                     // Set normalize to true if the user chose option 1
	    } else {
	        norm = false;                                                                    // Set normalize to false for any other choice
	    }                                            

	    System.out.println(ConsoleColour.BLUE);                                              // Set console color to blue
	    System.out.println("Enter an expression (e.g., 'king - man + woman', 'california - state + country'):");  // Display a message to the user
	    System.out.println(ConsoleColour.RESET);                                             // Reset console color

	    String expr = text.nextLine().toLowerCase().trim();                                  // Read, convert to lowercase, and trim spaces
	    StringBuilder correctedExpr = new StringBuilder();                                   
	    double[] resultVec = VecOperations.calcExpr(expr, norm, correctedExpr);              // Calculate the resulting vector with or without normalization.

	    if (resultVec == null) {                                                             // If calculation fails
	        System.out.println(ConsoleColour.RED);                                           // Set console color to red for error message
	        System.out.println("Failed to calculate the expression. Please check the input."); // Print error message
	        System.out.println(ConsoleColour.RESET);                                         // Reset console color
	        return;                                                                          // Exit method if calculation failed
	    }
	    
	    String correctedExp = correctedExpr.toString().trim();                               // Convert the corrected expression to a string

	    int topN = Utils.getInt(text, "Enter the number of most similar words to find: ");   // Ask user for the number of similar words to find
	    int vecCount = VecOperations.getVecCount();                                          // Get the total number of vectors
	    double[] simValues = new double[vecCount];                                           // Array to store similarity values
	    String[] simWords = new String[vecCount];                                            // Array to store  words
	    
	    System.out.println(ConsoleColour.YELLOW);                                            // Set console color to yellow	    
	    for (int i = 0; i < vecCount; i++) {                                                 // Loop through all vectors to calculate similarity with result vector
	    	double[][] vectors = VecOperations.getVectors();                                 // Get all vectors
	    	String[] words = VecOperations.getWords();                                       // Get all words
	    	simValues[i] = VecOperations.cosineSimilarity(resultVec, vectors[i]);            // Calculate cosine similarity between result vector and current vector
	        simWords[i] = words[i];                                                          // Store the word corresponding to the current vector
	        Output.printProgress(i + 1, vecCount);                                           // Update and display progress
	    }

	    System.out.println(ConsoleColour.RESET);                                             // Reset console color to default

	   
	    VecOperations.quickSort(simValues, simWords, 0, vecCount - 1, false);                // Sort the similarity values and corresponding words

	    String[] correctedW = correctedExp.split("\\s+");                                    // Split the corrected expression into an array
	    
	    filter(correctedW, simValues, simWords);                                             // Exclude the original words from the similarity results
	    
	    String outputPath = FileProcessing.getOutputPath();                                  // Get the path of the output file
	    BufferedWriter bWriter = new BufferedWriter(new FileWriter(outputPath, true));       // Open the output file in append mode with buffering
	    String operationType;
	    if (norm) {                                                                          // Check if normalization was used
	        operationType = "arithmetic operation with normalization";
	    } else {
	        operationType = "arithmetic operation without normalization";
	    }

	    Output.outputResults(bWriter, correctedExp, simWords, simValues, topN, operationType); // Write the results to the file and display them

	    System.out.println(ConsoleColour.BLUE);                                              // Set console color to blue
	    System.out.println("Results written to " + outputPath);                              // Inform the user that results were saved
	    System.out.println(ConsoleColour.RESET);                                             // Reset console color

	    bWriter.flush();                                                                     // Flush the writer to ensure all data is written
	    bWriter.close();                                                                     // Close the writer to finalize the output file
	}
	
	    // Processing one word
		public static void singleWord(Scanner text) throws IOException {                     
			System.out.println(ConsoleColour.BLUE);                                          // Set the console color to blue
			System.out.println("Enter a word:");                                             // Ask the user to input a word
			System.out.println(ConsoleColour.RESET);                                         // Reset color to default
			
			String input = text.nextLine().replaceAll("[^a-zA-Z ]", "").toLowerCase().trim(); // Read user's input and remove any leading and trailing spaces
			
			if (input.isEmpty()) {                                                           // Check if the input is empty
				System.out.println(ConsoleColour.RED);                                       // Set the console color to red
				System.out.println("Invalid input. Please enter a valid word.");             // Print error message
				System.out.println(ConsoleColour.RESET);                                     // Reset color
				return;                                                                      // Exit the method because input is not valid
			}
			
			String[] words = input.split("\\s+");                                            // Split the input into words 
		    
		    if (words.length != 1) {                                                         // Check if more than one word was entered
		        System.out.println(ConsoleColour.RED);                                       // Set the console color to red
		        System.out.println("Invalid input. Please enter only one word.");            // Print error message
		        System.out.println(ConsoleColour.RESET);                                     // Reset console color
		        return;                                                                      // Exit the method if the input is not a single word
		    }
		    
		    String word = words[0];                                                          // Get the single word from the array

		    
		    if (!word.matches("[a-zA-Z]+")) {                                                // Check if the word contains only alphabetic characters
		        System.out.println(ConsoleColour.RED);                                       // Set the console color to red
		        System.out.println("Invalid input. The word should contain only alphabetic characters.");  // Print error message
		        System.out.println(ConsoleColour.RESET);                                     // Reset console color
		        return;                                                                      // Exit the method because the word contains invalid characters
		    }
		    
		    Menu.menuNorm();                                                                 // Display the arithmetic menu
		    int normChoice = Utils.getUserOption(text, 1, 2);                                // Get user choice for normalization
		    boolean norm;                                                                       
		    if (normChoice == 1) {
		        norm = true;                                                                 // Set normalize to true if the user chose option 1
		    } else {
		        norm = false;                                                                // Set normalize to false for any other choice
		    }  
		    
			int topN = Utils.getInt(text, "Enter the number of most similar words to find: "); // Ask user for the number of similar words to find
			TextManager.similarWords(word, topN, norm);                                      // Find the most similar words
		}

		// Processing multiple words
		public static void multipleWords(Scanner words) throws IOException {                 
			System.out.println(ConsoleColour.BLUE);                                          // Set the console color to blue
			System.out.println("Enter a sentence or multiple words:");                       // Ask user to input a sentence or multiple words
			System.out.println(ConsoleColour.RESET);                                         // Reset console color

			String text = words.nextLine().replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();  // Read user input and remove leading and trailing spaces		
			
		    if (text.split("\\s+").length < 2) {                                             // Check if the input has less than two words
		        System.out.println(ConsoleColour.RED);                                       // Set the console color to red
		        System.out.println("Invalid input. Please enter at least two words.");       // Print error message
		        System.out.println(ConsoleColour.RESET);                                     // Reset console color
		        return;                                                                      // Exit the method if input is invalid
		    }
		    
		    String[] wordArr = text.split("\\s+");                                           // Split the input text into an array of words
		    for (String word : wordArr) {                                                    // Loop through each word in the array
		        if (!word.matches("[a-zA-Z]+")) {                                            // Check if the word contains only alphabetic characters
		            System.out.println(ConsoleColour.RED);                                   // Set the console color to red
		            System.out.println("Invalid input. All words must consist only of alphabetic characters.");  // Print error message
		            System.out.println(ConsoleColour.RESET);                                 // Reset console color
		            return;                                                                  // Exit the method if any word is invalid
		        }
		    }
		    
		    		    
			Menu.menuMultiWords();                                                           // Display the menu

			int subOption = Utils.getUserOption(words, 1, 3);                                // Get the user's choice for processing option

			Menu.menuNorm();                                                                 // Display the arithmetic menu
		    int normChoice = Utils.getUserOption(words, 1, 2);                               // Get user choice for normalization
		    
		    boolean norm;                                                                       
		    if (normChoice == 1) {
		        norm = true;                                                                 // Set normalize to true if the user chose option 1
		    } else {
		        norm = false;                                                                // Set normalize to false for any other choice
		    }  
			
			switch (subOption) {                                                             // Perform an action based on the user's choice
			case 1:                                                                          // If the user chose option 1				
				int topN1 = Utils.getInt(words, "Enter the number of most similar words to find: "); // Ask how many similar words to find
				TextManager.similarWords(text, topN1, norm);                                 // Find the most similar words for the entire input text
				break;
			case 2:                                                                          // If the user chose option 2			
				int topN3 = Utils.getInt(words, "Enter the number of most similar words to find: "); // Ask how many similar words to find
				TextManager.similarWithStopWordsRmv(text, topN3, norm);                      // Find the most similar words after removing stop words
				break;				
			case 3:                                                                          // If the user chose option 3
				int topN2 = Utils.getInt(words, "Enter the number of most similar words to find for each word: ");  // Ask how many similar words to find per word
				TextManager.similarEachWord(text, topN2, norm);                              // Find the most similar words for each word individually
				break;				
			default:
				System.out.println(ConsoleColour.RED);                                       // Set the console color to red
				System.out.println("Invalid option. Please try again.");                     // Print error message
		        System.out.println(ConsoleColour.RESET);                                     // Reset color				
				break;
			}
		}
}
