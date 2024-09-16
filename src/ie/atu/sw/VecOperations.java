package ie.atu.sw;

public class VecOperations {
	private static String[] words;                                              // Array to store words
	private static double[][] vectors;                                          // 2D array to store vectors corresponding to the words
	private static int vecCount = 0;                                            // Number of vectors
	private static final int FEATURES_COUNT = 50;                               // Number of features in each vector
	private static String similarityMetric = "cosine";                          // Default metric
	
	public static String[] getWords() {                                         // Getter for the words array
        return words;
    }
		
    public static void setWords(String[] newWords) {                            // Setter for the words array
        words = newWords;
    }

    public static double[][] getVectors() {                                     // Getter for the vectors array
        return vectors;
    }
    
    public static void setVectors(double[][] newVectors) {                      // Setter for the vectors array
        vectors = newVectors;
    }

    public static int getVecCount() {                                           // Getter for the number of vectors stored
        return vecCount;
    }
    
    public static void setVecCount(int count) {                                 // Setter for the number of vectors stored
        vecCount = count;
    }

    public static int getFeaturesCount() {                                      // Getter for the number of features in each vector
        return FEATURES_COUNT;
    }
    
    public static String getSimiMetric() {                                      // Getter for the similarity metric used
        return similarityMetric;
    }

    public static void setSimMetric(String metric) {                            // Setter for the similarity metric
        similarityMetric = metric;
    } 
    
    // This method normalizes a vector
    // The code is based on the formula https://www.calculatorultra.com/en/tool/normalize-vector-calculator.html 
    // and http://www.java2s.com/example/java-utility-method/vector-normalize-index-0.html
    
	public static double[] normVec(double[] v) {                      
	    double sum = 0.0;                                                       // Initialize the variable to store the length of the vector
	    
	    for (double value : v) {                                                // Loop through each element of the vector
	        sum += value * value;                                               // Add the square of the element to the length
	    }
	    sum = Math.sqrt(sum);                                                   // Take the square root of the sum
	    
	    double[] normalizedV = new double[v.length];                            // Create a new array
	    
	    for (int i = 0; i < v.length; i++) {                                    // Loop through each element in the original vector
	        normalizedV[i] = v[i] / sum;                                        // Divide each element by the length
	    }	 
	    return normalizedV;
	}
	
	// Method to calculate cosine similarity between two vectors    
	// The code is partially taken from the link https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-similarity-of-two-vectors
	// Modified for better readability and added division by zero check
	public static double cosineSimilarity(double[] vec1, double[] vec2) {   			
			double dProduct = 0.0;                                              // Initialize the variable to store the dot product of two vectors
			double normA = 0.0;                                                 // Initialize the variable normA to store the norm of the first vector
			double normB = 0.0;                                                 // Initialize the variable normB to store the norm of the second vector
			for (int i = 0; i < vec1.length; i++) {                             // Loop through each element
				dProduct += vec1[i] * vec2[i];                                  // Calculate dot product
				normA += Math.pow(vec1[i], 2);                                  // Calculate normA
				normB += Math.pow(vec2[i], 2);                                  // Calculate normB
			}
			if (normA <= 0.0 || normB <= 0.0) {                                 // Check if either norm is zero
		        return 0.0;                                                     // Return 0.0 if either vector has zero norm to avoid division by zero
		    }
			return dProduct / (Math.sqrt(normA) * Math.sqrt(normB));            // Return cosine similarity
	}
 
	// Method to calculate Euclidean distance between two vectors
	// Source https://stackoverflow.com/questions/54357325/calculating-largest-euclidean-distance-between-two-values-in-a-2d-array
	// 
	public static double euclideanDistance(double[] vec1, double[] vec2) {  			
			double sum = 0.0;                                                   // Initialize sum
			for (int i = 0; i < vec1.length; i++) {                             // Loop through each element of the vectors
				sum += Math.pow(vec1[i] - vec2[i], 2);                          // Calculate
			}
			return Math.sqrt(sum);                                              // Return Euclidean distance (take the square root of the sum)
	}

