import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Mock1StackWrapper {

    public static interface Stack<E> {
        public void push(E newEntry);
        public E pop();
        public E top();
        public boolean isEmpty();
        public int size();
        public void clear();
    }

    public static class SinglyLinkedStack<E> implements Stack<E> {

        private static class Node<E> {
            private E element;
            private Node<E> next;

            public Node(E elm, Node<E> next) {
                this.element = elm;
                this.next = next;
            }

            public Node(E data) {
                this(data, null);
            }

            public Node() {
                this(null, null);
            }

            public E getElement() {
                return element;
            }

            public Node<E> getNext() {
                return next;
            }


            public void setElement(E elm) {
                this.element = elm;
            }

            public void setNext(Node<E> next) {
                this.next = next;
            }

            public void clear() {  // no need to return data
                element = null;
                next = null;
            }

        }

        // instance variables for the stack object

        private Node<E> header; //Note that this is NOT a dummy header
        private int currentSize;

        public SinglyLinkedStack() {
            header = new Node<>();
            currentSize = 0;
        }

        @Override
        public void push(E newEntry) {
            Node<E> nNode = new Node<>(newEntry, header.getNext());
            header.setNext(nNode);
            currentSize++;
        }

        @Override
        public E pop() {
            E etr = top();
            Node<E> ntr = header.getNext();
            header.setNext(ntr.getNext());
            currentSize--;
            ntr.clear();
            ntr = null;
            return etr;
        }

        @Override
        public E top() {
            if (isEmpty())
                throw new EmptyStackException();
            return header.getNext().getElement();
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public int size() {
            return currentSize;
        }

        @Override
        public void clear() {
            while (size() > 0) pop();
        }

    }

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

        public Object[] toArray();

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
            return result;
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

    }

    /**
     *  Given an array target and an integer n.
     *  In each iteration, you will read a number from list = {1,2,3..., n}.
     *
     *  Build the target array using the following operations:
     * 		Push: Read a new element from the beginning list, and push it in the array.
     * 		Pop: delete the last element of the array.
     *
     *  If the target array is already built, stop reading more elements.
     *  Return the operations to build the target array.
     *
     *  Example 1:
     *  Input: target = [1,3], n = 3
     *  Output: ["Push","Push","Pop","Push"]
     *  Explanation:
     *  	Read number 1 and automatically push in the array -> [1]
     *  	Read number 2 and automatically push in the array then Pop it -> [1]
     *  	Read number 3 and automatically push in the array -> [1,3]
     *
     *  Example 2:
     *  Input: target = [1,2,3], n = 3
     *  Output: ["Push","Push","Push"]
     *
     *  Example 3:
     *  Input: target = [1,2], n = 4
     *  Output: ["Push","Push"]
     *  Explanation: You only need to read the first 2 numbers and stop.
     *
     *  Example 4:
     *  Input: target = [2,3,4], n = 4
     *  Output: ["Push","Pop","Push","Push","Push"]
     *
     */
    public static List<String> buildArray(int[] target, int n) {
        ArrayList<String> sol = new ArrayList<>();
        int j = 0;
        int i = 0;
        while(i+1 <= target[target.length-1]){
            sol.add("Push");
            if(target[j] == i+1)
                j++;

            else if(target[j] != i+1)
                sol.add("Pop");

            i++;
        }
        return sol;
    }

    public static void main(String[] args) {
        List<String> sol;
        sol = buildArray(new int []{2,3, 4}, 4);

        Object[] arr = sol.toArray();
        System.out.println(Arrays.toString(arr));
    }

}