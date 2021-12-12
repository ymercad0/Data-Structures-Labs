import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class Lab10P5Wrapper {

    public static interface Set<E> extends Iterable<E> {
        public boolean add(E obj);
        public boolean isMember(E obj);
        public boolean remove(E obj);
        public boolean isEmpty();
        public int size();
        public void clear();
        public Set<E> union(Set<E> S2);
        public Set<E> difference(Set<E> S2);
        public Set<E> intersection(Set<E> S2);
        public boolean isSubSet(Set<E> S2);
    }

    public static interface Map<K,V> {
        public int size();
        public boolean isEmpty();
        public V get(K key);
        public void put(K key, V value);
        public V remove(K key);
        public boolean containsKey(K key);
        public void clear();
        public List<K> getKeys();
        public List<V> getValues();
        public void print(PrintStream out);
    }

    public interface HashFunction<K> {

        int hashCode(K key);
    }

    public static interface List<E> extends Iterable<E> {

        public void add(E obj);
        public void add(int index, E obj);
        public boolean remove(E obj);
        public boolean remove(int index);
        public int removeAll(E obj);
        public E get(int index);
        public E set(int index, E obj);
        public E first();
        public E last();
        public int firstIndex(E obj);
        public int lastIndex(E obj);
        public int size();
        public boolean isEmpty();
        public boolean contains(E obj);
        public void clear();
    }

    public static class SinglyLinkedList<E> implements List<E> {

        private class Node {
            private E value;
            private Node next;

            public Node(E value, Node next) {
                this.value = value;
                this.next = next;
            }

            public Node(E value) {
                this(value, null); // Delegate to other constructor
            }

            public Node() {
                this(null, null); // Delegate to other constructor
            }

            public E getValue() {
                return value;
            }

            public void setValue(E value) {
                this.value = value;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

            public void clear() {
                value = null;
                next = null;
            }
        } // End of Node class


        private class ListIterator implements Iterator<E> {

            private Node nextNode;

            public ListIterator() {
                nextNode = header.getNext();
            }

            @Override
            public boolean hasNext() {
                return nextNode != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E val = nextNode.getValue();
                    nextNode = nextNode.getNext();
                    return val;
                }
                else
                    throw new NoSuchElementException();
            }

        } // End of ListIterator class


        // private fields
        private Node header;
        private int currentSize;


        public SinglyLinkedList() {
            header = new Node();
            currentSize = 0;
        }

        @Override
        public Iterator<E> iterator() {
            return new ListIterator();
        }

        @Override
        public void add(E obj) {
            Node curNode, newNode;
            // Need to find the last node
            for (curNode = header; curNode.getNext() != null; curNode = curNode.getNext());
            // Now curNode is the last node
            // Create a new Node and make curNode point to it
            newNode = new Node(obj);
            curNode.setNext(newNode);
            currentSize++;
        }

        @Override
        public void add(int index, E obj) {
            Node curNode, newNode;

            // First confirm index is a valid position
            // We allow for index == size() and delegate to add(object).
            if (index < 0 || index > size())
                throw new IndexOutOfBoundsException();
            if (index == size())
                add(obj); // Use our "append" method
            else {
                // Get predecessor node (at position index - 1)
                curNode = get_node(index - 1);
                // The new node must be inserted between curNode and curNode's next
                // Note that if index = 0, curNode will be header node
                newNode = new Node(obj, curNode.getNext());
                curNode.setNext(newNode);
                currentSize++;
            }
        }

        @Override
        public boolean remove(E obj) {
            Node curNode = header;
            Node nextNode = curNode.getNext();

            // Traverse the list until we find the element or we reach the end
            while (nextNode != null && !nextNode.getValue().equals(obj)) {
                curNode = nextNode;
                nextNode = nextNode.getNext();
            }

            // Need to check if we found it
            if (nextNode != null) { // Found it!
                // If we have A -> B -> C and want to remove B, make A point to C
                curNode.setNext(nextNode.getNext());
                nextNode.clear(); // free up resources
                currentSize--;
                return true;
            }
            else
                return false;
        }

        @Override
        public boolean remove(int index) {
            Node curNode, rmNode;
            // First confirm index is a valid position
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            curNode = get_node(index - 1);
            rmNode = curNode.getNext();
            // If we have A -> B -> C and want to remove B, make A point to C
            curNode.setNext(rmNode.getNext());
            rmNode.clear();
            currentSize--;

            return true;
        }

        /* Private method to return the node at position index */
        private Node get_node(int index) {
            Node curNode;
		
			/* First confirm index is a valid position
			   Allow -1 so that header node may be returned */
            if (index < -1 || index >= size())
                throw new IndexOutOfBoundsException();
            curNode = header;
            // Since first node is pos 0, let header be position -1
            for (int curPos = -1; curPos < index; curPos++)
                curNode = curNode.getNext();
            return curNode;
        }

        @Override
        public int removeAll(E obj) {
            int counter = 0;
            Node curNode = header;
            Node nextNode = curNode.getNext();

            /* We used the following in ArrayList, and it would also work here,
             * but it would have running time of O(n^2).
             *
             * while (remove(obj))
             * 		counter++;
             */

            // Traverse the entire list
            while (nextNode != null) {
                if (nextNode.getValue().equals(obj)) { // Remove it!
                    curNode.setNext(nextNode.getNext());
                    nextNode.clear();
                    currentSize--;
                    counter++;
					/* Node that was pointed to by nextNode no longer exists
					   so reset it such that it's still the node after curNode */
                    nextNode = curNode.getNext();
                }
                else {
                    curNode = nextNode;
                    nextNode = nextNode.getNext();
                }
            }
            return counter;
        }

        @Override
        public E get(int index) {
            // get_node allows for index to be -1, but we don't want get to allow that
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            return get_node(index).getValue();
        }

        @Override
        public E set(int index, E obj) {
            // get_node allows for index to be -1, but we don't want set to allow that
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            Node theNode = get_node(index);
            E theValue = theNode.getValue();
            theNode.setValue(obj);
            return theValue;
        }

        @Override
        public E first() {
            return get(0);
        }

        @Override
        public E last() {
            return get(size()-1);
        }

        @Override
        public int firstIndex(E obj) {
            Node curNode = header.getNext();
            int curPos = 0;
            // Traverse the list until we find the element or we reach the end
            while (curNode != null && !curNode.getValue().equals(obj)) {
                curPos++;
                curNode = curNode.getNext();
            }
            if (curNode != null)
                return curPos;
            else
                return -1;
        }

        @Override
        public int lastIndex(E obj) {
            int curPos = 0, lastPos = -1;
            // Traverse the list
            for (Node curNode = header.getNext(); curNode != null; curNode = curNode.getNext()) {
                if (curNode.getValue().equals(obj))
                    lastPos = curPos;
                curPos++;
            }
            return lastPos;
        }

        @Override
        public int size() {
            return currentSize;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public boolean contains(E obj) {
            return firstIndex(obj) != -1;
        }

        @Override
        public void clear() {
            // Avoid throwing an exception if the list is already empty
            while (size() > 0)
                remove(0);
        }
    }

    public static class SimpleHashFunction<K> implements HashFunction<K> {

        @Override
        public int hashCode(K key) {
            String temp = key.toString();
            int result = 0;
            for (int i = 0; i < temp.length(); i++)
                result += temp.charAt(i);
            return result;
        }

    }

    public static class HashTableSC<K, V> implements Map<K, V> {

        /**
         * The values in the linked lists within our buckets will be of this type.
         * @author Juan O. Lopez
         */
        private class BucketNode<K, V> {
            private K key;
            private V value;

            public BucketNode(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }
        }


        // private fields
        private int currentSize;
        private List<BucketNode<K, V>>[] buckets;
        private HashFunction<K> hashFunction;
        /*ADD LOAD FACTOR CONSTANT HERE*/
        private static final double loadFactor = 0.75;

        @SuppressWarnings("unchecked")
        public HashTableSC(int initialCapacity, HashFunction<K> hashFunction) {
            if (initialCapacity < 1)
                throw new IllegalArgumentException("Capacity must be at least 1");
            if (hashFunction == null)
                throw new IllegalArgumentException("Hash function cannot be null");

            currentSize = 0;
            this.hashFunction = hashFunction;
            buckets = new SinglyLinkedList[initialCapacity];
            for (int i = 0; i < initialCapacity; i++)
                buckets[i] = new SinglyLinkedList<BucketNode<K, V>>();

        }

        @Override
        public V get(K key) {
            if (key == null)
                throw new IllegalArgumentException("Parameter cannot be null.");

            /* First we determine the bucket corresponding to this key */
            int targetBucket = hashFunction.hashCode(key) % buckets.length;
            /* Within that bucket there is a linked list, since we're using Separate Chaining */
            List<BucketNode<K, V>> L = buckets[targetBucket];
            /* Look for the key within the nodes of that linked list */
            for (BucketNode<K, V> BN : L) {
                if (BN.getKey().equals(key)) // Found it!
                    return BN.getValue();
            }

            return null; // Did not find it
        }

        @Override
        public void put(K key, V value) {
            if (key == null || value == null)
                throw new IllegalArgumentException("Parameter cannot be null.");
            /* Can't have two elements with same key,
             * so remove existing element with the given key (if any) */
            remove(key);

            /*TODO What needs to be added here???*/
            if((double)(this.size()/buckets.length) >= loadFactor){
                System.out.println("REHASHING NEEDED"); //ADD THIS PRINT BEFORE CALLING rehash(), TESTS WILL FAIL IF NOT ADDED
                rehash();
            }


            System.out.println("Adding Element with key: " + key);

            /* Determine the bucket corresponding to this key */
            int targetBucket = hashFunction.hashCode(key) % buckets.length;
            /* Within that bucket there is a linked list, since we're using Separate Chaining */
            List<BucketNode<K, V>> L = buckets[targetBucket];
            /* Finally, add the key/value to the linked list */
            L.add(0, new BucketNode<K, V>(key, value));
            currentSize++;
        }

        @Override
        public V remove(K key) {
            if (key == null)
                throw new IllegalArgumentException("Parameter cannot be null.");

            /* First we determine the bucket corresponding to this key */
            int targetBucket = hashFunction.hashCode(key) % buckets.length;
            /* Within that bucket there is a linked list, since we're using Separate Chaining */
            List<BucketNode<K, V>> L = buckets[targetBucket];
            /* Iterate over linked list trying to find this the key */
            int pos = 0;
            for (BucketNode<K, V> BN : L) {
                if (BN.getKey().equals(key)) { // Found it!
                    L.remove(pos);
                    currentSize--;
                    return BN.getValue();
                }
                else
                    pos++;
            }
            return null;
        }

        @Override
        public boolean containsKey(K key) {
            return get(key) != null;
        }

        @Override
        public List<K> getKeys() {
            List<K> result = new SinglyLinkedList<K>();
            /* For each bucket in the hash table, get the keys in that linked list */
            for (int i = 0; i < buckets.length; i++)
                for (BucketNode<K, V> BN : buckets[i])
                    result.add(0, BN.getKey());
            return result;
        }

        @Override
        public List<V> getValues() {
            List<V> result = new SinglyLinkedList<V>();
            /* For each bucket in the hash table, get the values in that linked list */
            for (int i = 0; i < buckets.length; i++)
                for (BucketNode<K, V> BN : buckets[i])
                    result.add(0, BN.getValue());
            return result;
        }

        @Override
        public int size() {
            return currentSize;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public void clear() {
            currentSize = 0;
            for (int i = 0; i < buckets.length; i++)
                buckets[i].clear();
        }

        @Override
        public void print(PrintStream out) {
            /* For each bucket in the hash table, print the elements in that linked list */
            for (int i = 0; i < buckets.length; i++)
                for (BucketNode<K, V> BN : buckets[i])
                    out.printf("(%s, %s)\n", BN.getKey(), BN.getValue());
        }


        /**
         * TODO Bring your implementation of rehash to this implementation as well
         */
        @SuppressWarnings("unchecked")
        private void rehash() {
            List<BucketNode<K, V>>[] newBuckets = new SinglyLinkedList[buckets.length*2];

            for(int i = 0; i < newBuckets.length; i++){ //initialize as empty singly linked lists
                newBuckets[i] = new SinglyLinkedList<BucketNode<K, V>>();
            }

            for (List<BucketNode<K, V>> list : buckets) { //get each linked list
                for (int j = 0; j < list.size(); j++) { //rehash every element in that list
                    BucketNode<K, V> node = list.get(j); //each node in each singly linked list
                    int newBucketIndex = hashFunction.hashCode(node.getKey()) % newBuckets.length; //new hash index
                    newBuckets[newBucketIndex].add(node);
                }

            }

            buckets = newBuckets; //new rehashed bucket list
        }
    }


    /**
     * Custom Implementation of a HashSet
     * This version of HashSet is backed by a Separate Chaining Hash Table
     * @author Fernando J. Bermudez - bermed28
     *
     * @param <E>
     */
    public static class HashSet<E> implements Set<E> {
        //Private Fields
        private Map<E, Object> hashtable; //We use Object as a dummy value, elements will be the keys
        private static final int DEFAULT_SET_SIZE = 10;

        /*Constructs an empty HashSet with a given initial capacity*/
        public HashSet(int initialCapacity) {
            hashtable = new HashTableSC<>(initialCapacity, new SimpleHashFunction<>());
        }

        /*Constructs an empty HashSet with a default initial capacity of ten*/
        public HashSet() {
            hashtable = new HashTableSC<>(DEFAULT_SET_SIZE, new SimpleHashFunction<>());
        }

        @Override
        public Iterator<E> iterator() {
            return hashtable.getKeys().iterator();
        }

        /**
         * Takes a given object that is not already in the set
         * and adds it to the HashSet according to the hashCode generated for the object
         *
         * @param obj - Object to be added to set
         * @return true or false, depending if the object was added to the set
         */
        @Override
        public boolean add(E obj) {
            if(isMember(obj)) return false;
            hashtable.put(obj, obj);
            return true;
        }

        /**
         * Returns true if the given object is a member (is included) of the HashSet
         * @param obj - object to verify if is in set
         */
        @Override
        public boolean isMember(E obj) {
            return hashtable.get(obj) != null;
        }

        /**
         * Removes a given object from the set and returns true if the object was actually removed or not
         * @param obj - object to remove
         */
        @Override
        public boolean remove(E obj) {
            if(!isMember(obj)) return false; //can't remove an object not in the set
            hashtable.remove(obj);
            return true;
        }


        /**
         * Checks to see if set is empty
         * @return true or false if the set is empty or not
         */
        @Override
        public boolean isEmpty() {
            return hashtable.size() == 0;
        }

        /**
         * @return size of HashSet
         */
        @Override
        public int size() {
            return hashtable.size();
        }

        /**
         * Removes everything from HashSet until it is empty
         */
        @Override
        public void clear() {
            hashtable.clear();
        }

        /**
         * Returns a set with the all the elements from the target set and a given S2 combined
         * @param S2 - the second set
         * @return S3 - The set with the combined elements from the target set and S2
         */
        @Override
        public Set<E> union(Set<E> S2) {
            Set<E> res = new HashSet<>(this.size()+S2.size());
            //size affects rehashing, in the worse case, you add all elems from both sets

            for(E elem : this){
                res.add(elem);
            }

            for(E elem2 : S2){
                res.add(elem2);
            }
            return res;
        }

        /**
         * Returns a set with the non-common elements from the target set and a given S2
         * @param S2 - the second set
         * @return S3 - The set with the non-common elements from the target set and S2
         */
        @Override
        public Set<E> difference(Set<E> S2) {
            Set<E> res = new HashSet<>();

            for (E elem : this) {
                if (!S2.isMember(elem)) { //obj in set A but not in B
                    res.add(elem);
                }
            }
            return res;
        }

        /**
         * Returns a set with the common elements from the target set and a given S2
         * @param S2 - the second set
         * @return S3 - The set with the common elements from the target set and S2
         */
        @Override
        public Set<E> intersection(Set<E> S2) {
            Set<E> res = new HashSet<>();

            for (E elem : this) {
                if (S2.isMember(elem)) { //obj in S1
                    res.add(elem);
                }
            }
            return res;
        }

        /**
         * Returns true if S2 is a subset of the target set, otherwise it returns false
         * @param S2 - the set to compare with
         */
        @Override
        public boolean isSubSet(Set<E> S2) { //if all elements in S2 are in S1
            for(E elem : S2){
                if(!this.isMember(elem)) return false;
            }
            return true;
        }

    }

    public static void printSet(Set<Integer> s) {
        int i = 0;
        System.out.println("Size: " + s.size()); //Test size()
        if(s.isEmpty()) System.out.println("Set is Empty");
        for (Integer integer : s) {
            System.out.println("Element " + i + ": " + integer);
            i++;
        }
    }

    public static void main(String[] args) {
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> hashSet3 = new HashSet<>();

        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3); //Should Trigger rehash()
        hashSet.add(4);
        hashSet.add(5); //Should Trigger rehash()

        hashSet.remove(4);
        hashSet.remove(5);

        hashSet3.add(1);
        hashSet3.add(7);
        hashSet3.add(4);
        hashSet3.add(1); //Should return false when trying to add (run debugger to test if true)
        hashSet3.add(3);

        System.out.println("Intersection of hashSet and hashSet3 is: \n");
        Set<Integer> intersection2 = hashSet.intersection(hashSet3); //Should be 1,3,4
        System.out.println(intersection2.size());


        System.out.println("Done!");


        System.out.println((int)5/2);
    }

}