		// Method to calculate dot product between two vectors
		// Source https://www.javatpoint.com/vector-operations-java
	    // 
		public static double dotProduct(double[] vec1, double[] vec2) {    		                                                                 
			double sum = 0.0;                                                   // Initialize the variable sum 
			for (int i = 0; i < vec1.length; i++) {                             // Loop through each element in the vectors
				sum += vec1[i] * vec2[i];                                       // Calculate dot product  				
			}	
			return sum;                                                         // Return dot product
		}
		
		// Method to add two vectors
		public static double[] addVec(double[] vec1, double[] vec2) {            
			double[] result = new double[vec1.length];                          // Create array to store the result
			for (int i = 0; i < vec1.length; i++) {                             // Loop through each element in the vectors
				result[i] = vec1[i] + vec2[i];                                  // Add elements of vec1 and vec2
			}
			return result;                                                      // Return the resulting vector
		}

		// Method to subtract one vector from another 
		public static double[] subtractVec(double[] vec1, double[] vec2) {      
			double[] result = new double[vec1.length];                          // Create array to store the result
			for (int i = 0; i < vec1.length; i++) {                             // Loop through each element
				result[i] = vec1[i] - vec2[i];                                  // Subtract elements of vec2 from vec1
			}
			return result;                                                      // Return the resulting vector
		}

		// Method to multiply two vectors 
		public static double[] multiplyVec(double[] vec1, double[] vec2) {      
			double[] result = new double[vec1.length];                          // Create array to store the result
			for (int i = 0; i < vec1.length; i++) {
				result[i] = vec1[i] * vec2[i];                                  // Multiplying elements
			}
			return result;                                                      // Return the resulting vector
		}

		// Method to divide two vectors 
		public static double[] divideVec(double[] vec1, double[] vec2) {        
			double[] result = new double[vec1.length];                          // Create array to store the result
			for (int i = 0; i < vec1.length; i++) {
				if (vec2[i] != 0.0) {                                           // Avoid division by zero
					result[i] = vec1[i] / vec2[i];                              // Divide elements
				} else {
					result[i] = 0.0;                                            // Handle division by zero
				}
			}
			return result;                                                      // Return the resulting vector
		}
		
		// Method that finds the index of the given word in the embeddings array
		public static int findWord(String word) { 
		    int index = findIndex(word);                                       // Find the index of the word in the embeddings array
		    if (index == -1) {                                                 // Check if the word was not found
		        System.out.println(ConsoleColour.RED);                         // Set console color to red
		        System.out.println("Word not found in embeddings: " + word);   // Print an error message
		        System.out.println(ConsoleColour.RESET);                       // Reset console color
		    }
		    return index;                                                      // Return the index
		}
		
		// Method to find the index of a word in the words array
		// Source https://www.javaguides.net/2024/05/java-find-index-of-element-in-array.html
		public static int findIndex(String word) {                                               
			for (int i = 0; i < vecCount; i++) {                                // Iterate over each element in the array "words"
				if (words[i].equals(word)) {                                    // Check if the current word in the array matches the given word
					return i;                                                   // If a match is found, return the index of the matching word
				}
			}
			return -1;                                                          // Return -1 if the word is not found
		}
		
		// Method to calculate the average vector for a given text
		public static double[] averVector(int[] indices) {                                         
			double[] avgVector = new double[FEATURES_COUNT];                    // Create an array to hold the average vector with a fixed size
			int numVect = 0;                                                    // Initialize a counter to count valid words

			for (int index : indices) {                                         // Loop through each index in the array
				for (int i = 0; i < avgVector.length; i++) {                    // Loop through each element in the avgVector
	                avgVector[i] += vectors[index][i];                          // Add each element of the vector to the corresponding position in avgVector
	            }
	            numVect++;                                                      // Increment the numVect counter
			}

			
				for (int i = 0; i < avgVector.length; i++) {                     // Loop through each element in the average vector
					avgVector[i] /= numVect;                                     // Calculate the average
				}
				return avgVector;                                                // Return the calculated average vector
			
		}
		
