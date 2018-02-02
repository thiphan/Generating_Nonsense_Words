package project3;

import java.util.*;

public class Client {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter data file name:");
		String fileName = input.nextLine();
		
		System.out.println("Please enter value for n:");
		int n = input.nextInt();
		
		System.out.println("Please enter number of words to generate");
		int NumOfWords = input.nextInt();
		input.close();
		
		String word ="";
		ArrayList<String> generated_Words = new ArrayList<>();//to store words are generated
		
		//generate 'NumOfWords' words
		for(int i = 0 ; i<NumOfWords;i++) {
			KeyGen<String> keygenString = new KeyGen<>();//create String KeyGen object
			KeyGen<Integer> keygenInt= new KeyGen<>();//create Integer KeyGen object
			
			//determine the length of the word
			BSTDictionary<Integer,Double> length_Frequency_Dict =  DataFileReader.createDictWordLengths(fileName);
			int wordLength = keygenInt.generateKey(length_Frequency_Dict);
			System.out.println("wordLength: " + wordLength);//test
			
			//randomly pick the first character of he word
			char letter = (char)('a' + Math.random() * ('z' - 'a' + 1));
			//System.out.println("letter: " + letter);//test

			word += letter+"";
			int j =1;
			
			//pick other characters base on the first one
			while(word.length()!= wordLength ) {
				BSTDictionary<String, BSTDictionary<String,Double>> Dict_Of_Dict = DataFileReader.createDictLetterFrequencies(fileName, j);
				
				BSTDictionary<String,Double> inner_DictOfDict = Dict_Of_Dict.getValue(word.substring(0, j));
				//System.out.println("inner_DictOfDict: " +inner_DictOfDict);//test
				word+= keygenString.generateKey(inner_DictOfDict);
				System.out.println("word: "+word);
				if(j<n)
					j++;
			}
			generated_Words.add(word);
		}
		System.out.println(generated_Words);
	}
}
