import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class Lab10P2Wrapper {

    public static void add(Heap<Integer> t, Integer e) {
        System.out.println("\nHeap content after adding element " + e);
        t.add(e);
        t.display();
    }

    public static void removeMin(Heap<Integer> t) {
        System.out.println("\nHeap content after removing element " + t.removeMin());
        t.display();
    }

    public static class IntegerComparator1 implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

    }

    public static interface Position<E> {
        E getElement();
    }

    public static interface BinaryTree<E> extends Tree<E> {
        boolean hasLeft(Position<E> p) throws IllegalArgumentException;
        boolean hasRight(Position<E> p) throws IllegalArgumentException;
        Position<E> left(Position<E> p) throws IllegalArgumentException;
        Position<E> right(Position<E> p) throws IllegalArgumentException;
        Position<E> sibling(Position<E> p) throws IllegalArgumentException;
    }

    public static interface Tree<E> extends Iterable<E> {
        Position<E> root();
        Position<E> parent(Position<E> p) throws IllegalArgumentException;
        Iterable<Position<E>> children(Position<E> p) throws
                IllegalArgumentException;
        int numChildren(Position<E> p) throws IllegalArgumentException;
        boolean isInternal(Position<E> p) throws IllegalArgumentException;
        boolean isExternal(Position<E> p) throws IllegalArgumentException;
        boolean isRoot(Position<E> p) throws IllegalArgumentException;
        int size();
        boolean isEmpty();
        Iterator<E> iterator();
        Iterable<Position<E>> positions();
    }

    public static abstract class AbstractTree<E> implements Tree<E> {

        @Override
        public boolean isInternal(Position<E> p) throws IllegalArgumentException {
            return this.numChildren(p) > 0;
        }

        @Override
        public boolean isExternal(Position<E> p) throws IllegalArgumentException {
            return this.numChildren(p) == 0;
        }

        @Override
        public boolean isRoot(Position<E> p) throws IllegalArgumentException {
            return this.parent(p) == null;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        /**
         * Returns Iterator object of elements in the tree,
         * and based on inorder traversal. Notice that this is
         * based on the Iterable<Position<E>> object produced by
         * method positions()
         */
        public Iterator<E> iterator() {
            ArrayList<Position<E>> pList = new ArrayList<>();
            fillIterable(root(), pList);

            ArrayList<E> eList = new ArrayList<>();
            for (Position<E> p : pList)
                eList.add(p.getElement());
            return eList.iterator();
        }

        @Override
        /**
         * Produces an iterable of positions in the tree based on
         * postorder traversal.
         */
        public Iterable<Position<E>> positions() {
            ArrayList<Position<E>> pList = new ArrayList<Position<E>>();
            fillIterable(root(), pList);
            return pList;
        }

        /**
         * Method to fill the Iterable<Position<E>> object by properly traversing
         * the positions in the tree. Final version is decided at the particular
         * type of tree - general, binary, etc.
         *
         * The default method, as implemented here, generates an Iterable<Position<E>>
         * object based on PREORDER.
         * @param r
         * @param pList
         */
        protected void fillIterable(Position<E> r, ArrayList<Position<E>> pList) {
            if (r != null) {
                pList.add(r);
                for (Position<E> p : children(r))
                    fillIterable(p, pList);
            }
        }

        public void display() {                                              //
            final int MAXHEIGHT = 100;                                       //
            int[] control = new int[MAXHEIGHT];                              //
            control[0]=0;                                                    //
            if (!this.isEmpty())                                             //
                recDisplay(this.root(), control, 0);                         //
            else                                                             //
                System.out.println("Tree is empty.");                        //
        }                                                                    //
        //
        // Auxiliary Method to support display                               //
        protected void recDisplay(Position<E> root,                          //
                                  int[] control, int level)                                    //
        {                                                                    //
            printPrefix(level, control);                                     //
            System.out.println();                                            //
            printPrefix(level, control);                                     //
            System.out.println("__("+root.getElement()+")");                 //
            control[level]--;                                                //
            int nc = this.numChildren(root);                                 //
            control[level+1] = nc;                                           //
            for (Position<E>  p : this.children(root)) {                     //
                recDisplay(p, control, level+1);                             //
            }                                                                //
        }                                                                    //
        //
        // Auxiliary method to support display                               //
        protected void printPrefix(int level, int[] control) {        //
            for (int i=0; i<=level; i++)                                     //
                if (control[i] <= 0)                                         //
                    System.out.print("    ");                                //
                else                                                         //
                    System.out.print("   |");                                //
        }
    }

    public static abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

        @Override
        public Iterable<Position<E>> children(Position<E> p)
                throws IllegalArgumentException {
            ArrayList<Position<E>> list = new ArrayList<>();
            if (this.hasLeft(p)) list.add(this.left(p));
            if (this.hasRight(p)) list.add(this.right(p));

            return list;
        }

        @Override
        public int numChildren(Position<E> p) throws IllegalArgumentException {
            int count = 0;
            if (this.hasLeft(p)) count++;
            if (this.hasRight(p)) count++;
            return count;
        }

        @Override
        public boolean hasLeft(Position<E> p) throws IllegalArgumentException {
            return this.left(p) != null;
        }

        @Override
        public boolean hasRight(Position<E> p) throws IllegalArgumentException {
            return this.right(p) != null;
        }

        @Override
        public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
            Position<E> parent = this.parent(p);
            if (parent == null)
                return null;
            if (this.left(parent) == p)
                return this.right(parent);
            return this.left(parent);
        }

        /**/
        // internal method to construct the Iterable<Position<E>> object.
        // based on inorder traversal.
        protected void fillIterable(Position<E> r, ArrayList<Position<E>> pList) {
            if (hasLeft(r))
                fillIterable(left(r), pList);
            pList.add(r);
            if (hasRight(r))
                fillIterable(right(r), pList);
        }
        /**/


        protected void recDisplay(Position<E> r,
                                  int[] control, int level)
        {
            printPrefix(level, control);
            System.out.println();
            printPrefix(level, control);
            Position<E> parent = parent(r);
            if (parent != null)
                if (left(parent) == r)
                    System.out.println("__L("+r.getElement()+")");
                else
                    System.out.println("__R("+r.getElement()+")");
            else
                System.out.println("__ROOT("+r.getElement()+")");
            control[level]--;
            int nc = this.numChildren(r);
            control[level+1] = nc;
            for (Position<E>  p : this.children(r)) {
                recDisplay(p, control, level+1);
            }
        }
    }

    public static class CompleteBinaryTree<E> extends AbstractBinaryTree<E> {

        protected ArrayList<CBTPosition<E>> list;   // hold the positions in the tree

        public CompleteBinaryTree() {
            list = new ArrayList<>();
        }

        protected CBTPosition<E> validate(Position<E> p) throws IllegalArgumentException {
            if (!(p instanceof CBTPosition))
                throw new IllegalArgumentException("Invalid position - not of type HeapPosition.");
            CBTPosition<E> hp = (CBTPosition<E>) p;
            if (hp.getIndex() < 0 || hp.getIndex() >= list.size())
                throw new IllegalArgumentException("Position does not belong to this heap.");
            return hp;
        }

        // The following three methods are used to determine the index of the
        // left child, the right child, and the parent of a node whose index is
        // given (parameter i). Notice that the names of each of these methods
        // are overloaded....... with corresponding methods having parameter of
        // type Position<>.
        private int left(int i) {
            return 2*i+1;
        }

        private int right(int i) {
            return 2*i+2;
        }

        private int parent(int i) {
            return ((i-1)/2);
        }
        ///////////////////////////////////////////////////////////////////////

        @Override
        /**
         * left method of BinaryTree ADT
         */
        public Position<E> left(Position<E> p) throws IllegalArgumentException {
            CBTPosition<E> hp = validate(p);

            int leftIndex = left(hp.getIndex());  // index of left child location in the array

            if (leftIndex < list.size())          // check of p has left child
                return list.get(leftIndex);

            return null;
        }

        @Override
        /**
         * right method of BinaryTree ADT
         */
        public Position<E> right(Position<E> p) throws IllegalArgumentException {
            CBTPosition<E> hp = validate(p);

            int rightIndex = right(hp.getIndex()); // index of right child location in the array

            if (rightIndex < list.size())          // checking if p has right child
                return list.get(rightIndex);

            return null;
        }

        @Override
        /**
         * root() method of BinaryTree
         */
        public Position<E> root() {
            if (list.isEmpty())
                return null;
            return list.get(0);
        }

        @Override
        /**
         * parent method of BinaryTree
         */
        public Position<E> parent(Position<E> p) throws IllegalArgumentException {
            CBTPosition<E> hp = validate(p);

            if (hp.getIndex() == 0)    // if p is the root, return null (no parent)
                return null;

            return list.get(parent(hp.getIndex()));  // parent is at location hp.getIndex()
        }

        @Override
        /**
         * size method of BinaryTree
         */
        public int size() {
            return list.size();
        }

        @Override
        /**
         * iterator method of BinaryTree - based on breadth-first-search traversal
         */
        public Iterator<E> iterator() {
            // this is really a bfs iterator
            ArrayList<E> iterList = new ArrayList<>();
            for (Position<E> p : list)
                iterList.add(p.getElement());

            return iterList.iterator();

        }

        @Override
        /**
         * positions method of BinaryTree - based on breadth-first-search traversal
         */
        public Iterable<Position<E>> positions() {
            ArrayList<Position<E>> iterList = new ArrayList<>();
            for (Position<E> p : list)
                iterList.add(p);

            return iterList;
        }

        /**
         * Method to add a new element to the complete binary tree.
         * The new element will be added as a new leaf (the next possible
         * for the tree to continue satisfying the complete binary tree
         * property). In this implementation, that translates to add
         * the new position at the end of the internal list.
         * @param element
         */
        public Position<E> add(E element) {
            CBTPosition<E> hp = new CBTPosition<>(element, list.size());
            list.add(hp);    // add the new position to location list.size() in array
            return hp;
        }

        // Private class implementing the type of positions being used in this
        // implementation. This type of position is specialized for implementations
        // in which the positions are stored in an array or arraylist.
        @SuppressWarnings("hiding")
        protected class CBTPosition<E> implements Position<E> {

            private E element;   // the element at this position
            private int index;   // its position in the array list
            // the previous index is needed because no operation of Tree
            // uses indexes; those that refer to a tree location, use a position.
            // Hence to implement such methods, the implementation needs to
            // know what location in the arraylist is where that position is stored.

            public CBTPosition(E element, int index) {
                this.element = element;
                this.index = index;
            }

            @Override
            public E getElement() {
                return element;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public void setElement(E element) {
                this.element = element;
            }

        }
    }

    public static class Heap<E> extends CompleteBinaryTree<E> {

        private Comparator<E> cmp;        // heap insertion is based on this comparator

        public Heap(Comparator<E> cmp) {
            this.cmp = cmp;
        }



        /**
         * Method to add a new element to the heap.
         * Adds a new element to the heap. The heap is assumed to be a min-heap.
         * Always add the new element at the first position available in the tree.
         * Just add to the position list.size() in the array list. After the new
         * element is added, apply upheap to guarantee that the current content
         * (which is a heap), plus the new element, continue satisfying the heap
         * property.
         * @param element
         */
        public Position<E> add(E element) {
            CBTPosition<E> p = (CBTPosition<E>) super.add(element);    // append to the list (at super)
            upHeap(p);      // do up-heap as necessary from that position.
            return p;
        }

        /**
         * Returns minimum element in the heap. That element is at location 0
         * in the array or arraylist.
         * @return reference to the minimum element.
         */
        public E min() {
            if (list.isEmpty())
                return null;
            return list.get(0).getElement();    // min element is at position 0
        }


        /**
         * Same effect as min() but it also removes that element from the pq.
         * @return
         */
        public E removeMin() {
            if (list.isEmpty())
                return null;

            CBTPosition<E> ptr = (CBTPosition<E>) list.get(0);

            if (list.size() > 1) {
                list.set(0, list.remove(list.size()-1));
                ((CBTPosition<E>) list.get(0)).setIndex(0);
                downHeap((CBTPosition<E>) list.get(0));
            }
            else
                list.remove(0);

            return ptr.getElement();
        }

        /**
         * Does downheap of r in subtree having root r
         * @param r
         */
        private void downHeap(CBTPosition<E> r) {
            if (this.hasLeft(r)) { //gets smallest child between left and right subtrees
                CBTPosition<E> minChild = (CBTPosition<E>) this.left(r);
                if (this.hasRight(r)) {
                    CBTPosition<E> rChild = (CBTPosition<E>) this.right(r);
                    if (cmp.compare(minChild.getElement(), this.right(r).getElement()) > 0) //left child > right child
                        minChild = rChild;
                }

                if(cmp.compare(r.getElement(), minChild.getElement()) > 0){ //swap root with the smallest child
                    swapPositionsInList(r, minChild);
                    downHeap(r);
                }

            }
        }

        /**
         * Moves upward, as needed, in the path from p to root, the element at p
         * with the goal of maintaining the heep property after the new element
         * is added. Potentially, this goes upward, all the way from p to root,
         * or until the value initially in p reached a level in which the key
         * at its parent is less than the key initially at p.
         * @param p
         */
        private void upHeap(CBTPosition<E> p) {
            if (!this.isRoot(p)) {
                CBTPosition<E> parent = (CBTPosition<E>) this.parent(p);
                if (cmp.compare(p.getElement(), parent.getElement()) < 0) {
                    swapPositionsInList(p, parent);    // p is now parent of parent....
                    upHeap(p);
                }
            }
        }

        /**
         * Interchanges two position in the array.
         *
         * @param r one of the position
         * @param c the other position
         */
        private void swapPositionsInList(CBTPosition<E> r, CBTPosition<E> c) {
            int ir = r.getIndex();
            int ic = c.getIndex();
            r.setIndex(ic);         // since position change location, indexes are changed too
            c.setIndex(ir);

            // swap content of location ir and ic in the arraylist
            list.set(ir, list.set(ic, r));   // swaps elements at positions ir and ic in list

        }
    }
}