		// Method to calculate similarity between two vectors
		public static double calcSimilarity(double[] vec1, double[] vec2) {                      
			switch (similarityMetric) {                                          // Check which similarity metric is chosen
			case "euclidean":                                                                    
				return euclideanDistance(vec1, vec2);                            // If metric is Euclidean, calculate Euclidean distance
			case "dot":                                                                          
				return dotProduct(vec1, vec2);                                   // If metric is dot product, calculate dot product
			case "cosine":
			default:
				return cosineSimilarity(vec1, vec2);                             // If metric is cosine or anything else, calculate cosine similarity
			}
		}
		
		// Method to calculate similarities between the average vector and all vectors
		public static void calcSimilarities(String text, double[] avgVector, double[] simScores, String[] simWords, boolean norm) {
			double[][] vecLocal = vectors;                                       // Local variable for accessing the vectors array
		    String[] wLocal = words;                                             // Local variable to access the words array
			for (int i = 0; i < vecCount; i++) {                                 // Loop through all vectors			
				double[] vectorToCompare = vecLocal[i];                          // Get the current vector

		        if (norm) {                                                      // If normalization is required
		            vectorToCompare = normVec(vectorToCompare);                  // Normalize the vector
		        }
				
				    simScores[i] = calcSimilarity(avgVector, vectorToCompare);   // Compute similarity score
					simWords[i] = wLocal[i];                                     // Save the words			
					if (i % 10 == 0) {                                           // Update progress every 10 iterations
						System.out.print(ConsoleColour.YELLOW);                  // Set the console color to yellow
						Output.printProgress(i + 1, vecCount);                   // Show progress
						System.out.print(ConsoleColour.RESET);                   // Reset the console color
					}
			}
			System.out.print(ConsoleColour.YELLOW);                              // Set the console color to yellow
		    Output.printProgress(vecCount, vecCount);                            // Show progress
		    System.out.print(ConsoleColour.RESET);                               // Reset the console color
		}
		
		// Method to calculate a vector result from an arithmetic expression
		public static double[] calcExpr(String expr, boolean norm,  StringBuilder correctedExpr) {                             
		    String[] element = expr.split("\\s+");                               // Split the input expression into elements by spaces
		    String prevElement = null;                                           // Variable to store the previous element
		    double[] vec1 = null;                                                // Variable to store the first vector
		    
		    if (element.length < 3) {                                            // Check if there are enough elements to form a valid expression
		        System.out.println(ConsoleColour.RED);                           // Set console color to red
		        System.out.println("Invalid expression. Expression must contain at least one operation and two words."); // Print error message
		        System.out.println(ConsoleColour.RESET);                         // Reset console color
		        return null;                                                     // Return null because the expression is invalid
		    }
		    
		    for (int i = 0; i < element.length; i++) {                           // Loop through each element
		        String el = element[i];                                          // Get the current element
		        int index;                                                       // Variable to store the index of the word in the vector array

		        if (i % 2 == 0) {                                                // Element at even positions should be words
		        	el = el.replaceAll("[^a-zA-Z]", "");                         // Remove all non-alphabetic characters
		        	
		        	index = VecOperations.findWord(el);                          // Find the index of the word in the vector array
		            if (index == -1) {                                           // If the word is not found		     
		                return null;                                             // Return null because the word was not found
		            }
		            
		            correctedExpr.append(el).append(" ");
		            
		            double[] vec2 = vectors[index];                              // Next vector
		            if (norm) {                                                  // If normalization is required
		                vec2 = normVec(vec2);                                    // Normalize the vector
		            }

		            if (vec1 == null) {                                          // If vec1 is not set yet
		                vec1 = vec2.clone();                                     // Clone vec2 into vec1
		            } else {                                                     // If vec1 is already set	
		            	if (vec1.length != vec2.length) {                        // Check the length of two vectors
		            	    System.out.println(ConsoleColour.RED);               // Set console text color to red
		            	    System.out.println("Error: Vectors must be of the same length."); // Print an error
		            	    System.out.println(ConsoleColour.RESET);             // Reset console color
		            	    return null;                                         // Exit the method and return null 
		            	}
		                switch (prevElement) {                                   // Check the previous operator
		                    case "+":
		                        vec1 = VecOperations.addVec(vec1, vec2);         // Add vectors
		                        break;
		                    case "-":
		                        vec1 = VecOperations.subtractVec(vec1, vec2);    // Subtract vectors
		                        break;
		                    case "*":
		                        vec1 = VecOperations.multiplyVec(vec1, vec2);    // Multiply vectors
		                        break;
		                    case "/":
		                        vec1 = VecOperations.divideVec(vec1, vec2);      // Divide vectors
		                        break;
		                    default:
		                        System.out.println(ConsoleColour.RED);                           // Set console color to red
		                        System.out.println("Unexpected operator: " + prevElement);       // Print error message
		                        System.out.println(ConsoleColour.RESET);                         // Reset console color
		                        return null;                                                     // Return null because of unexpected operator
		                }
		            }
		        } else {                                                                         // If the element index is odd, it should be an operator
		            if (!(el.equals("+") || el.equals("-") || el.equals("*") || el.equals("/"))) {
		                System.out.println(ConsoleColour.RED);                                   // Set console color to red
		                System.out.println("Invalid operator: " + el);                           // Print error message
		                System.out.println(ConsoleColour.RESET);                                 // Reset console color
		                return null;                                                             // Return null because of invalid operator
		            }
		            correctedExpr.append(el).append(" ");
		            prevElement = el;                                                            // Store the current operator
		        }
		    }
		   
		    if (element.length % 2 == 0) {                                                       // Check if the expression ends with an operator
		        System.out.println(ConsoleColour.RED);                                           // Set console color to red
		        System.out.println("Invalid expression. Expression cannot end with an operator."); // Print error message
		        System.out.println(ConsoleColour.RESET);                                         // Reset console color
		        return null;                                                                     // Return null because the expression is incomplete
		    }

		    return vec1;                                                                         // Return the resulting vector
		}
		
