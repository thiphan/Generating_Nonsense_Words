package project3;
import java.util.*;

public class BSTDictionary<K extends Comparable<K>, V> {
	//create Node class
	public static class Node<K,V> {
		private K key;
		private V value;
		private Node<K,V> left, right;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		//Constructor
		public Node(K key, V value, Node<K,V> left, Node<K,V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		public K getKey() {
			return this.key;
		}
		public V getValue() {
			return this.value;
		}
		public String toString() {
			return "<" + key+", "+value+">";
		}
	}
	private Node<K,V> root;

	//add method: insert specified key-value pair into the dictionary
	public void add(K key, V value) {
		if(root == null)
			root = new Node<K,V>(key,value,null,null); 
		else
			root = insert(root,key,value);
	}
	private Node<K,V> insert(Node<K,V> current, K newKey, V newValue) {
		if(newKey.compareTo(current.key)==0)
			current.value = newValue;
		else if(newKey.compareTo(current.key)<0){
			//new key < root's key, check left side of root
			if(current.left==null)
				current.left = new Node<K,V>(newKey, newValue, null, null);
			else
				insert(current.left,newKey, newValue);
		}
		else {//new key > root's key, check right side of root
			if(current.right==null)
				current.right = new Node<K,V>(newKey, newValue,null,null);
			else
				insert(current.right,newKey, newValue);
		}
		return current;
	}
	//Get value method
	public V getValue(K key) {
		if(root == null)
			return null;
		else {
			return getValue(root,key);
		}
		
	}
	private V getValue(Node<K,V> current, K newKey) {
		if(newKey.compareTo(current.key)==0)
			return current.value;
		else if(newKey.compareTo(current.key)<0){//key<current's key, check left side
			if(current.left==null)
				return null;
			else
				return getValue(current.left, newKey);
		}
		else {//key<current's key, check right side
			if(current.right==null)
				return null;
			else
				return getValue(current.right, newKey);
		}
				
	}
	//Contains method
	public boolean contains(K key) {
		if(root == null)
			return false;
		else
			return checkContains(root, key);
	}
	private boolean checkContains(Node<K,V> current, K newkey) {
		boolean result = false;
		if(newkey.compareTo(current.key)==0)
			result = true;
		else if(newkey.compareTo(current.key)<0) {//check left side
			if(current.left==null)
				result = false;
			else
				checkContains(current.left, newkey);
		}
		else {//check right side
			if(current.right==null)
				result = false;
			else
				checkContains(current.right, newkey);
		}
		return result;
	}
	//iterator method using BST code written in class.
	private class InOrderIterator implements Iterator<Node<K,V>> {	// Since InOrderIterator is not static, it *does* have access to the instance variables of BST.
		private Node<K,V> current = root;
		private Stack<Node<K,V>> pile = new Stack<>();
		
		@Override
		public boolean hasNext() {
			return !(current == null && pile.empty());
		}

		@Override
		public Node<K,V> next() {
			while (current != null) {
				pile.push(current);
				current = current.left;
			}
			
			Node<K,V> popped = pile.pop();
			current = popped.right;
			return popped;
		}
	}
	public Iterator<Node<K,V>> iterator() {
		return new InOrderIterator();
	}
	
	//to string to test code
	public String toString() {
		return toString(root, "");
	}

	// Recursively computes a toString of the subtree rooted at where.
	// The indent parameter keeps track of how many spaces should be included in front.
	private String toString(Node<K,V> where, String indent) {
		if (where == null)
			return indent + "null";
		else
			return indent + where.key +"," + where.value + "\n" + toString(where.left, indent+" ") + "\n" + toString(where.right, indent +" ");
	}
	
	//test class
	public static void main(String[] args) {
		BSTDictionary<Integer,Double> tree = new BSTDictionary<>();
		tree.add(4, 0.5);
		tree.add(19, 0.2);
		tree.add(75, 0.3);		
		tree.add(75, 0.7);
		
		Iterator<Node<Integer,Double>> it = tree.iterator();
		
		while (it.hasNext())
			System.out.println(it.next());
		System.out.println(tree);
		
	}
}
