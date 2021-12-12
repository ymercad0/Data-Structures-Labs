import java.util.ArrayList;
import java.util.Comparator;


public class Lab10P3Wrapper {

    public static class IntegerComparator1 implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

    }

    public static void makeEmpty(DisplayablePriorityQueue<Integer, String> pq) {
        while (!pq.isEmpty())
            removeMin(pq);
    }

    public static <K, V> void add(DisplayablePriorityQueue<K, V> pq, K key, V value) {
        System.out.println("\nPQ content after adding entry: key = " + key
                + " and value = " + value);
        pq.insert(key, value);
        pq.display();
    }

    public static <K, V> void removeMin(DisplayablePriorityQueue<K, V> pq) {
        System.out.println("\nPQ content after removing highest priority element " + pq.removeMin());
        pq.display();
    }

    public static interface DisplayablePriorityQueue<K, V> extends PriorityQueue<K,V> {
        void display();
    }

    public static interface PriorityQueue<K, V> {
        int size();
        boolean isEmpty();
        Entry<K, V> insert(K key, V value) throws IllegalArgumentException;
        Entry<K, V> min();
        Entry<K, V> removeMin();
    }

    public static interface Entry<K, V> {
        K getKey();
        V getValue();
    }

    public static abstract class AbstractPriorityQueue<K,V> implements DisplayablePriorityQueue<K,V> {

        protected EntryComparator<K,V> entryCmp;  // the comparator object
        /**
         * Notice that here we use a comparator of entries.
         * Such a comparator is constructed based on a key comparator that is
         * received as the parameter of one of the constructors,
         * or based on the default comparator of keys if the default constructor
         * is used to instantiate the priority queue object. This provides
         * flexibility in determining what constitutes "high priority", and
         * allows to implement max-heaps or min-heaps without the need of
         * making changes to the code (see class HeapPriorityQueue).
         */

        /**
         * This constructor receives a comparator that is capable of comparing keys.
         * Based on that comparator of keys, it construct the entry comparator that
         * will be used in different operations of the class.
         *
         * @param cmp the key comparator that will be used
         */
        protected AbstractPriorityQueue(Comparator<K> cmp) {
            this.entryCmp = new EntryComparator<>(cmp);
        }

        /**
         * Returns true if the queue is empty; otherwise, returns false.
         */
        public boolean isEmpty() {
            return this.size() == 0;
        }

        /**
         * Internal method used to compare entries.
         * @param e1
         * @param e2
         * @return
         */
        protected int compare(Entry<K, V> e1, Entry<K, V> e2) {
            return entryCmp.compare(e1, e2);
        }

        protected boolean validate(K key) throws IllegalArgumentException {
            if (key == null) throw new IllegalArgumentException("Key is null.");
            try {
                return entryCmp.compare(new PQEntry<K,V>(key, null), new PQEntry<K,V>(key, null))==0;
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("Key does not match comparator requirements.");
            }
        }

        ///////////////////////////  Internal Classes /////////////////////////////
        /**
         * Entry data type. Objects of this type hold a pair of values: key-value
         * pair.
         *
         * @param <K> data type of the key
         * @param <V> data type of the value
         */
        protected class PQEntry<K, V> implements Entry<K, V> {

            private K key;
            private V value;

            public PQEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public K getKey() {
                // TODO Auto-generated method stub
                return key;
            }

            @Override
            public V getValue() {
                // TODO Auto-generated method stub
                return value;
            }

            public void setKey(K key) {
                this.key = key;
            }

            public void setValue(V value) {
                this.value = value;
            }

            public String toString() {
                return "[" + key + ", " + value + "]";
            }
        }

        /**
         * Default comparator. Presumes that objects to be compared are of type
         * Comparator<E>. Notice that the compare method will throw an exception
         * if the objects to compare are not Comparable.
         *
         * An object of this type is used to implement the EntryComparator
         * object in the cases when the pq instance is created using the
         * default constructor. ...
         *
         * @param <E> The data type of objects to be compared.
         */
        private class DefaultComparator<E> implements Comparator<E> {

            @SuppressWarnings("unchecked")
            @Override
            public int compare(E o1, E o2) throws ClassCastException {
                return ((Comparable<E>) o1).compareTo(o2);
            }
        }


        /**
         * Implementation of comparator of entries. The compare method bases its results
         * on how do the keys in both entries given compare.
         *
         * @author pedroirivera-vega
         *
         * @param <K> Data type of Key
         * @param <V> Data type of value
         */
        private class EntryComparator<K, V> implements Comparator<Entry<K, V>> {
            private Comparator<K> cmp;
            public EntryComparator(Comparator<K> cmp) {
                this.cmp = cmp;
            }

            public int compare(Entry<K, V> e1, Entry<K, V> e2) {
                return cmp.compare(e1.getKey(), e2.getKey());
            }

        }

    }

    public static abstract class AbstractListPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

        protected ArrayList<Entry<K,V>> list;

        protected AbstractListPriorityQueue(Comparator<K> cmp) {
            super(cmp);
            list = new ArrayList<>();
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public Entry<K, V> min() {
            if (this.isEmpty()) return null;
            return list.get(minEntryIndex());
        }

        /**
         * Internal method to find the index of the element in list
         * containing an entry having min key. Subclasses will
         * implement as needed.
         *
         * @return index of the element having min key in list.
         */
        protected abstract int minEntryIndex();

        /**
         * This method is mainly for testing purposes. Elements (entries)
         * are displayed as they are inside the internal list, from first
         * to last; hence, in this case, they they are not seen in any
         * particular order (increasing or decreasing), but just as they
         * are in the list....
         */
        public void display() {
            if (this.isEmpty())
                System.out.println("EMPTY");
            else
                for (Entry<K,V> e : list)
                    System.out.println(e);
        }

        @Override
        public Entry<K, V> removeMin() {
            int index = minEntryIndex();
            if(index != -1)
                return list.remove(index);
            else
                return null;
        }

    }


    public static class UnsortedListPriorityQueue<K, V> extends AbstractListPriorityQueue<K, V> {

        public UnsortedListPriorityQueue(Comparator<K> cmp) {
            super(cmp);
            list = new ArrayList<>();
        }

        /**
         * Internal method to find the index of the element in list
         * containing an entry having min key (based on the comparator)
         * @return index of the element having min key in list.
         */
        protected int minEntryIndex() {
            // internal method - PRE: list is not empty
            // sequentially look for min

            if(isEmpty()) return -1;
            int mpIndex = 0;

            for (int i=0; i < list.size(); i++)
                if (compare(list.get(i), list.get(mpIndex)) < 0)
                    mpIndex = i;
            return mpIndex;

        }

        @Override
        /**
         * Insert as for the unsorted list implementation. Add new entry to the end
         * of the list.
         */
        public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
            super.validate(key);
            Entry<K,V> newest = new PQEntry<K,V>(key, value);
            list.add(newest);
            return newest;
        }

    }

}