		// Method to sort similarity scores and corresponding words
		// Source https://www.javatpoint.com/quick-sort
		// And https://medium.com/@hamzamirza347/quick-sort-optimization-6784c43935bd
		// The code is modified to sort two arrays at once - one for scores and one for words 
		// The modification also allows sorting in both ascending and descending order
		public static void quickSort(double[] arr, String[] words, int low, int high, boolean ascending) { 
			                                                                                    
			while (low < high) {                                                // Loop until the entire array is sorted
		        int pi = partition(arr, words, low, high, ascending);           // Partition the array and get the pivot index
		        
		        if (pi - low < high - pi) {                                     // Check if the left side is smaller
		            quickSort(arr, words, low, pi - 1, ascending);              // Sort the left subarray
		            low = pi + 1;                                               // Update the low index
		        } else {
		            quickSort(arr, words, pi + 1, high, ascending);             // Sort the right subarray
		            high = pi - 1;                                              // Update the high index
		        }
		    }
		}

		// Method to partition the array for quicksort
		public static int partition(double[] arr, String[] words, int low, int high, boolean ascending) { 
			double pivot = arr[high];                                            // Last element as the pivot
			int i = low - 1;                                                     // Initialize index 
			for (int j = low; j < high; j++) {                                   // Loop through the array
		        if (ascending) {                                                 // Check if we need to sort in ascending order
		            if (arr[j] < pivot) {                                        // If the current element is less than the pivot
		                i++;                                                     // Increment i
		                // Swap arr[i] and arr[j]
		                double tempScore = arr[i];
		                arr[i] = arr[j];
		                arr[j] = tempScore;

		                // Swap words[i] and words[j]
		                String tempW = words[i];
		                words[i] = words[j];
		                words[j] = tempW;
		            }
		        } else {                                                                         
		            if (arr[j] > pivot) {                                        // Comparison of elements
		                i++;                                                     // Increment i
		                // Swap arr[i] and arr[j]
		                double tempScore = arr[i];
		                arr[i] = arr[j];
		                arr[j] = tempScore;

		                // Swap words[i] and words[j]
		                String tempWord = words[i];
		                words[i] = words[j];
		                words[j] = tempWord;
		            }
		        }
		    }

            // Swap arr[i+1] and arr[high]
			double tempScore = arr[i + 1];
			arr[i + 1] = arr[high];
			arr[high] = tempScore;

            // Swap words[i+1] and words[high]
			String tempWord = words[i + 1];
			words[i + 1] = words[high];
			words[high] = tempWord;

			return i + 1;                                                        // Return the partition index
		}

	}
