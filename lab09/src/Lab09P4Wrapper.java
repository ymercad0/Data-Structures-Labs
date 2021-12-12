import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class Lab09P4Wrapper {

    public static interface TreeNode<E> {
        public E getValue();
    }

    public static class BinaryTreeNode<E> implements TreeNode<E> {

        private E value;
        private BinaryTreeNode<E> leftChild;
        private BinaryTreeNode<E> rightChild;
        private BinaryTreeNode<E> parent;


        public BinaryTreeNode(E value) {
            super();
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;

        }


        public BinaryTreeNode(E value, BinaryTreeNode<E> parent, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild) {
            super();
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

        @Override
        public E getValue() {
            return this.value;

        }


        public BinaryTreeNode<E> getLeftChild() {
            return leftChild;
        }


        public void setLeftChild(BinaryTreeNode<E> leftChild) {
            this.leftChild = leftChild;
        }


        public BinaryTreeNode<E> getRightChild() {
            return rightChild;
        }


        public void setRightChild(BinaryTreeNode<E> rightChild) {
            this.rightChild = rightChild;
        }


        public void setValue(E value) {
            this.value = value;
        }


        public BinaryTreeNode<E> getParent() {
            return parent;
        }


        public void setParent(BinaryTreeNode<E> parent) {
            this.parent = parent;
        }

    }

    public static interface Tree<E> {
        public TreeNode<E> root();
        public TreeNode<E> left(TreeNode<E> p);
        public TreeNode<E> right(TreeNode<E> p);
        public TreeNode<E> sibling(TreeNode<E> p);
        public boolean isEmpty();
        public int size();
        public boolean verifyCousins(E e1, E e2);
    }

    public static class BinaryTree<E> implements Tree<E> {

        private BinaryTreeNode<E> root;



        public BinaryTree(BinaryTreeNode<E> root) {
            super();
            this.root = root;
        }

        public BinaryTree(BinaryTreeNode<E> root, BinaryTree<E> T1, BinaryTree<E> T2) {
            super();
            this.root = root;
            if (T1 != null) {
                BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T1.root();
                this.root.setLeftChild(temp);
                temp.setParent(this.root);

            }
            if (T2 != null) {
                BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T2.root();

                this.root.setRightChild(temp);
                temp.setParent(this.root);

            }

        }



        @Override
        public TreeNode<E> root() {
            return this.root;
        }


        private void check(TreeNode<E> p) {
            if (p==null) {
                throw new IllegalArgumentException();
            }
        }
        @Override
        public TreeNode<E> left(TreeNode<E> p) {
            this.check(p);
            BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
            return temp.getLeftChild();
        }


        @Override
        public TreeNode<E> right(TreeNode<E> p) {
            this.check(p);
            BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
            return temp.getRightChild();

        }

        @Override
        public TreeNode<E> sibling(TreeNode<E> p) {
            this.check(p);
            BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
            if (temp.getParent().getLeftChild() != temp) {
                return temp.getRightChild();
            }
            else {
                return temp.getLeftChild();
            }

        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public int size() {
            return this.sizeAux(this.root);
        }

        private int sizeAux(BinaryTreeNode<E> N) {
            if (N == null) {
                return 0;
            }
            else {
                return 1 + this.sizeAux(N.getLeftChild()) + this.sizeAux(N.getRightChild());
            }

        }


        public void print() {
            this.printAux(this.root, 0);
        }

        private void printAux(BinaryTreeNode<E> N, int i) {
            if (N != null) {
                this.printAux(N.getRightChild(), i + 4);
                for (int j=0; j < i; ++j) {
                    System.out.print(" ");
                }
                System.out.println(N.getValue());
                this.printAux(N.getLeftChild(), i + 4);
            }

        }

        private BinaryTreeNode<E> find(E e, BinaryTreeNode<E> N) {
            if (N == null) {
                return null;
            }
            else if (N.getValue().equals(e)) {
                return N;
            }
            else {
                BinaryTreeNode<E> temp = find(e, N.getLeftChild());
                if (temp != null) {
                    return temp;
                }
                else {
                    return find(e, N.getRightChild());
                }
            }
        }

        public int depth(E target){
            return depthHelper(root, target, 0);
        }

        public int depthHelper(BinaryTreeNode<E> node, E target, int currDepth){ //need 3 parameters else you overwrite target
            if(node == null) return -1;
            int level = -1;

            if(node.getValue() == target){
                return currDepth;
            }

            if(node.leftChild != null){
                level = depthHelper(node.leftChild, target, currDepth+1); //check for target in left subtree

                if(level != -1) return level; //found the target's depth in the left subtree
            }

            if(node.rightChild != null){ //target must be in right subtree
                level = depthHelper(node.rightChild, target, currDepth+1);
            }
            return level; //when the elem is in the right subtree or not in tree
        }

        @Override
        public boolean verifyCousins(E e1, E e2) { //assumed both nodes are in the tree
            return depth(e1) == depth(e2);
        }

    }

    public static void main(String[] args) {
        //root node
        BinaryTreeNode<Integer> origin = new BinaryTreeNode<>(6);

        //branch to the left
        BinaryTreeNode<Integer> branch1 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> node1 = new BinaryTreeNode<>(1);
        node1.setParent(branch1);
        branch1.setLeftChild(node1);

        BinaryTreeNode<Integer> node2 = new BinaryTreeNode<>(2);
        node2.setParent(branch1);

        BinaryTreeNode<Integer> node3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> node4 = new BinaryTreeNode<>(7);
        node3.setParent(node2);
        node4.setParent(node2);
        node2.setLeftChild(node3);
        node2.setRightChild(node4);


        branch1.setRightChild(node2);

        //connect left branch to root
        origin.setLeftChild(branch1);

        //branch to the right
        BinaryTreeNode<Integer> node5 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> node6 = new BinaryTreeNode<>(8);

        node5.setParent(origin);
        node5.setLeftChild(node6);
        node6.setParent(node5);

        //connect branch to the right to the root
        origin.setRightChild(node5);

        //make a tree with the connected nodes
        BinaryTree<Integer> tree = new BinaryTree<>(origin);

//        System.out.println(tree.depth(8));
//        System.out.println(tree.depth(7));
//        System.out.println(tree.depth(9));


        System.out.println(tree.verifyCousins(3,7));
    }

}