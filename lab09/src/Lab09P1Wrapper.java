public class Lab09P1Wrapper {
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
        public int getLevelCount(int level);
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
            // ADD YOUR CODE HERE
            return this.sizeAux(this.root);

            //return 0;
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


        @Override
        public int getLevelCount(int level) {
            if(level <= 0){
                throw new IllegalArgumentException("Level must be greater than 0!");
            }

            return getRecLevelCount(root, level);
        }

        /*RECURSIVE AUXILIARY METHOD*/
        private int getRecLevelCount(BinaryTreeNode<E> root, int level) {
            return helperGetRecLevelCount(root, level,  1);
        }

        private int helperGetRecLevelCount(BinaryTreeNode<E> root, int level, int currLevel) {
            if(root == null) return 0;
            if(currLevel  == level) return 1; //iterate through the left and right subtrees in order
            return helperGetRecLevelCount(root.leftChild, level, currLevel+1) +
                    helperGetRecLevelCount(root.rightChild, level, currLevel+1);
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

        System.out.println(tree.getLevelCount(1));

    }

}