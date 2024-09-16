package ie.atu.sw;                                                                           // Define the package for this class

import java.io.*;                                                                            // Import classes for input-output operations
import java.nio.file.Paths;                                                                  // Import Paths class
import java.util.Scanner;                                                                    // Import utility classes
 
public class Runner {
	// This method removes the original input words from the similarity results			
	public static void main(String[] args) {                                                 
		String[] words = VecOperations.getWords();                                           // Retrieve the array of words
		String filePath = null;                                                              // Initialize file path
		boolean flag = true;                                                                 // Flag to keep the application running
		Scanner input = new Scanner(System.in);                                              // Initializes a Scanner object to read user input

		try {
			while (flag) {                                                                   // Loop until the user chooses to quit
				String outputPath = FileProcessing.getOutputPath();                          // Retrieve the current output file path from the FileProcessing class
				String embeddingsPath = FileProcessing.getEmbeddingsPath();                  // Retrieve the current embeddings file path from the FileProcessing class
				Menu.displayMenu(outputPath);                                                // Display the menu
				int num = Utils.getUserOption(input, 1, 7);                                  // Get the user's menu option
				
				if ((num == 3 || num == 4 || num == 5) && 
						(filePath == null || words == null)) {                               // Check if the file path or words array is null
	                System.out.println(ConsoleColour.RED);                                   // Set the console color to red
	                System.out.println("Please specify an embedding file first.");           // Display error message
	                System.out.println(ConsoleColour.RESET);                                 // Reset console color
	                continue;
	            }
				
				switch (num) {                                                               // Switch statement to handle different menu options selected by the user
				case 1:
					filePath = FileProcessing.filePath(input, 
							"Specify Embedding File", embeddingsPath);                       // Get the embedding file path
					if (!FileProcessing.checkPrintPath(filePath)) {                          // Check if the file path is valid and readable
						break;
					}
					if (FileProcessing.parse(filePath)) {                                    // If the file is successfully parsed
						System.out.println(ConsoleColour.GREEN);                             // Set the console color to green
						System.out.println("Embeddings loaded successfully.");               // Display message
						System.out.println(ConsoleColour.RESET);                             // Reset console color
						words = VecOperations.getWords();                                    // Retrieve the array of words from the VectorOperations class
					} else {
						System.out.println(ConsoleColour.RED);                               // Set the console color to red
						System.out.println("Failed to load embeddings. "
								+ "Please check the file path and try again.");              // Display error message
						System.out.println(ConsoleColour.RESET);                             // Reset console color
					}
					break;
				case 2:
					outputPath = FileProcessing.filePath(input, 
							"Specify an Output File", outputPath);                           // Get the output file path
					FileProcessing.setOutputPath(outputPath);                                // Set the output file path
					System.out.println(ConsoleColour.GREEN);                                 // Set the console color to green
					System.out.println("Output file set to: " + 
					        Paths.get(outputPath).toAbsolutePath().toString());              // Display the absolute path of the output file
					System.out.println(ConsoleColour.RESET);                                 // Reset console color
					break;
				case 3:					
						Actions.singleWord(input);                                           // Processes a single word input and finds the most similar words					
					break;
				case 4:					
						Actions.multipleWords(input);                                        // Calls the method to process and analyze multiple words or sentences					
					break;
				case 5:  					
						Actions.arithmetic(input);                                           // Calls the method to perform arithmetic operations5					
					break;
				case 6:
					Menu.configureOptions(input);                                            // Display and configure options for the similarity metric					
					int choice = Utils.getUserOption(input, 1, 3);                           // Get the user's choice for similarity metric
					
					switch (choice) {                                                        // Switch case to handle user's choice of similarity metric
					case 2:
						VecOperations.setSimMetric("euclidean");                             // Euclidean distance
						break;
					case 3:
						VecOperations.setSimMetric("dot");                                   // Dot Product						                                           
						break;						
					case 1:
					default:
						VecOperations.setSimMetric("cosine");                                // Cosine Distance						                                       
						break;
					}
					
					System.out.print(ConsoleColour.BLUE);                                    // Set the console color to blue
					String similarityMetric = VecOperations.getSimiMetric();                 // Retrieve the current similarity metric from the VectorOperations class
					System.out.println("Similarity metric set to: " + similarityMetric);     // Display the selected similarity metric
					System.out.println(ConsoleColour.RESET);                                 // Reset console color
					break;
				case 7:
					System.out.println(ConsoleColour.GREEN);                                 // Set the console color to green
					System.out.println("Quitting...");                                       // Display message
					System.out.println(ConsoleColour.RESET);                                 // Reset console color
					flag = false;                                                            // Set the flag to false to exit the loop
					break;
					
				default:
					System.out.println(ConsoleColour.RED);                                   // Set the console color to red
					System.out.println("Invalid option. Please try again.");                 // Display error message
					System.out.println(ConsoleColour.RESET);                                 // Reset console color
				}
			}
		} catch (IOException e) {                                                            // Handle I/O exceptions
	        System.out.println(ConsoleColour.RED);                                           // Set the console color to red
	        System.err.println("An I/O error occurred: " + e.getMessage());                  // Display error message
	        System.out.println(ConsoleColour.RESET);                                         // Reset console color
	        e.printStackTrace();                                                             // Print the stack trace of the exception
		} catch (Exception e) {                                                              // Catch any unexpected exceptions
			System.out.println(ConsoleColour.RED);                                           // Set the console color to red
			System.err.println("An unexpected error occurred: " + e.getMessage());           // Display error message
			System.out.println(ConsoleColour.RESET);                                         // Reset console color
			e.printStackTrace();
		} finally {
			input.close();                                                                   // Close the Scanner                                                         
		}
	}

	
}


