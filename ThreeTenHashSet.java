import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

//You need to add javadocs to this class.
//You need to submit this file for grading.
//If you don't submit this for grading we will use
//a vanialla version which doesn't have javadocs.
//This will mean that your code won't pass the style checker.

//Remember that for interface methods with existing documentation
//(such as the java.util.Set interface), the documentation is already
//written, you just need to resuse that (don't copy-and-paste it,
//use inheritdoc).

/**
 * This is the hashset class.
 * @param <E> generic type that will be used.
 */
class ThreeTenHashSet<E> implements Set<E> {
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write.
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************

	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param c collection that was not passed.
	 * @return UnsupportedOperationException.
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param c collection that was not passed.
	 * @return UnsupportedOperationException.
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param a array that was not passed.
	 * @param <T> the generic type that was not passed.
	 * @return UnsupportedOperationException
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param c the collection that was not passed
	 * @return UnsupportedOperationException.
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param c the collection that was not passed
	 * @return UnsupportedOperationException.
	 */
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @param o the object that was not passed
	 * @return UnsupportedOperationException.
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Did not use so throws a UnsupportedOperationException.
	 * @return UnsupportedOperationException.
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will grade these methods to make sure they still work, so don't break them.
	//********************************************************************************
	/**
	 * This is the storage that will be used to store the set.
	 */
	private ThreeTenHashMap<E,E> storage = new ThreeTenHashMap<>(5);

	/**
	 * This adds the parameter into the hashset.
	 * @param e the element that was added into the hashset.
	 * @return nothing.
	 */
	public boolean add(E e) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.put(e, e) == null;
	}

	/**
	 * This method clears the Hashset.
	 */
	public void clear() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		storage = new ThreeTenHashMap<>(5);
	}

	/**
	 * This method checks if the object is inside the hashset.
	 * @param o is the object that is being looked for in the hashset.
	 * @return true if the object is inside the hashset.
	 */
	public boolean contains(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.get(o) != null;
	}

	/**
	 * Checks if the hashset is empty.
	 * @return true if empty/ false otherwise.
	 */
	public boolean isEmpty() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return size() == 0;
	}

	/**
	 * This removes the object from the hashset.
	 * @param o the object being removed.
	 * @return if the object was removed or not.
	 */
	public boolean remove(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.remove(o) != null;
	}

	/**
	 * Checks for size.
	 * @return the size of the storage.
	 */
	public int size() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.size();
	}

	/**
	 * Changes the hashset to an array.
	 * @return the array of the hashset.
	 */
	public Object[] toArray() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		ThreeTenHashMap.TableEntry[] arr = storage.toArray();
		Object[] ret = new Object[arr.length];
		for(int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].key;
		}
		return ret;
	}

	/**
	 * changes the storage to string.
	 * @return the storage as string.
	 */
	public String toString(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.toString();
	}

	/**
	 * This is the iterator that iterators through the hashset.
	 * @return an iterator.
	 */
	public Iterator<E> iterator() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return new Iterator<>() {
			private Object[] values = toArray();
			private int currentLoc = 0;
			
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) values[currentLoc++];
			}
			
			public boolean hasNext() {
				return currentLoc != values.length;
			}
		};
	}
}