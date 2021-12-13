import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Mock1List2Wrapper {

    public static interface List<E> extends Iterable<E>{
        public void add(E elm);
        public void add(int index, E elm);
        public boolean remove(E elm);
        public E remove(int index);
        public int removeAll(E elm);
        public boolean contains(E elm);
        public E get(int index);
        public E set(int index, E elm);
        public E first();
        public E last();
        public int firstIndexOf(E elm);
        public int lastIndexOf(E elm);
        public int size();
        public boolean isEmpty();
        public void clear();
        public Object[] toArray();
        public void mergeTwoSortedLists(List<E> l2);
    }

    public static class SinglyLinkedList<E extends Comparable<E>> implements List<E>{

        private class Node<E>{
            private E element;
            private Node<E> next;

            public Node(E elm, Node<E> next) {this.element = elm; this.next = next;}
            public Node(E elm) {this(elm, null);}
            public Node() {this(null, null);}

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

            public void clear() {
                this.element = null;
                this.next = null;
            }
        } //End of Node class

        private Node<E> header;
        private int currentSize;

        public SinglyLinkedList() {
            this.header = new Node<>();
            this.currentSize = 0;
        }

        private class ListIterator implements Iterator<E>{

            Node<E> curNode;

            public ListIterator() {
                curNode = header.getNext();
            }

            @Override
            public boolean hasNext() {
                return curNode != null;
            }

            @Override
            public E next() {
                if(hasNext()) {
                    E elm = curNode.getElement();
                    curNode = curNode.getNext();
                    return elm;
                } else {
                    throw new NoSuchElementException();
                }


            }

        }

        @Override
        public Iterator<E> iterator() {
            return new ListIterator();
        }

        @Override
        public void add(E elm) {
            if (this.isEmpty()) {
                header.setNext(new Node<E>(elm));
                currentSize++;
            } else {
                Node<E> curNode;
                for(curNode = header.getNext(); curNode.getNext() != null; curNode = curNode.getNext());
                Node<E> newNode = new Node<>(elm);
                curNode.setNext(newNode);
                currentSize++;
            }
        }

        @Override
        public void add(int index, E elm) {
            if (index == currentSize) this.add(elm);
            else {
                Node<E> prevNode;

                if (index == 0) prevNode = this.header;
                else prevNode = getNode(index - 1);

                Node<E> newNode = new Node<>(elm, prevNode.getNext());
                prevNode.setNext(newNode);
                this.currentSize++;
            }

        }

        @Override
        public boolean remove(E elm) {
            for(Node<E> curNode = header.getNext(); curNode.getNext() != null; curNode = curNode.getNext()) {
                Node<E> nextNode = curNode.getNext();
                if(nextNode.getElement().equals(elm)) {
                    curNode.setNext(nextNode.getNext());
                    nextNode.clear();
                    nextNode = null;
                }
            }
            return false;
        }

        @Override
        public E remove(int index) {
            if ((index < 0) || (index >= this.currentSize)){
                throw new IndexOutOfBoundsException();
            }
            else {
                Node<E> temp = this.header;
                int currentPosition =0;
                Node<E> target = null;

                while (currentPosition != index) {
                    temp = temp.getNext();
                    currentPosition++;
                }
                E result = null;
                target = temp.getNext();
                result = target.getElement();
                temp.setNext(target.getNext());
                target.setElement(null);
                target.setNext(null);
                this.currentSize--;
                return result;

            }

        }

        @Override
        public int removeAll(E elm) {
            int copiesRemoved = 0;
            while(!remove(elm)) copiesRemoved++;
            return copiesRemoved;
        }

        @Override
        public boolean contains(E elm) {
            return firstIndexOf(elm) >= 0;
        }

        @Override
        public E get(int index) {
            return getNode(index).getElement();
        }

        private Node<E> getNode(int index){
            if(index < 0 || index >= size())
                throw new IndexOutOfBoundsException();

            Node<E> target = header.getNext();
            for(int i = 0; i < index; i++, target = target.getNext());
            return target;
        }

        @Override
        public E set(int index, E elm) {
            Node<E> node = getNode(index);
            E element = node.getElement();
            node.setElement(elm);
            return element;
        }

        @Override
        public E first() {
            return header.getNext().getElement();
        }

        @Override
        public E last() {
            return get(size() - 1);
        }

        @Override
        public int firstIndexOf(E elm) {
            int i = 0;
            for(Node<E> curNode = header.getNext(); curNode != null; curNode = curNode.getNext(), i++)
                if(curNode.getElement().equals(elm)) return i;

            return -1;
        }

        @Override
        public int lastIndexOf(E elm) {
            int curPos = 0;
            int result = -1;
            for(Node<E> curNode = header.getNext(); curNode != null; curNode = curNode.getNext(), curPos++)
                if(curNode.getElement().equals(elm)) result = curPos;

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
            while(!isEmpty()) {
                remove(0);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public Integer[] toArray() {
            Integer[] result = new Integer[this.size()];
            int i = 0;
            for(Node<E> curNode = header.getNext(); curNode != null; curNode = curNode.getNext(), i++) {
                result[i] = (Integer) curNode.getElement();
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        private void sort() {
            Integer[] elms = toArray();
            Arrays.sort(elms);
            clear();
            for (Integer object : elms) {
                add((E) object);
            }
        }

        /**
         * Write a member method that merges the target list with the list given as parameter.
         * You can assume that the target list will be sorted by calling the sort() method.
         * The method should modify the original target list such that it has both lists sorted.
         *
         * If L1 = {2,4,1,3,5} and L2 = {6,7,8,9,10}, then a call to L1.mergeTwoSortedLists(L2)
         * modifies L1 such that L1 = {1,2,3,4,5,6,7,8,9,10}
         *
         * Hint - You can compare elements using the .compareTo() method:
         *
         * Example: If you have elm1 and elm2, elm1.compareTo(elm2) returns the following values:
         * 			1) 0, if elm1 == elm2
         * 			2) 1, if elm1 > elm2
         * 			3) -1, if elm1 < elm2
         *
         * @param l2 - List to merge the target list with
         */
        @Override
        public void mergeTwoSortedLists(List<E> l2){
            sort(); //DO NOT REMOVE THIS METHOD
            /*ADD YOUR CODE HERE*/
            int i = 0;
            int j = 0;
            while(j < l2.size()){ //use l2s size because l1 is constantly increasing

                try{
                    this.get(i+1);
                }

                catch (IndexOutOfBoundsException e){
                    this.add(i+1, l2.get(j));
                    j++;
                    continue;
                }

                if((this.get(i).compareTo(l2.get(j)) < 0 && this.get(i+1).compareTo(l2.get(j)) > 0)){  //a < b, a > b
                    this.add(i+1, l2.get(j));
                    j++;
                }

                i++;
            }

        }
        public static void main(String[] args) {
            SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
            SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
            l1.add(0);
            l1.add(3);
            l1.add(6);
            l1.add(7);

            l2.add(1);
            l2.add(2);
            l2.add(8);
            l2.add(9);

            l1.mergeTwoSortedLists(l2);
            Integer arr[] = l1.toArray();
            System.out.println(Arrays.toString(arr));

        }
    }

}