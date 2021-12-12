import java.util.Arrays;
public class Lab07DequeWrapper {

    public static interface Deque<E> {
        public void addFirst(E elm);
        public void addLast(E elm);
        public E removeFirst();
        public E removeLast();
        public E getFirst();
        public E getLast();
        public boolean removeFirstOccurrence(E elm);
        public boolean removeLastOccurrence(E elm);
        public int size();
        public boolean isEmpty();

        //DO NOT USE THIS IN EXERCISES
        public String[] toArray(); //DO NOT REMOVE, TEST WILL FAIL
    }

    /**
     * Implementation of Deque ADT using a Circular Doubly-Linked Queue
     * @author Fernando J. Bermudez & Juan O. Lopez Gerena
     * @param <E>
     */
    public static class CircularDoublyLinkedQueque<E> implements Deque<E>{

        private class Node<E>{
            private E element;
            private Node<E> next, prev;

            public Node(E elm, Node<E> next, Node<E> prev) {
                this.element = elm;
                this.next = next;
                this.prev = prev;
            }
            public Node(E elm, Node<E> next) {this(elm, next, null);}

            public Node(E elm) {this(elm, null, null);}

            public Node() {this(null, null, null);}

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
            public void clear() {
                next = null;
                prev = null;
                element = null;
            }
        } //End of Node Class



        private Node<E> header;
        private int currentSize;

        public CircularDoublyLinkedQueque() {
            header = new Node<>(null, header, header);
            currentSize = 0;
        }

        @Override
        public void addFirst(E elm) {
            Node<E> toAdd;
            if(currentSize > 0){
                toAdd = new Node<>(elm, header.next, header);
                toAdd.next.prev = toAdd;
                header.next = toAdd;
                currentSize++;
                return;
            }
            toAdd = new Node<>(elm, header, header); //no elements in the list
            header.next = toAdd;
            header.prev = toAdd;
            currentSize++;

        }

        @Override
        public void addLast(E elm) {
            Node<E> toAdd;
            if(currentSize >  0){
                toAdd = new Node<>(elm, header, header.prev); //last elem loops around to header
                header.prev = toAdd;
                toAdd.prev.next = toAdd; //sets the previous to point to the last
            }

            else{
                toAdd = new Node<>(elm, header, header); //no elements in the list
                header.next = toAdd;
                header.prev = toAdd;
            }

            currentSize++;
        }

        @Override
        public E removeFirst() {
            if(currentSize == 0) return null;

            Node<E> node = header.next;
            E value = node.getElement();
            header.next = node.next;
            node.clear();

            currentSize--;
            return value;
        }

        @Override
        public E removeLast() {
            if(currentSize == 0) return null;

            Node<E> node = header.prev;
            E value = node.getElement();
            header.prev = node.prev;
            node.prev.next = header;
            node.clear();

            currentSize--;
            return value;
        }

        @Override
        public E getFirst() {
            if(currentSize == 0) return null;
            return header.next.getElement();
        }

        @Override
        public E getLast() {
            if(currentSize == 0) return null;
            return header.prev.getElement();
        }

        @Override
        public boolean removeFirstOccurrence(E elm) {
            if(currentSize == 0) return false;

            Node<E> node = header.next;
            while(node != header){
                if(node.getElement().equals(elm)){
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.clear();
                    currentSize--;
                    return true;
                }
                node = node.next;
            }

            return false;
        }

        @Override
        public boolean removeLastOccurrence(E elm) {
            if(currentSize == 0) return false;

            Node<E> node = header.next;
            Node<E> toDelete = header;
            while(node != header){
                if(node.getElement().equals(elm)){
                    toDelete = node;
                }
                node = node.next;
            }
            if(toDelete != header){
                toDelete.prev.next = toDelete.next;
                toDelete.next.prev = toDelete.prev;
                toDelete.clear();
                currentSize--;
                return true;
            }

            return false;
        }

        @Override
        public int size() {
            return currentSize;
        }

        @Override
        public boolean isEmpty() {
            return currentSize == 0;
        }

        //DO NOT USE THIS IN EXERCISES
        //DO NOT DELETE, TESTS WILL FAILS
        @Override
        public String[] toArray() {
            String[] arr = new String[size()];

            Node<E> curNode = header.getNext();
            for (int i = 0; curNode != header; curNode = curNode.getNext(), i++) {
                arr[i] = (String) curNode.getElement();
            }
            return arr;
        }
    }

    public static void main(String[] args) {
        CircularDoublyLinkedQueque<String> queue = new CircularDoublyLinkedQueque<>();

        queue.addLast("Susan");
        queue.addLast("John");
        queue.addLast("Xavier");
        queue.addLast("Sora");
        queue.addLast("John");

        System.out.println("Removed Last Ocurrence: " + queue.removeLastOccurrence("Susan"));
        System.out.println("Is empty: " + queue.isEmpty());
        System.out.println("Size: " + queue.size());
        System.out.println("First Element: " + queue.getFirst());
        System.out.println("Last Element: " + queue.getLast());
        //print
        String[] arr = queue.toArray();
        System.out.println(Arrays.toString(arr));

    }
}