package ie.atu.sw;

import java.util.Scanner;

public class Utils {
	
	    // This method asks the user to input a number between min and max values
		public static int getUserOption(Scanner uInput, int min, int max) {                                   
			while (true) {                                                                     // Infinite loop to continuously check input until input is valid
		        System.out.print("Please enter your option: ");
		        String input = uInput.nextLine().trim();                                       // Read the user's input and remove extra spaces

		        try {
		            int option = Integer.parseInt(input);                                      // Сonvert input to an integer
		            if (option >= min && option <= max) {                                      // Check if the selected option is within the valid range
		                return option;                                                         // If valid, return the input as the selected option
		            } else {
		                System.out.println(ConsoleColour.RED);                                 // Set console color to red
		                System.out.println("Invalid input. Please enter a number between " +
		                min + " and " + max + ".");                                            // Display error message
		                System.out.println(ConsoleColour.RESET);                               // Reset console color
		            }
		        } catch (NumberFormatException e) {
		            System.out.println(ConsoleColour.RED);                                     // Set console color to red
		            System.out.println("Invalid input. Please enter a number between " + 
		            min + " and " + max + ".");                                                // Display error message
		            System.out.println(ConsoleColour.RESET);                                   // Reset console color
		        }
		    }
		}
		
		// This method asks the user for a positive integer
		public static int getInt(Scanner input, String sentence) {                                               
			while (true) {                                                                     // Infinite loop to continuously check input until input is valid
				System.out.print(ConsoleColour.BLUE);                                          // Set console text color to blue
				System.out.print(sentence);                                                    // Print the sentence to the console
				System.out.print(ConsoleColour.RESET);                                         // Reset color
				if (input.hasNextInt()) {                                                      // Check if the input is an integer
					int value = input.nextInt();                                               // Read the integer and stores it in the variable
					input.nextLine();                                                          // Read whole line of text and clears the buffer
					if (value > 0) {                                                           // Check if the value is positive
						return value;                                                          // Return the valid value
					} else {
						System.out.println(ConsoleColour.RED);                                 // Set the console color to red for error messages
						System.out.println("Invalid input. Please enter a positive integer."); // Display error message
						System.out.println(ConsoleColour.RESET);                               // Reset color
					}
				} else {
					System.out.println(ConsoleColour.RED);                                     // Set the console color to red for error messages
					System.out.println("Invalid input. Please enter a valid integer.");        // Display error message
					System.out.println(ConsoleColour.RESET);                                   // Reset color
					input.nextLine();                                                          // Remove invalid input
				}
			}
		}
}
