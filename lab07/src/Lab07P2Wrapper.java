import java.util.EmptyStackException;
import java.util.Iterator;


public class Lab07P2Wrapper {

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
	 * Implement a non-member method for the Stack ADT called stackSort. 
	 * The function takes as a parameter an array of integers and returns the array sorted in increasing order.
	 * 
	 * For example consider we have an array A = {9, 11, 15, 11, 1, -1, 3, 11}
	 * In order to sort values, we will use two stacks which will be called the left and right stacks. 
	 * The values in the stacks will be sorted (in non-descending order) and the values in the left stack 
	 * will all be less than or equal to the values in the right stack. 
	 * 
	 * The following example illustrates a possible state for our two stacks:
	 * 
	 * 				left		right
	 * 				[  ]		[  ]
	 * 				[  ]		[ 9]
	 * 				[ 3]		[11]
	 * 				[ 1]		[11]
	 * 				[-1]		[15]
	 * 
	 * Notice that the values in the left stack are sorted so that the smallest value is at the bottom of the stack. 
	 * The values in the right stack are sorted so that the smallest value is at the top of the stack. 
	 * If we read the values up the left stack and then down the right stack, we get:
	 * 			A = {-1, 1, 3, 9, 11, 11, 11, 15}
	 * which is in sorted order.
	 * 
	 * 
	 * Consider the following cases, using the example shown above as a point of reference, to help you design your algorithm:
	 * 		1) If we were to insert the value 5, it could be added on top of either stack and the collection would remain sorted. 
	 * 		   What other values, besides 5, could be inserted in the  example without having to move any values?
	 * 
	 * 		2) If we were to insert the value 0, some values must be moved from the left stack to the right stack before we could actually insert 0. 
	 * 		   How many values must actually be moved?
	 * 
	 *		3) If we were to insert the value 11, first some values must be moved from the right stack to the left stack. 
	 *		   How many values must actually be moved?
	 *		   What condition should we use to determine if enough values have been moved in either of the previous two cases?
	 *		   
	 * YOU MUST USE TWO STACKS, IMPLEMENTATIONS THAT USE Arrays.sort(); 
	 * OR ANY SORTING ALGORITHM (BubbleSort, SelectionSort, etc.) WILL NOT BE GIVEN CREDIT
	 * 
	 * @param array
	 * @return Sorted array using two stacks
	 */
	public static int[] stackSort(int[] array) {
		if(array.length < 1){return array;}
		SinglyLinkedStack<Integer> left = new SinglyLinkedStack<>();
		SinglyLinkedStack<Integer> right = new SinglyLinkedStack<>();
		int numPopped = 0;
		left.push(array[0]); 
		for(int i = 1;  i < array.length; i++) {
			if(!left.isEmpty() && array[i] > left.top()) {
				while(!right.isEmpty() && right.top() < array[i]) { //transfer to the other stack
					left.push(right.pop());
					numPopped++;
				}
				right.push(array[i]); //push element at the bottom
				if(numPopped != 0) {
					while(numPopped > 0) {
						right.push(left.pop());
						numPopped--;
					}
				}
			}
			
			else if(!left.isEmpty() && array[i] < left.top()){ //edge case
			    while(!left.isEmpty() && array[i] < left.top()) {
					right.push(left.pop());
					numPopped++;
				}
				left.push(array[i]);
				if(numPopped != 0) {
					while(numPopped > 0) {
						left.push(right.pop());
						numPopped--;
					}
				}
			}
			
			else if(!right.isEmpty() && array[i] <= right.top()) {
				while(!left.isEmpty() && left.top() > array[i]) {
					right.push(left.pop());
					numPopped++;
				}
				left.push(array[i]);
				if(numPopped != 0) {
					while(numPopped > 0) {
						left.push(right.pop());
						numPopped--;
					}
				}
			}
			
			
		}
		
		while(!left.isEmpty()){ //left is sorted so just push into right stack
		    right.push(left.pop());
		}
		
		int index = 0; 
		while(!right.isEmpty()){ //copy into array
		    array[index] = right.pop();
		    index++;
		}
		return array;
		
	}
}