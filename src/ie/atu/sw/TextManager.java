package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextManager {
	
	    // Array of stop words 
	    // Source LTK Stopwords - https://aparnamishra144.medium.com/removing-stopwords-with-nltk-bcebf1c3cf62
		private static final String[] STOP_WORDS_ARR = { "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you",
				"you're", "you've", "you'll", "you'd", "your", "yours", "yourself", "yourselves", "he", "him", "his",
				"himself", "she", "she's", "her", "hers", "herself", "it", "it's", "its", "itself", "they", "them", "their",
				"theirs", "themselves", "what", "which", "who", "whom", "this", "that", "that'll", "these", "those", "am",
				"is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did",
				"doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by",
				"for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above",
				"below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then",
				"once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most",
				"other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t",
				"can", "will", "just", "don", "don't", "should", "should've", "now", "d", "ll", "m", "o", "re", "ve", "y",
				"ain", "aren", "aren't", "couldn", "couldn't", "didn", "didn't", "doesn", "doesn't", "hadn", "hadn't",
				"hasn", "hasn't", "haven", "haven't", "isn", "isn't", "ma", "mightn", "mightn't", "mustn", "mustn't",
				"needn", "needn't", "shan", "shan't", "shouldn", "shouldn't", "wasn", "wasn't", "weren", "weren't", "won",
				"won't", "wouldn", "wouldn't" };
		
		private static final String[] SUFFIXES = { "ing", "ed", "es", "s" };                     // Array of common suffixes
		
        // Method to reduce a word to its base form
		// Source https://www.techiedelight.com/how-to-remove-a-suffix-from-a-string-in-java/
		// The code has been modified to iterate over the SUFFIXES array.
		public static String stem(String word) {                                     
			for (String suffix : SUFFIXES) {                                                     // Loop through each suffix in the SUFFIXES array
				if (word.endsWith(suffix) && word.length() > suffix.length() + 1) {              // Check if the word ends with the current suffix and is longer than the suffix length + 1 char
					return word.substring(0, word.length() - suffix.length());                   // Return the word with the suffix removed
				}
			}
			return word;                                                                         // If no suffix is found, return the original word                                                       
		}
		
	    // Method to preprocess text by removing non-alphabet characters, converting to lowercase, and stemming
		public static String textProcessing(String text) {		    
		   
			String[] words = text.split("\\s+");
			StringBuilder result = new StringBuilder();                                          
		    
		    for (String word : words) {
	            String stemmedWord = stem(word);                                                 // Stem each word
	            result.append(stemmedWord).append(" ");                                          // Add the stemmed word to the result
	        }
	    
		    return result.toString().trim();                                                     // Return the final stemmed text after trimming the last space
		}
				
		// Method to check if a word is a stop word
		public static boolean stopWord(String word) {                                            
			for (String stopWord : STOP_WORDS_ARR) {                                             // Loop through the stop words array
				if (stopWord.equals(word)) {                                                     // If the word matches any stop word
					return true;                                                                 // Return true
				}
			}
			return false;                                                                        // Return false if the word is not a stop word
		}
		
		// Method to remove stop words from a text
		public static String rmvStopWords(String text) {                                         
			StringBuilder newText = new StringBuilder();                                         
			for (String word : text.split("\\s+")) {                                             // Split the text by spaces
				if (!stopWord(word)) {                                                           // If the word is not a stop word
					newText.append(word).append(" ");                                            // Add the word to the cleaned text
				}
			}
			return newText.toString().trim();                                                    // Return the cleaned text after removing extra spaces			
		}
		
	    // Method that finds and writes the most similar words for each word in the given text		
		public static void similarEachWord(String text, int topN, boolean norm) throws IOException {  		
				  	    
			BufferedWriter bWriter = null;                                                       // Initialize BufferedWriter to null
			try {
				String[] words = text.split("\\s+");                                             // Split text into individual words

				String outputPath = FileProcessing.getOutputPath();                              // Get the output file path 
			    bWriter = new BufferedWriter(new FileWriter(outputPath, true));                  // Open the file in append mode
				for (String word : words) {                                                      // Loop through each word
					if (!word.isEmpty()) {                                                       // If the word is not empty
						System.out.println(ConsoleColour.PURPLE);                                // Set the console color to purple
						System.out.println("Finding similar words for: " + word);                // Print which word is being processed
						System.out.println(ConsoleColour.RESET);                                 // Reset color
						similarWords(word, topN, norm);                                          // Find similar words for the current word
					}
				}
			} catch (IOException e) {                                                            // Handle any IO exceptions
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("Error finding similar words: " + e.getMessage());            // Print the error message
				System.out.println(ConsoleColour.RESET);                                         // Reset the console color
			} catch (Exception e) {                                                              // Handle other unexpected exceptions
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("An unexpected error occurred: " + e.getMessage());           // Print the error message
				System.out.println(ConsoleColour.RESET);                                         // Reset the console color
				e.printStackTrace();                                                             // Print the stack trace
			} finally {
				if (bWriter != null) {                                                           // Check if BufferedWriter was initialized
					try {
						bWriter.flush();                                                         // Ensure all data is written to the file
						bWriter.close();                                                         // Close the BufferedWriter
					} catch (IOException e) {                                                    // Handle any IO exceptions
						System.out.println(ConsoleColour.RED);                                   // Set the console text color to red
						System.err.println("Error closing writer: " + e.getMessage());           // Print the error message
						System.out.println(ConsoleColour.RESET);                                 // Reset the console color
					}
				}
			}
		}
		
		// Methods to find most similar words
		public static void similarWords(String text, int topN, boolean norm) throws IOException {                                 
			BufferedWriter bWriter = null;                                                       // Initialize BufferedWriter to null
			
			try {
				String[] inputWords = text.split("\\s+");                                        // Split the input text into words
				
				int[] indices = new int[inputWords.length];                                      // Array to store word indices

				// Check if words exist in embeddings
				for (int i = 0; i < inputWords.length; i++) {
					indices[i] = VecOperations.findWord(inputWords[i]);                          // Find word index
					if (indices[i] == -1) {                                                      // Check if the word was not found
						return;                                                                  // Exit if any word is not found
					}
				}
				
				double[] avgVector = VecOperations.averVector(indices);                          // Calculate the average vector
				if (avgVector == null) {                                                         // Check if the average vector is null
					System.out.println(ConsoleColour.RED);                                       // Set the console color to red
					System.err.println("Average vector is null. No valid words found in the input text."); // Print error message
					System.out.println(ConsoleColour.RESET);                                     // Reset the console color
					return;                                                                      // Exit the method if there is no valid average vector
				}
				if (norm) {
			        avgVector = VecOperations.normVec(avgVector);                                // Normalize vector if user selected
			    }

				int vecCount = VecOperations.getVecCount();                                      // Get the number of vectors available
				double[] simScores = new double[vecCount];                                       // Create an array to store similarity scores
				String[] simWords = new String[vecCount];                                        // Create an array to store corresponding words

				VecOperations.calcSimilarities(text, avgVector, simScores, simWords, norm);      // Calculate similarities
				String similarityMetric = VecOperations.getSimiMetric();                         // Get the name of the similarity metric used
				
				boolean ascending;
				if ("euclidean".equals(similarityMetric)) {                                      // Determine the sort order based on the similarity metric used. If the metric is "Euclidean"
				    ascending = true;                                                            // then 'ascending' should be set to true, since a smaller Euclidean distance indicates greater similarity,
				                                                                                 // which means the results should be sorted in ascending order, with the most similar items first.
				} else {                                                                         
				    ascending = false;                
				}    				                                                                                 
				
				VecOperations.quickSort(simScores, simWords, 0, vecCount - 1, ascending);        // Sort the results
				
				Actions.filter(inputWords, simScores, simWords);                                 // Filter words to exclude the input words from the final output
				String outputPath = FileProcessing.getOutputPath();                              // Get the output file path
				FileWriter fileWriter = new FileWriter(outputPath, true);                        // Open a FileWriter in append mode to write results to the file
				bWriter = new BufferedWriter(fileWriter);                                        // Wrap FileWriter in BufferedWriter		
				String mDisp;                                                                    // Declare a variable to hold the display name
				switch (similarityMetric) {                                                      // Start a switch statement to check the value of similarityMetric
	            case "euclidean":
	                mDisp = "Euclidean distance";
	                break;
	            case "cosine":
	                mDisp = "Cosine distance";
	                break;
	            case "dot":
	                mDisp = "Dot product";
	                break;
	            default:                                                                         // If similarityMetric doesn't match any of the above cases
	                mDisp = similarityMetric;                                                    // Set mDisp to the original similarityMetric value
	        }
				if (norm) {                                                                      // Check if normalization was applied
					mDisp  += " (with normalization)";                                           // Append text indicating normalization was used
				} else {
					mDisp  += " (without normalization)";                                        // Append text indicating no normalization was used
				}
				Output.outputResults(bWriter, text, simWords, simScores, topN, mDisp);           // Write the results to the file
				
				System.out.println(ConsoleColour.BLUE);                                          // Set the console color to blue
				System.out.println("Results written to " + outputPath);                          // Notify user that results were written to the file
				System.out.println(ConsoleColour.RESET);                                         // Reset the console color
			} catch (IOException e) {                                                            // Handle any IO exceptions that occur
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("Error writing to output file: " + e.getMessage());           // Print error message
				System.out.println(ConsoleColour.RESET);                                         // Reset the console color
			} catch (Exception e) {                                                              // Handle other unexpected exceptions
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("An unexpected error occurred: " + e.getMessage());           // Print error message
				System.out.println(ConsoleColour.RESET);                                         // Reset the console color
				e.printStackTrace();
			} finally {
				if (bWriter != null) {                                                           // If the BufferedWriter was initialized
					try {
						bWriter.flush();                                                         // Ensure all data is written to the file
						bWriter.close();                                                         // Close the BufferedWriter 
					} catch (IOException e) {                                                    // Handle any IO exceptions
						System.out.println(ConsoleColour.RED);                                   // Set the console color to red
						System.err.println("Error closing writer: " + e.getMessage());           // Print error message
						System.out.println(ConsoleColour.RESET);                                 // Reset the console color
					}
				}
			}
		}
		
		// Finds the most similar words to the input text after removing stop words
		public static void similarWithStopWordsRmv(String text, int topN, boolean norm) throws IOException {                      
			BufferedWriter bWriter = null;                                                       // Declare BufferedWriter, initially set to null
			try {
				text = TextManager.textProcessing(text);                                         // Text processing
				text = TextManager.rmvStopWords(text);                                           // Remove stop words
				
				
			    if (text.isEmpty()) {                                                            // Check if the text is empty
			        System.out.println(ConsoleColour.RED);                                       // Set the console color to red
			        System.out.println("Invalid input. The text is either empty or contains only stop words."); // Print error message
			        System.out.println(ConsoleColour.RESET);                                     // Reset the console color
			        return;                                                                      // Exit the method if the text is empty
			    }			    
			 
			    if (text.split("\\s+").length < 2) {                                             // Check if the processed text is too short (has less than two words)
			        System.out.println(ConsoleColour.RED);                                       // Set the console color to red
			        System.out.println("Invalid input. The text is too short after preprocessing."); // Print error message
			        System.out.println(ConsoleColour.RESET);                                     // Reset console color
			        return;                                                                      // Exit the method if the text is too short
			    }
			   				                   
				similarWords(text, topN, norm);                                                  // Find similar words
			} catch (IOException e) {                                                            // Catch any IOException
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("Error finding similar words: " + e.getMessage());            // Print error message
				System.out.println(ConsoleColour.RESET);                                         // Reset color
			} catch (Exception e) {                                                              // Catch other unexpected exceptions
				System.out.println(ConsoleColour.RED);                                           // Set the console color to red
				System.err.println("An unexpected error occurred: " + e.getMessage());           // Print error message
				System.out.println(ConsoleColour.RESET);                                         // Reset console color
				e.printStackTrace();                                                             // Print the stack trace
			} finally {
				if (bWriter != null) {                                                           // If BufferedWriter was initialized
					try {
						bWriter.flush();                                                         // Flush the writer to ensure all data is written
						bWriter.close();                                                         // Close the writer
					} catch (IOException e) {                                                    // Catch any IOException
						System.out.println(ConsoleColour.RED);                                   // Set the console color to red
						System.err.println("Error closing writer: " + e.getMessage());           // Print error message
						System.out.println(ConsoleColour.RESET);                                 // Reset console  color
					}
				}
			}
		}
			
}
