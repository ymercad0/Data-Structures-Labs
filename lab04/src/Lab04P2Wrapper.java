import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Lab04P2Wrapper {

    public static interface List<E> extends Iterable<E> {

        public int size();
        public boolean isEmpty();
        public boolean isMember(E e);
        public int firstIndexOf(E e);
        public int lastIndexOf(E e);
        public void add(E e);
        public void add(E e, int position);
        public E get(int position);
        public E remove(int position);
        public E replace(int position, E newElement);
        public void clear();
        public List<E> reverse();

        //DO NOT REMOVE THESE, TESTS WILL FAIL
        public Object[] toArray();
        public boolean equals(List<E> l2);
    }

    @SuppressWarnings("unchecked")

    public static class ArrayList<E> implements List<E> {
        @SuppressWarnings("hiding")
        private class ArrayListIterator<E> implements Iterator<E> {
            private int currentPosition;
            public ArrayListIterator() {
                super();
                this.currentPosition = 0;
            }
            @Override
            public boolean hasNext() {
                return this.currentPosition < currentSize;
            }
            @Override
            public E next() {
                if (this.hasNext()) {
                    E result = (E) elements[this.currentPosition++]; // elements is array in enclosing class
                    return result;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        }

        private E[] elements;
        private int currentSize;
        private static final int DEFAULT_SIZE = 10;

        public ArrayList(int initialSize) {
            if (initialSize < 1) {
                throw new IllegalArgumentException("Size must be at least 1.");
            }
            this.elements = (E[]) new Object[initialSize];
            this.currentSize = 0;
        }

        public ArrayList() {
            this(DEFAULT_SIZE);
        }

        @Override
        public int size() {
            return this.currentSize;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public boolean isMember(E e) {
            return this.firstIndexOf(e) >= 0;
        }

        @Override
        public int firstIndexOf(E e) {
            for (int i=0; i < this.size(); ++i) {
                if (this.elements[i].equals(e)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void add(E e) {
            if (this.size() == this.elements.length) {
                this.reAllocate();
            }
            this.elements[this.currentSize++]  = e;
        }

        private void reAllocate() {
            E[] temp = (E[]) new Object[2*this.size()];
            for (int i=0; i < this.size(); ++i) {
                temp[i] = this.elements[i];
            }
            this.elements = temp;
        }

        @Override
        public void add(E e, int position) {
            if ((position < 0) || (position > this.currentSize)){
                throw new IndexOutOfBoundsException("Illegal position");
            }
            if (position == this.currentSize) {
                this.add(e);
            }
            else {
                if (this.size() == this.elements.length) {
                    this.reAllocate();
                }
                for (int i=this.currentSize; i > position; --i) {
                    this.elements[i] = this.elements[i-1];
                }
                this.elements[position] = e;
                this.currentSize++;
            }
        }

        @Override
        public E get(int position) {
            if ((position < 0) || (position >= this.currentSize)) {
                throw new IndexOutOfBoundsException("Illegal position");
            }
            return this.elements[position];
        }

        @Override
        public E remove(int position) {
            if ((position < 0) || (position >= this.currentSize)) {
                throw new IndexOutOfBoundsException("Illegal position");
            }
            E result = this.elements[position];

            for (int i=position; i < this.size() - 1; ++i) {
                this.elements[i] = this.elements[i + 1];
            }
            this.elements[this.currentSize-1] = null;
            this.currentSize--;
            return result;
        }

        @Override
        public E replace(int position, E newElement) {
            if ((position < 0) || (position >= this.currentSize)) {
                throw new IndexOutOfBoundsException("Illegal position");
            }
            E result = this.elements[position];
            this.elements[position] = newElement;
            return result;
        }

        @Override
        public void clear() {
            while(!this.isEmpty()) {
                this.remove(0);
            }
        }

        @Override
        public Object[] toArray() {
            Object[] result = (E[]) new Object[this.size()];
            System.arraycopy(this.elements, 0, result, 0, this.size());
//			for (int i=0; i < this.size(); ++i) {
//				result[i] = this.elements[i];
//			}
            return result;
        }

        @Override
        public boolean equals(List<E> l2) {
            E[] targetList = (E[]) this.toArray();
            E[] secondList = (E[]) l2.toArray();

            return Arrays.equals(targetList, secondList);
        }

        @Override
        public Iterator<E> iterator() {
            return new ArrayListIterator<E>();
        }

        @Override
        public int lastIndexOf(E e) {
            for (int i=this.currentSize-1; i>= 0; --i) {
                if (this.elements[i].equals(e)) {
                    return i;
                }
            }
            // not found
            return -1;
        }

        /**
         * Consider a member method reverse() for the List<E> ADT
         * that returns new List with the elements in reversed order.
         *
         * For example if a List L = {Bob, Mel, Ron, Jil, Ron},
         * then a call to L.reverse() will return a new list M = {Ron, Jil, Ron, Mel, Bob}.
         *
         * The method returns an empty list if the list L is empty.
         *
         * @param None
         * @return - The target list in reversed order
         */
        @Override
        public List<E> reverse() {
            ArrayList<E> arr = new ArrayList<>();
            for(int i = this.currentSize-1; i >= 0; i--){
                arr.add(elements[i]);
            }
            return arr;
        }

        public static void main(String[] args) {
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(1);
            arr.add(2);
            arr.add(3);

            List<Integer> newArr = arr.reverse();
            for (int i = 0; i < newArr.size(); i++) {
                System.out.println(newArr.get(i));
            }

            for (int i = 0; i < newArr.size(); i++) {
                System.out.println(arr.get(i));
            }


        }

    }
}