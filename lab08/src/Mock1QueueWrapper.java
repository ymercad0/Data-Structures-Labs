import java.util.EmptyStackException;

public class Mock1QueueWrapper {
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

    /**
     *	The school cafeteria offers circular and square sandwiches at lunch break,
     *	referred to by numbers 0 and 1 respectively.
     *	All students stand in a queue. Each student either prefers square or circular sandwiches.
     *
     *	The number of sandwiches in the cafeteria is equal to the number of students.
     *	The sandwiches are placed in a STACK. At each step:
     *			If the student at the front of the QUEUE prefers the sandwich on the top of the stack, they will take it and leave the queue.
     *			Otherwise, they will leave it and go to the queue's end.
     *	This continues until none of the queue students want to take the top sandwich and are thus unable to eat
     *
     *
     *	You are given two integer arrays students and sandwiches
     *  Where sandwiches[i] is the type of the i​​​​​​th sandwich in the stack (i = 0 is the top of the stack)
     *	and students[j] is the preference of the j​​​​​​th student in the initial queue (j = 0 is the front of the queue).
     *	Return the number of students that are unable to eat.
     *
     *  Hint: pass students & sandwiches to a Queue & a Stack respectively
     *
     *	@param students - Preference of students
     *  @param sandwiches - Types of Sandwiches
     *  @return the number of students that are unable to eat.
     */

    /*
     * Input: students = [1,1,0,0], sandwiches = [0,1,0,1]
     * Output: 0
     * Explanation:
     *	- Front student leaves the top sandwich and returns to the end of the line making students = [1,0,0,1].
     *  - Front student leaves the top sandwich and returns to the end of the line making students = [0,0,1,1].
     *  - Front student takes the top sandwich and leaves the line making students = [0,1,1] and sandwiches = [1,0,1].
     *  - Front student leaves the top sandwich and returns to the end of the line making students = [1,1,0].
     *  - Front student takes the top sandwich and leaves the line making students = [1,0] and sandwiches = [0,1].
     *  - Front student leaves the top sandwich and returns to the end of the line making students = [0,1].
     *  - Front student takes the top sandwich and leaves the line making students = [1] and sandwiches = [1].
     *  - Front student takes the top sandwich and leaves the line making students = [] and sandwiches = [].
     *  Hence all students are able to eat
     *
     *  Input: students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
     *  Output: 3
     */
    public static int countStudents(int[] students, int[] sandwiches) {
        boolean canTake = true;
        DoublyLinkedQueue<Integer> studentsQueue = new DoublyLinkedQueue<>();
        SinglyLinkedStack<Integer> sandwichStack = new SinglyLinkedStack<>();

        for (int student : students) {
            studentsQueue.enqueue(student);
        }

        for(int i = sandwiches.length-1; i >= 0; i--){
            sandwichStack.push(sandwiches[i]);
        }

        while(canTake){
            canTake = false;
            for(int i = 0; i < studentsQueue.size(); i++){
                if(studentsQueue.getFront().equals(sandwichStack.top())){
                    studentsQueue.dequeue();
                    sandwichStack.pop();
                    canTake = true;
                }
                else{
                    studentsQueue.enqueue(studentsQueue.dequeue());
                }
            }
        }

        return studentsQueue.size();
    }

    public static void main(String[] args) {
        System.out.println(countStudents(new int[] {1,1,1,0,0,1}, new int[] {1,0,0,0,1,1}));
    }
}
