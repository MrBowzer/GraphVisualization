import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.Collection; //for returning in the values() function only

//If you uncomment the import to ArrayList below, the grader will manually
//look to make sure you are not using it anywhere else... if you use it
//somewhere else you will get 0pts on the entire project (not a joke).

//uncomment the line below ONLY if you are implementing values().
//import java.util.ArrayList; //for returning in the values() function only

/**
 * This is the implementation of a hashmap.
 * @param <K> the key that is in the hashmap.
 * @param <V> the value that is the hashmap.
 */
class ThreeTenHashMap<K,V> implements Map<K,V> {
	//********************************************************************************
	//   DO NOT EDIT ANYTHING IN THIS SECTION (except to add the JavaDocs)
	//********************************************************************************
	
	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	/**
	 * This is the storage of nodes in the hashmap.
	 */
	private Node<K,V>[] storage;
	
	//you must use to track the current number of elements
	//and you may not alter this variable's name, type, etc.
	/**
	 * This tracks the amount of elements in the hashmap.
	 */
	private int numElements = 0;
	
	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************
	/**
	 * this is the original size of the hashmap.
	 */
	private int originalSize;
	/**
	 * This is the constructor of the hashmap.
	 * @param size is the size the storage will be.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashMap(int size) {
		//Create a hash table where the size of the storage is
		//the provided size (number of "slots" in the table)
		//You may assume size is >= 1
		
		//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new Object[10];
		//instead, use this format:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new ClassWithGeneric[10];

		Node<K,V>[] temp = new Node[size];
		storage = temp;
		originalSize = size;

	}

	/**
	 * This simply clears the hashmap.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		//the table should return to the original size it had
		//when constructed
		//O(1)
		Node<K,V>[] temp = new Node[originalSize];
		storage = temp;

	}

	/**
	 * This returns if the hashmap is empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		//O(1)

		return numElements == 0;
	}

	/**
	 * This see if there is any slots in the table.
	 * @return how many "slots" are in the table.
	 */
	public int getSlots() {
		//return how many "slots" are in the table
		//O(1)
		return storage.length;
	}

	/**
	 * This returns how many elements are in the hashmap.
	 * @return the number if elements in the table.
	 */
	public int size() {
		//return the number of elements in the table
		//O(1)
		return numElements;
	}

	/**
	 * Given a key, return the value from the table.
	 * @param key the key that the method id fetching
	 * @return the value associated with the key.
	 */
	public V get(Object key) {
		//Given a key, return the value from the table.
		
		//If the value is not in the table, return null.
		
		//Worst case: O(n), Average case: O(1)
		int location = Math.abs(key.hashCode()) % storage.length;
		if(location == Math.abs(Integer.MIN_VALUE) % storage.length)
		{
			location = location * -1;
		}
		V value = null;
		if(storage[location] == null)
		{
			return null;
		}
		else if(storage[location].entry.key.equals(key))
		{
			return storage[location].entry.value;
		}
		else{
			Node<K,V> temp = storage[location];
			while(temp != null)
			{
				if(temp.entry.key.equals(key))
				{
					value = storage[location].entry.value;
				}
				temp = temp.next;
			}
		}

		return value;
	}

	/**
	 * This shows all the keys in the Hashmap.
	 * @return all the keys in the hashmap.
	 */
	public Set<K> keySet() {
		//O(n+m) or better, where n is the size and m is the
		//number of slots
		
		//Hint: you aren't allowed to import
		//anything, but a ThreeTenHashSet is a Set

		ThreeTenHashSet<K> keys = new ThreeTenHashSet<>();

		for(int i =0; i<storage.length; i++){
			if(storage[i] != null){
				Node<K,V> current = this.storage[i];
				while (current != null){
					keys.add(current.entry.key);
					current = current.next;
				}
			}
		}

		if(keys.isEmpty()){
			return null;
		}
		return keys;
	}

	/**
	 * This removes the key in the hashmap.
	 * @param key is the key that will be removed.
	 * @return the value that was removed with the key.
	 */
	public V remove(Object key) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.		
		//If the value is not in the table, return null.
		
		//Hint: Remember there are no tombstones for
		//separate chaining! Don't leave empty nodes!
		
