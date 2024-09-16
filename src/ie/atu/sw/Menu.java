package ie.atu.sw;

import java.nio.file.Paths;
import java.util.Scanner;

// Method to display the menu
public class Menu {	
	    //Displays the main menu options
		public static void displayMenu(String outputPath) {             
			System.out.println(ConsoleColour.GREEN_BOLD);                                       // Set console text color to green
			System.out.println("************************************************************");
			System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
			System.out.println("*                                                          *");
			System.out.println("*          Similarity Search with Word Embeddings          *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println(ConsoleColour.PURPLE);                                            // Change text color to purple for the menu options
			System.out.println("(1) Specify embedding file");
			System.out.println("(2) Specify an output file (current: " + Paths.get(outputPath).toAbsolutePath().toString() + ")");
			System.out.println("(3) Enter a word");
			System.out.println("(4) Enter multiple words or a sentence");
			System.out.println("(5) Arithmetic operations with words");		
		    System.out.println("(6) Configure options");
		    System.out.println("(7) Quit");
			System.out.print(ConsoleColour.BLUE);                                                // Set console text color to blue
			System.out.print("Select Option [1-7]>");
			System.out.println(ConsoleColour.RESET);                                             // Reset console color
		}
				
		// Displays options to configure the similarity metric (Cosine, Euclidean, Dot Product)
		public static void configureOptions(Scanner scanner) {                                   			
			System.out.println(ConsoleColour.PURPLE);                                            // Change text color to purple
			System.out.println("Choose similarity metric (default is cosine distance):");        // Display the available similarity metrics
			System.out.println("(1) Cosine distance");
			System.out.println("(2) Euclidean distance");
			System.out.println("(3) Dot product");		
			System.out.print(ConsoleColour.BLUE);                                                // Change text color to blue
			System.out.print("Select Option [1-3]>");
			System.out.println(ConsoleColour.RESET);                                             // Reset console color

			
		}
		
		//Displays the menu options for processing multiple words or sentences
		public static void menuMultiWords() {
			System.out.println(ConsoleColour.PURPLE);                                            // Set the console color to purple
			System.out.println("Choose processing option:");                                     // Display a message asking the user to select an option for processing
			System.out.println("(1) Find the most similar words for the entire input text.");
			System.out.println("(2) Remove stop words and find the most similar words for the entire input text.");
			System.out.println("(3) Find and display the most similar words for each word individually.");
			System.out.print(ConsoleColour.BLUE);                                                // Set the console color to blue
			System.out.print("Select Option [1-3]>");
			System.out.println(ConsoleColour.RESET);                                             // Reset console color
			
		}
		
		//Displays the menu options for arithmetic operations
		public static void menuNorm() {
		System.out.println(ConsoleColour.PURPLE);                                                // Change the console color to purple
	    System.out.println("Do you want to normalize vectors?");
	    System.out.println("(1) Yes");
	    System.out.println("(2) No");
	    System.out.print(ConsoleColour.BLUE);                                                    // Change the console color to blue for option selection
	    System.out.print("Select Option [1-2]: ");
	    System.out.println(ConsoleColour.RESET);                                                 // Reset console color
		}
}
