package project3;
import java.util.*;

import project3.BSTDictionary.Node;

import java.io.*;
public class DataFileReader {
	
	/**method read file & return a dictionary associating word lengths 
	with frequency.*/
	public static BSTDictionary<Integer,Double> createDictWordLengths(String fileName){
		BSTDictionary<Integer,Double> result = new BSTDictionary<>();
		Scanner input, entry;
		int totalWord =0;
		try {
			entry= new Scanner(new File(fileName));
			while(entry.hasNext()){
				String string = entry.nextLine();
				//System.out.println(string);//test
				totalWord++;
			}
			//System.out.println(totalWord);//test
			input = new Scanner(new File(fileName));
			while(input.hasNext()) {
				String word = input.nextLine(); //read word in file
				int wordLength = word.length();

				//add new node with value = first time occur if the key does not exist
				if(!result.contains(wordLength)) {
					result.add(wordLength, (double)(1.0/totalWord));
					//System.out.println(wordLength +" , "+ result.getValue(wordLength));//test
				}
				else {				//update value if the key already exist
					double previousValue = result.getValue(wordLength);
					result.add(wordLength,(double)(previousValue+(1.0/totalWord)));
					//System.out.println(wordLength+" , "+ result.getValue(wordLength));//test
				
				}
			}
			entry.close();input.close();
	
		}
		catch (FileNotFoundException e) {
			System.out.println("Error!Find not found!");;
		}
		return result;
	}
	
	/** Method read find & return dictionary of dictionaries */
	public static BSTDictionary<String, BSTDictionary<String,Double>> createDictLetterFrequencies(String fileName, int n){
		
		BSTDictionary<String, BSTDictionary<String,Double>> result = new BSTDictionary<>();
		BSTDictionary<String,Double> innerDict = new BSTDictionary<>();
		BSTDictionary<String,Double> empty = new BSTDictionary<>();
		ArrayList<Node<String,Integer>> outerKeyList = new ArrayList<>();
		ArrayList<String> list = new ArrayList<>();

		Scanner entry;
		String word;
		String previousOuterKey ="";
		int countOuterKey = 0;
		try {
			entry = new Scanner(new File(fileName));
		//Count number of each outerKey
			while(entry.hasNext()) {
				word = entry.next(); //read word in file
				if(word.length()>n) { //handle out of bound exception in word length
					String outerKey = word.substring(0,n);
					list.add(outerKey);
				}
				else
					continue; //skip the shorter word compare to n
			}
			for(String s: list) {
				for(String j: list) {
					if(s.equals(j))
						countOuterKey++;
				}
				outerKeyList.add(new Node<>(s, countOuterKey));
				countOuterKey=0;
			}
			
			System.out.println(outerKeyList);

			while(entry.hasNext()) {
				word = entry.next(); //read word in file
				if(word.length()>n) { //handle out of bound exception in word length
					String outerKey = word.substring(0,n);
					String innerKey = word.charAt(n)+"";
					System.out.println(outerKeyList.getValue(outerKey));
					//update or create innerDict
					if(!result.contains(outerKey)) {
						innerDict.add(innerKey, 1.0/outerKeyList.getValue(outerKey));
						if(previousOuterKey!="")
							innerDict = empty;
					}
					else {
						if(innerDict.contains(innerKey)) 
							innerDict.add(innerKey, innerDict.getValue(innerKey)+(1.0/outerKeyList.getValue(outerKey)));
						else
							innerDict.add(innerKey, 1.0/outerKeyList.getValue(outerKey));
						
					}
					
				result.add(outerKey, innerDict);	
				}
			}
			
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found exception!");
		}
		return result;		
	}
			
	public static void main(String[] args) {
		BSTDictionary<Integer,Double> tree = createDictWordLengths("test.txt");
		//System.out.println(tree);
		BSTDictionary<String, BSTDictionary<String,Double>> tree2 = createDictLetterFrequencies("test.txt",3);
		System.out.println(tree2);

	}
}
