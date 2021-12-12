import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class Lab11P1Wrapper {

    public static interface List<E> extends Iterable<E> {

        public int size();
        public boolean isEmpty();
        public boolean isMember(E e);
        public int firstIndexOf(E e);
        public int lastIndexOf(E e);
        public void add(E e);
        public void add(E e, int index);
        public E get(int index);
        public E remove(int index);
        public boolean remove(E e);
        public int removeAll(E e);
        public E replace(int index, E newElement);
        public void clear();
        public Object[] toArray();
    }

    public static interface Queue<E> {
        public int size();
        public boolean isEmpty();
        public E front();
        public E dequeue();
        public void enqueue(E e);
        public void makeEmpty();
        public void print(PrintStream P);
    }

    public static interface Pair<T, E> {
        public T getFirst();
        public E getSecond();
        public boolean equals(Pair<T,E> p);
    }

    public static class OrderedPair<T, E> implements Pair<T, E> {

        private T first;
        private E second;

        public OrderedPair(T first, E second) {
            super();
            this.first = first;
            this.second = second;
        }

        @Override
        public T getFirst() {
            return this.first;
        }

        @Override
        public E getSecond() {
            return this.second;
        }

        @Override
        public boolean equals(Pair<T, E> p) {
            return this.getFirst().equals(p.getFirst()) &&
                    this.getSecond().equals(p.getSecond());
        }

        @Override
        public String toString() {
            return "(" + this.first + "," + this.second + ")";
        }

    }

    public static class DoublyLinkedQueue<E> implements Queue<E> {

        private static class Node<E>{
            private E element;
            private Node<E> next;
            private Node<E> prev;

            public Node() {
                this.element = null;
                this.next = this.prev = null;

            }
            public E getElement() {
                return element;
            }
            public void setElement(E element) {
                this.element = element;
            }
            public Node<E> getNext() {
                return next;
            }
            public void setNext(Node<E> next) {
                this.next = next;
            }
            public Node<E> getPrev() {
                return prev;
            }
            public void setPrev(Node<E> prev) {
                this.prev = prev;
            }

        }

        private Node<E> header;
        private Node<E> tail;
        private int currentSize;

        public DoublyLinkedQueue() {
            this.currentSize = 0;
            this.header = new Node<>();
            this.tail = new Node<>();

            this.header.setNext(this.tail);
            this.tail.setPrev(this.header);
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
        public E front() {
            return (this.isEmpty() ? null : this.header.getNext().getElement());
        }

        @Override
        public E dequeue() {
            if (this.isEmpty()) {
                return null;
            }
            else {
                Node<E> target = null;
                target = this.header.getNext();
                E result = target.getElement();
                this.header.setNext(target.getNext());
                target.getNext().setPrev(this.header);
                target.setNext(null);
                target.setPrev(null);
                target.setElement(null);
                this.currentSize--;
                return result;
            }
        }

        @Override
        public void enqueue(E e) {
            Node<E> newNode = new Node<E>();
            newNode.setElement(e);
            newNode.setNext(this.tail);
            newNode.setPrev(this.tail.getPrev());
            this.tail.setPrev(newNode);
            newNode.getPrev().setNext(newNode);
            this.currentSize++;
        }

        @Override
        public void makeEmpty() {
            while (this.dequeue() != null);

        }

        @Override
        public void print(PrintStream P) {
            Node<E> temp = this.header.getNext();
            while (temp != this.tail) {
                P.println(temp.getElement());
                temp = temp.getNext();
            }

        }

    }

    /**
     * You are given a positive integer X in which you can only perform 3 operations.
     * You can only subtract one from X, divide X by 2, or divide X by 3.
     * Design an algorithm that finds the SHORTEST PATH from X to 1 by only performing these 3
     * operations
     *
     * NOTE: What should happen when X is not a multiple of 2 or 3?
     * HINT: The emphasis is on SHORTEST PATH, what algorithm is useful for this?
     * HINT: Use Java's HashSet to keep track of how many transformations of X you have performed
     *
     * @param X - Input Number
     * @return The number of operations performed to convert X to 1
     */
    public static int shortestPathToOne(int X) {
        DoublyLinkedQueue<OrderedPair<Integer, Integer>> Q = new DoublyLinkedQueue<>(); //integer, steps taken as weight
        HashSet<Integer> visited = new HashSet<>();
        int moves = -1;

        Q.enqueue(new OrderedPair<>(X,0));
        while(!Q.isEmpty()){
            OrderedPair<Integer, Integer> node = Q.dequeue();

            if(node.first == 1){ //lowest amount of moves to get to 1
                moves = node.second;
                break;
            }

            if(visited.contains(node.first)) continue; //already checked a node
            visited.add(node.first); //visited history

            if(node.first % 3 == 0){ //increase the number of moves
                Q.enqueue(new OrderedPair<>(node.first/3, node.second+1));
            }

            if(node.first % 2 == 0){
                Q.enqueue(new OrderedPair<>(node.first/2, node.second+1));
            }

            Q.enqueue(new OrderedPair<>(node.first-1, node.second+1));

        }
        return moves;
    }

}