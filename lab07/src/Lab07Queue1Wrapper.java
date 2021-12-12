public class Lab07Queue1Wrapper {

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

    public static Queue<String> binaryNumberSequence(int N){
        DoublyLinkedQueue<String> generator = new DoublyLinkedQueue<>();
        DoublyLinkedQueue<String> results = new DoublyLinkedQueue<>();
        generator.enqueue("1");
        String front = "";

        while(N > 0){
            front = generator.dequeue(); //dequeue from the front and generate the next string (add 0 then add 1)
            results.enqueue(front);
            generator.enqueue(front + "0"); //generate the next two binary numbers at a time
            generator.enqueue(front + "1");
            N--;
        }
        return results;

    }
}