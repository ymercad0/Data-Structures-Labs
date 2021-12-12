import java.util.ArrayList;
import java.util.List;


public class OnlineWrapperClass3 {
    // Comparator class
    public static interface Comparator<E> {
        int compare(E a, E b);

    }

    public static class DefaultComparator<K> implements Comparator<K> {

        @SuppressWarnings("unchecked")
        @Override
        public int compare(K a, K b) {
            return ((Comparable<K>) a).compareTo(b);
        }
    }

    // Entry in a Priority Queue
    public static interface Entry<K, V> {

        public K getKey();

        public V getValue();

    }

    public static interface PriorityQueue<K, V> {

        public int size();

        public boolean isEmpty();

        public Entry<K,V> insert(K key, V value);

        public Entry<K,V> min();

        public Entry<K,V> removeMin();

    }

    public static abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {

        protected static class PQEntry<K,V> implements Entry<K,V> {

            private K key;
            private V value;



            public PQEntry(K key, V value) {
                super();
                this.key = key;
                this.value = value;
            }

            @Override
            public K getKey() {
                return this.key;
            }

            @Override
            public V getValue() {
                return this.value;
            }

            @Override
            public String toString() {
                return "(" + key + "," + value + ")";
            }


        }

        protected AbstractPriorityQueue(Comparator<K> comparator) {
            this.comparator = comparator;
        }
        protected AbstractPriorityQueue() {
            this.comparator = new DefaultComparator<K>();
        }
        protected Comparator<K> comparator;

        protected int compare(K k1, K k2) {
            return this.comparator.compare(k1, k2);
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }



    }

    public static class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

        private List<Entry<K,V>> heap ;



        public HeapPriorityQueue() {
            super();
            this.heap = new ArrayList<Entry<K,V>>();
        }

        public HeapPriorityQueue(Comparator<K> comparator) {
            super(comparator);
            this.heap = new ArrayList<Entry<K,V>>();
        }

        @Override
        public int size() {
            return this.heap.size();
        }

        @Override
        public Entry<K, V> insert(K key, V value) {
            Entry<K,V> newEntry = new PQEntry<K,V>(key, value);
            this.heap.add(newEntry);
            this.upheap(this.size() - 1);
            return newEntry;
        }



        @Override
        public Entry<K, V> min() {
            if (this.isEmpty()) {
                return null;
            }
            else {
                return this.heap.get(0);
            }
        }

        @Override
        public Entry<K, V> removeMin() {
            if (this.isEmpty()) {
                return null;
            }
            else {
                Entry<K,V> result = this.min();
                int target = this.size()-1;
                this.swap(0, target);
                this.heap.remove(target);
                this.downheap(0);
                return result;

            }
        }



        private int leftChild(int n) {
            return 2*n+1;
        }

        private int rightChild(int n) {
            return 2*n+2;
        }

        private int parent(int n) {
            return (n-1)/2;
        }

        private boolean hasLeft(int n) {
            return this.leftChild(n) < this.heap.size();
        }

        private boolean hasRight(int n) {
            return this.rightChild(n) < this.heap.size();
        }

        private void swap(int i, int j) {
            Entry<K,V> temp = this.heap.get(i);
            this.heap.set(i, this.heap.get(j));
            this.heap.set(j, temp);
        }

        private void upheap(int i) {
            int p;
            while (i > 0) {
                p = this.parent(i);
                if (this.compare(this.heap.get(i).getKey(), this.heap.get(p).getKey()) >= 0) {
                    break;
                }
                else {
                    this.swap(i, p);
                    i = p;
                }
            }
        }

        private void downheap(int i) {
            int leftIndex, rightIndex, smallestIndex;
            while (this.hasLeft(i)) {
                leftIndex = this.leftChild(i);
                smallestIndex = leftIndex;
                if (this.hasRight(i)) {
                    rightIndex = this.rightChild(i);
                    K key1 = this.heap.get(leftIndex).getKey();
                    K key2 = this.heap.get(rightIndex).getKey();
                    if (this.compare(key1, key2) > 0) {
                        smallestIndex = rightIndex;
                    }
                }
                if (this.compare(this.heap.get(smallestIndex).getKey(),
                        this.heap.get(i).getKey()) >=0) {
                    break;
                }
                else {
                    this.swap(i, smallestIndex);
                    i = smallestIndex;
                }
            }
        }

        @SuppressWarnings("unchecked")
        public HeapPriorityQueue<K, V> clone(){
            HeapPriorityQueue<K, V> result = new HeapPriorityQueue<K, V>();
            ArrayList<Entry<K,V>> copy = (ArrayList<Entry<K,V>>) this.heap;
            result.heap = (List<Entry<K, V>>) copy.clone();
            return result;
        }
    }


    public static class PriorityQueueClient<K,V> { //implemented as an array list
        public PriorityQueueClient(){

        }
        public  List<Entry<K,V>> range(K key1, K key2, PriorityQueue<K,V> P, Comparator<K> comp){
            // Sets up result
            List<Entry<K,V>> L = new ArrayList<Entry<K,V>>();
            // Create C, a copy of priority queue P
            HeapPriorityQueue<K, V> C = ((HeapPriorityQueue<K, V>) P).clone();

            boolean found1 = false;
            boolean found2 = false;
            while(!C.isEmpty()){
                Entry<K,V> elem = C.removeMin();

                if(elem.getKey() == key1){
                    found1 = true;
                }

                if(elem.getKey() == key2){
                    found2 = true;
                }

                if(comp.compare(elem.getKey(), key1) >= 0 && comp.compare(elem.getKey(), key2) < 0){
                    L.add(elem);
                }
            }

            if(!found1 || !found2) return new ArrayList<Entry<K,V>>();
            return L;
        }

    }
}
