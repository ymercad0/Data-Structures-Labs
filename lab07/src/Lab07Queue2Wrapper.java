import java.io.PrintStream;

public class Lab07Queue2Wrapper {

    public static class EmptyQueueException extends RuntimeException {
        public EmptyQueueException(String message) {
            super(message);
        }
    }

    public static interface Queue<E> {
        int size();
        boolean isEmpty();
        E getFront() throws EmptyQueueException;
        void enqueue(E element);
        E dequeue() throws EmptyQueueException;
        void clear();
    }

    public static class DoublyLinkedQueue<E> implements Queue<E> {

        private static class Node<E> {
            private E element;
            private Node<E> next, prev;


            public Node(E elm, Node<E> nextNode, Node<E> prevNode) {
                this.element = elm;
                this.next = nextNode;
                this.prev = prevNode;
            }

            public Node(E elm, Node<E> next) {
                this(elm, next, null);
            }

            public Node(E elm) {
                this(elm, null, null);
            }

            public Node() {
                this(null, null, null);
            }

            public E getElement() {
                return this.element;
            }

            public Node<E> getNext(){
                return this.next;
            }

            public void setNext(Node<E> next) {
                this.next = next;
            }

            public Node<E> getPrev(){
                return this.prev;
            }

            public void setPrev(Node<E> prev) {
                this.prev = prev;
            }

            public void setElement(E elm) {
                this.element = elm;
            }

            public void clear() {
                this.element = null;
                this.next = null;
                this.prev = null;
            }
        } // END CLASS NODE

        private Node<E> header, trailer;   // references to first and last node
        private int currentSize;

        public DoublyLinkedQueue() {         // initializes instance as empty queue
            header = new Node<>(null, trailer, null);
            trailer = new Node<>(null, null, header);
            currentSize = 0;
        }
        public int size() {
            return currentSize;
        }
        public boolean isEmpty() {
            return size() == 0;
        }

        public E getFront() throws EmptyQueueException {
            if (isEmpty())
                throw new EmptyQueueException("Queue is empty");
            return header.getNext().getElement();
        }

        public E dequeue() throws EmptyQueueException {
            if (this.isEmpty()) {
                return null;
            }
            else {
                Node<E> target = this.header.getNext();
                E result = getFront();

                header.setNext(target.getNext());
                target.getNext().setPrev(header);

                target.clear();
                target = null;

                this.currentSize--;
                return result;
            }
        }

        public void enqueue(E e) {
            Node<E> newNode = new Node<E>(e, trailer, trailer.getPrev());
            this.trailer.setPrev(newNode);
            newNode.getPrev().setNext(newNode);
            this.currentSize++;
        }

        public void clear() {
            while(!this.isEmpty())
                this.dequeue();
        }
    }
    /*
     * Write a non-member method named bottomQueue() that receives as parameters a queue Q
     * and a integer N. The method returns last N elements in the queue Q. After completion,
     * the queue Q must be left as it was at the begining of the function. The method returns
     * an empty queue is Q has less than N elements.
     * For example if Q = {Joe, Ned, Amy, Bob, Kim, Ron}, with Joe at the head, then
     * bottomQueue(Q, 3), returns the queue {Bob, Kim, Ron}.
     */
    public static Queue<String> bottomQueue(Queue<String> Q , int N){
        DoublyLinkedQueue<String> queue = new DoublyLinkedQueue<>();
        if(Q.size() < N) {return queue;}

        int index = Q.size()-N; //starting index of the last N elements
        int toQueue = N;
        N = 0;

        while(N < index){
            Q.enqueue(Q.dequeue()); //passes elements from the front to the rear to preserve order
            N++;
        }

        while(toQueue > 0){
            queue.enqueue(Q.getFront());
            Q.enqueue(Q.dequeue());
            toQueue--;
        }

        return queue;
    }


}