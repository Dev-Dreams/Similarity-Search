package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.IOException;

public class Output {
	// Method to save results to file and print to console
	public static void outputResults(BufferedWriter bfWriter, String text, String[] simWords, 
			double[] simValue, int topN, String metric) throws IOException {                 
	    
	    System.out.println(ConsoleColour.WHITE);                                             // Set text color to white	    
	 
	    String metricDisp;                                                                   // Declare a variable
	    switch (metric.toLowerCase()) {                                                      // Convert metric name to lowercase and choose the display name
	        case "euclidean":
	            metricDisp = "Euclidean distance";                                           // Set name for Euclidean distance
	            break;
	        case "cosine":
	            metricDisp = "Cosine distance";                                              // Set name for Cosine distance
	            break;
	        case "dot":
	            metricDisp = "Dot product";                                                  // Set name for Dot product
	            break;
	        default:
	            metricDisp = metric;                                                         // If the metric is not recognized, keep the original name
	    }
	    
	    String output = "Most similar words to: \"" + text + "\" using " + metricDisp + ":\n";   // Output string
	    bfWriter.write(output);                                                              // Write the header to the file
	    System.out.print(output);                                                            // Print the header to the console

	    int vecCount = VecOperations.getVecCount();                                          // Get the total number of vectors
	    int count = 0;                                                                       // Initialize a counter 
	    for (int i = 0; i < vecCount && count < topN; i++) {                                 // Loop through the vectors
	        if (simWords[i] != null) {                                                       // Check if the similar word is not null
	            String result = simWords[i] + ": " + simValue[i] + "\n";                     // Create a string for the result
	            bfWriter.write(result);                                                      // Write each result to file
	            System.out.print(result);                                                    // Print each result to console
	            count++;                                                                     // Increment results counter
	        }
	    }
	    bfWriter.write("\n");                                                                // Add a new line to the file
	                                                                   
	    System.out.println(ConsoleColour.RESET);                                             // Reset console color
	}
	
	// Method to print progress to the console
	public static void printProgress(int index, int total) {                                 
		if (index > total)                                                                   // Check if current progress is more than total
			return;                                                                          // If true, stop the method
		int size = 50;                                                                       // Set the length of the progress bar to 50 characters
		char done = '█';                                                                     // Character to show completed part of the progress
		char todo = '░';                                                                     // Character to show remaining part of the progress

		int complete = (100 * index) / total;                                                // Calculate the percentage complete
		int completeLen = size * complete / 100;                                             // Calculate the length of the completed part

		StringBuilder sb = new StringBuilder();                                              
		sb.append("[");
		for (int i = 0; i < size; i++) {                                                     // Build the progress bar
			sb.append((i < completeLen) ? done : todo);
		}

		System.out.print("\r" + sb + "] " + complete + "%");                                 // Print the progress bar

		if (index == total)                                                                  // If progress is complete
			System.out.println("\n");                                                        // Move to a new line when complete
	}
}
