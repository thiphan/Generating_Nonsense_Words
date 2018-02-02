package project3;

import java.util.*;

import project3.BSTDictionary.Node;

public class KeyGen<K extends Comparable<K>> {
	
	public K generateKey(BSTDictionary<K, Double> d) {
		K result = null;
		double total =0.0;
		
		//count total percent
		Iterator<Node<K, Double>> it = d.iterator();
		while(it.hasNext()) {
			Node<K, Double> element = it.next();
			total += element.getValue();
		}
		
		//randomly generate a double number
		double random = (double)(Math.random()*total);
		double value = 0.0;
		
		//pick key associate with random number
		Iterator<Node<K, Double>> it2 = d.iterator();
		while(it2.hasNext()) {
			Node<K, Double> node = it2.next();
			value += node.getValue();
			if(random<=value) {
				result = node.getKey();
				break;
			}
		}
		return result;
	}
	public static void main(String[] args) {
		KeyGen<Integer> keygen = new KeyGen<>();
		BSTDictionary<Integer,Double> tree = new BSTDictionary<>();
		tree.add(4, 0.5);
		tree.add(19, 0.2);
		tree.add(75, 0.3);
		
		Integer key = keygen.generateKey(tree);
		System.out.println("key1:"+key);
		
		BSTDictionary<Integer,Double> t = DataFileReader.createDictWordLengths("test.txt");
		Integer k = keygen.generateKey(t);
		System.out.println("key2:"+k);
		
		KeyGen<String> keygen2 = new KeyGen<>();
		BSTDictionary<String, BSTDictionary<String,Double>> d = DataFileReader.createDictLetterFrequencies("latin.txt", 5);
		String s = keygen2.generateKey(d.getValue("abbac"));
		System.out.println("key3:"+s);
	}
}
