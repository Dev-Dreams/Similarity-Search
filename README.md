# Similarity-Search
## Description 
The "Similarity Search with Word Embeddings" project is an application designed to help users 
find the most similar words based on word embeddings. The application allows the user to input 
a word or a short sentence and compare it against a large set of word embeddings provided in a 
text file. The output will be a list of the most similar words along with their similarity scores. 
Additionally, the application supports several advanced features, including arithmetic operations 
with words, handling multiple words or sentences, and the ability to choose different similarity 
metrics (e.g., cosine distance, Euclidean distance, dot product), and the option to use 
normalization. 
## To Run 
1. Navigate to the project directory in your terminal, then to src with cd ./src
2. Compile the Java source files: javac ie/atu/sw/*.java
3. To run the application, execute the following command from the project directory: 
java ie.atu.sw.Runner
## Additional Features 
### Arithmetic Operations with Words. 
Users can perform arithmetic operations (add, subtract, multiply, divide) on word vectors. 
### Enter Multiple Words or a Sentence. 
1. Find the most similar words for the entire input text. It calculates a single average vector for 
the whole input text and finds the most similar words to this vector. 
2. Remove stop words and find the most similar words for the entire input text. It removes 
common stop words from the input and then finds the most similar words based on the cleaned 
text. 
3. Find and display the most similar words for each word individually. 
### Users can choose from three different similarity metrics. 
 1. Cosine Distance. 
 2. Euclidean Distance. 
 3. Dot Product. 
### Normalization Option. 
Users can choose to use normalization or not.