		//Worst case: O(n), Average case: O(1)
		int location = Math.abs(key.hashCode()) % storage.length;
		if(location == Math.abs(Integer.MIN_VALUE) % storage.length)
		{
			location = location * -1;
		}
		V value = null;
		Node<K,V> temp = storage[location];
		Node<K,V> prevNode = null;
		if(storage[location] == null)
		{
			return null;
		}
		else if(storage[location].entry.key.equals(key))
		{
			if(storage[location].next != null)
			{
				value = storage[location].entry.value;
				storage[location] = temp.next;
				temp.next = null;
				numElements --;
			}
			else {
				value = storage[location].entry.value;
				storage[location] = null;
				numElements --;
			}
		}
		else
		{
			while(temp != null && !temp.entry.key.equals(key))
			{
				prevNode = temp;
				temp = temp.next;
			}
			if(temp != null && temp.next == null)
			{
				//do something
				value = temp.entry.value;
				prevNode.next = null;
				numElements --;
			}
			else if(temp != null)
			{
				value = temp.entry.value;
				prevNode.next = temp.next;
				temp.next = null;
				numElements --;

			}
		}

		
		return value;
	}

	/**
	 * This puts the key and value in the hashmap.
	 * @param key the key that will be added to the hashmap.
	 * @param value the value that will be added to the hashmap.
	 * @return the value.
	 */
	private V putNoExpand(K key, V value) {
		//Place value v at the location of key k.
		//Use separate chaining if that location is in use.
		//You may assume both k and v will not be null.
		//This method does NOT handle issues with the load,
		//that is handled by put(K,V) in the provided section
		//The return value is the same as specified by put()
		//(see the Map interface).
		
		//Hint 1: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the location of the entry.
		
		//Here's something you may want to know about Math's
		//absolute value method:
		//	"If the argument is equal to the value of Integer.MIN_VALUE,
		//  the most negative representable int value, the result is
		//  that same value, which is negative." -Oracle Docs
		
		//Hint 2: Remember that you're dealing with an array
		//of linked lists in this part of the project, not
		//an array of table entries.
		
		//If the key already exists in the table
		//replace the current value with v.
		
		//If the key does not exist in the table, add
		//the new node to the _end_ of the linked list.
		
		//This method is used by the provided put() and
		//rehash() methods, so if those aren't working,
		//look here!
		
		//Worst case: O(n) where n is the number
		//of items in the list, NOT O(m) where m
		//is the number of slots, and NOT O(n+m)


		TableEntry<K,V> newEntry = new TableEntry<>(key, value);
		int location = Math.abs(key.hashCode()) % storage.length;
		if(location == Math.abs(Integer.MIN_VALUE) % storage.length)
		{
			location = location * -1;
		}
		Node<K,V> newNode = new Node<>(newEntry,null);
		V returnV = null;
		if(storage[location] == null)
		{
			storage[location] = newNode;
			numElements++;
		}
		else
		{
			if(storage[location].entry.key.equals(newEntry.key))
			{
				returnV = storage[location].entry.value;
				storage[location].entry.value = newEntry.value;
			}
			else {
				Node<K,V> temp = storage[location];
				while(storage[location] != null && !storage[location].entry.key.equals(newEntry.key))
				{
					storage[location] = storage[location].next;
				}
				if(storage[location] == null)
				{
					storage[location] = temp;
					while(storage[location].next != null)
					{
						storage[location] = storage[location].next;
					}
					storage[location].next = newNode;
					storage[location] = temp;

					numElements++;
				}
				else {
					returnV = storage[location].entry.value;
					storage[location].entry.value = newEntry.value;
				}


			}

		}
		return returnV;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------

	/**
	 * This is the main method used for testing.
	 * @param args not used.
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		ThreeTenHashMap<String,String> st1 = new ThreeTenHashMap<>(10);
		ThreeTenHashMap<String,Integer> st2 = new ThreeTenHashMap<>(5);
		ThreeTenHashMap<Integer, Integer> sp3 = new ThreeTenHashMap<>(5);

		for(int i = 0; i < 10; i++)
		{
			sp3.put(i,i);
		}
		for(int i = 0; i < 10; i++)
		{
			sp3.remove(i);
		}
		sp3.keySet();

		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");

		for(String n: st1.keySet())
		{
			System.out.println(n);
		}

		System.out.println(st1.size());

		if(st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
			System.out.println("Yay 1");
		}
		
		if(st1.getSlots() == 10 && st1.size() == 3 && st1.get("a").equals("apple")) {
			System.out.println("Yay 2");
		}
		
		st2.rehash(1);
		st2.put("a",1);
		st2.put("b",2);

		//System.out.println();
		//System.out.println(st2.toString());
		//System.out.println(st2.toStringDebug());

		if(st2.toString().equals("b:2\na:1") && st2.toStringDebug().equals("[0]: [b:2]->null\n[1]: [a:1]->null")
			&& st2.put("e",3) == null && st2.put("y",4) == null &&
			st2.toString().equals("a:1\ne:3\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[e:3]->[y:4]->null\n[2]: [b:2]->null\n[3]: null")) {
			System.out.println("Yay 3");
		}
		//System.out.println();
		//System.out.println(st2.toString());
		//System.out.println(st2.toStringDebug());
		
		if(st2.remove("e").equals(3) && st2.rehash(8) == true &&
			st2.size() == 3 && st2.getSlots() == 8 &&
			st2.toString().equals("a:1\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[y:4]->null\n[2]: [b:2]->null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: null")) {
			System.out.println("Yay 4");
		}
		
		ThreeTenHashMap<String,String> st3 = new ThreeTenHashMap<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
				System.out.println("Yay 5");
			}
		}
		
		//This is NOT all the testing you need... several methods are not
		//being tested here! Some method return types aren't being tested
		//either. You need to write your own tests!
		
		//Also, try this and see if it works:
		//ThreeTenHashMap<Integer,Integer> st4 = new ThreeTenHashMap<>(10);
		//st4.put(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
	
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write,
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************

	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @return UnsupportedOperationException
	 */
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param m is the map that was not used.
	 */
	public void	putAll(Map<? extends K,? extends V> m) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param value object that was not used.
	 * @return UnsupportedOperationException
	 */
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @return UnsupportedOperationException
	 */
	public Set<Map.Entry<K,V>> entrySet() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param o object that was not used.
	 * @return UnsupportedOperationException
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @return UnsupportedOperationException
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param key object that was not used.
	 * @return UnsupportedOperationException
	 */
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will check these things to make sure they still work, so don't break them.
	//********************************************************************************
	
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT

	/**
	 * This is constructor of the node class for the linked list used in the hashMap.
	 * @param <K> the key that will be set.
	 * @param <V> the value that comes with key.
	 */
	public static class Node<K,V> {
		/**
		 * This is the data in the node.
		 */
		public TableEntry<K,V> entry;
		/**
		 * Pointer to the next node.
		 */
		public Node<K,V> next;
		/**
		 * The node constructor.
		 * @param entry data that will be stored in the node.
		 */
		public Node(TableEntry<K,V> entry) {
			this.entry = entry;
		}

		/**
		 * The second node constructor.
		 * @param entry data that will be stored in the node.
		 * @param next the pointer to the next node.
		 */
		public Node(TableEntry<K,V> entry, Node<K,V> next) {
			this(entry);
			this.next = next;
		}

		/**
		 * This converts the node to String.
		 * @return the String.
		 */
		public String toString() {
			return "[" + entry.toString() + "]->";
		}
	}
	
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT

	/**
	 * This is the tableEntry that will be assigned to the node.
	 * @param <K> the key that will be set.
	 * @param <V> the value that comes with key.
	 */
	public static class TableEntry<K,V> {
		/**
		 * The key in the table.
		 */
		public K key;
		/**
		 * The value that is associate with the key.
		 */
		public V value;

		/**
		 * The constructor in that makes up the key and value.
		 * @param key stores the key in the table.
		 * @param value stores the value with the key.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Converts the key and value to string.
		 * @return the string of the key and value.
		 */
		public String toString() {
			return key.toString()+":"+value.toString();
		}
	}

	/**
	 * this converts the hash map to an array.
	 * @return the array of the hash map.
	 */
	public TableEntry[] toArray(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		TableEntry[] collection = new TableEntry[this.numElements];
		int index = 0;
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null) {
				Node<K,V> curr = storage[i];
				while(curr != null) {
					collection[index] = curr.entry;
					index++;
					curr = curr.next;
				}
			}
		}
		return collection;		
	}

	/**
	 * Converts the hash map into a string.
	 * @return the string of the hash map.
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			if(curr == null) continue;
			
			while(curr != null) {
				s.append(curr.entry.toString());
				s.append("\n");
				curr = curr.next;
			}
		}
		return s.toString().trim();
	}

	/**
	 * This is the string to help debug the hash map.
	 * @return the debugged string.
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			
			s.append("[" + i + "]: ");
			while(curr != null) {
				s.append(curr.toString());
				curr = curr.next;
			}
			s.append("null\n");
		}
		return s.toString().trim();
	}

	/**
	 * This rehashed the storage to whatever size the parameter is.
	 * @param size the size that the storage will be.
	 * @return if the hashing was done.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		if(size < 1) return false;
		
		Node<K,V>[] oldTable = storage;
		storage = (Node<K,V>[]) new Node[size];
		numElements = 0;
		
		for(Node<K,V> node : oldTable) {
			while(node != null) {
				putNoExpand(node.entry.key, node.entry.value);
				node = node.next;
			}
		}
		
		return true;
	}

	/**
	 * this puts the key and value inside the storage of the hash map.
	 * @param key the key that wil be put.
	 * @param value the value that comes with the key.
	 * @return the value that was put in the storage.
	 */
	public V put(K key, V value) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		V ret = putNoExpand(key, value);
		while((numElements/(double)storage.length) >= 2) {
			rehash(storage.length*2);
		}
		return ret;
	}
}