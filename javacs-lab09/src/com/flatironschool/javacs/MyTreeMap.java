/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
/**
 * Implementation of a Map using a binary search tree.
 * 
 * @param <K>
 * @param <V>
 *
 */
public class MyTreeMap<K, V> implements Map<K, V> {

	private int size = 0;
	private Node root = null;

	/**
	 * Represents a node in the tree.
	 *
	 */
	protected class Node {
		public K key;
		public V value;
		public Node left = null;
		public Node right = null;
		
		/**
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
		
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public boolean containsKey(Object target) {
		return findNode(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none. 
	 * 
	 * @param target
	 */
	private Node findNode(Object target) {
		// some implementations can handle null as a key, but not this one
		if (target == null) {
            throw new NullPointerException();
	    }
		
		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		
		// the actual search
        	Node searchNode=root;
		while(searchNode!=null){
			if(k.compareTo(searchNode.key)==0)
				return searchNode;
		
			else if(k.compareTo(searchNode.key)<0)
				searchNode=searchNode.left;
			else
				searchNode=searchNode.right;
		}

        return null;
	}

	/**
	 * Compares two keys or two values, handling null correctly.
	 * 
	 * @param target
	 * @param obj
	 * @return
	 */
	private boolean equals(Object target, Object obj) {
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		Stack<Node> s = new Stack<Node>();
		s.push(root);
		while(!s.isEmpty()){
			Node searchNode=s.pop();
		if(searchNode==null)
			continue;
		if(equals(target,searchNode.value))
			return true;
		s.push(searchNode.left);
		s.push(searchNode.right);
		}
		
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		Node node = findNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new LinkedHashSet<K>();
        // TODO: Fill this in.
		keySetHelper(root,set);
		return set;
	}
	public void keySetHelper(Node n,Set s){
		if(n.left!=null)
			keySetHelper(n.left,s);
		s.add(n.key);
		if(n.right!=null)
			keySetHelper(n.right,s);
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new Node(key, value);
			size++;
			return null;
		}
		return putHelper(root, key, value);
	}

	private V putHelper(Node node, K key, V value) {
        	Node n=findNode(key);
		if(n!=null){
			V oldVal=n.value;
			n.value=value;
			return oldVal;
		}
		else{
			n = new Node(key, value);
			Node searchNode=node;
			Comparable<? super K> k = (Comparable<? super K>) key;
			while(searchNode!=null){
				if(k.compareTo(searchNode.key)<0){
					if(searchNode.left==null){
						searchNode.left=n;
						size++;
						return null;
					}
					else
						searchNode=searchNode.left;
				}
				else if(k.compareTo(searchNode.key)>0){
					 if(searchNode.right==null){
                                                searchNode.right=n;
						size++;
                                                return null;
                                        }
					else
                                        searchNode=searchNode.right;
			
			
       			
				}
				else{
					searchNode.key=key;	
					return null;
				}
		}
	}
			return null;	
}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		Deque<Node> stack = new LinkedList<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (node == null) continue;
			set.add(node.value);
			stack.push(node.left);
			stack.push(node.right);
		}
		return set;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyTreeMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);
		
		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Makes a node.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public MyTreeMap<K, V>.Node makeNode(K key, V value) {
		return new Node(key, value);
	}

	/**
	 * Sets the instance variables.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param node
	 * @param size
	 */
	public void setTree(Node node, int size ) {
		this.root = node;
		this.size = size;
	}

	/**
	 * Returns the height of the tree.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @return
	 */
	public int height() {
		return heightHelper(root);
	}

	private int heightHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int left = heightHelper(node.left);
		int right = heightHelper(node.right);
		return Math.max(left, right) + 1;
	}
}
