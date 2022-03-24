/**
 * Name: Anh Bach
 * Email: tbach@ucsd.edu
 * Sources used: Oracle, Lecture Slides
 * 
 * This file contains a MyBSTIterator class that use key and
 * value to keep track the binary tree. Value can be accessed by key 
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class contains methods to iterate the binary search tree
 * and extends MyBST
 */
public class MyBSTIterator<K extends Comparable<K>, V> extends MyBST<K, V> {
    abstract class MyBSTNodeIterator<T> implements Iterator<T> {
        MyBSTNode<K, V> next;
        MyBSTNode<K, V> lastVisited;

        /**
         * Constructor that initializes the node iterator
         *
         * @param first The initial node that next points
         */
        MyBSTNodeIterator(MyBSTNode<K, V> first) {
            next = first;
            lastVisited = null;
        }

        /**
         * This method is used for determining if the next pointer in the
         * iterator points to null.
         *
         * @return If next is null based on the current position of iterator
         */
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Advances the iterator to the next node 
         * @return the node we advanced (/visited)
         */
        MyBSTNode<K, V> nextNode() {
            // Exception 
            if (next == null) {
                throw new NoSuchElementException();
            }
            
            // lastVisited should be updated to point to the node 
            // that next previously pointed to
            lastVisited = next;
            // Next should be updated to point to the inorder successor
            next = next.successor();

            return lastVisited;
        }

        /**
         * This method removes the last visited node from the tree.
         */
        public void remove() {
            // If last visited node is null then
            // throw an exception
            if (lastVisited == null) {
                throw new IllegalStateException();
            }
            // If last visited node has 2 children
            // then set next node = last visited node
            if (lastVisited.getRight() != null &&
                    lastVisited.getLeft() != null) {
                next = lastVisited;
            }
            // Recursively remove last visited node's key
            MyBSTIterator.this.remove(lastVisited.getKey());
            // Set last visited node to null to stop the recursion
            lastVisited = null;
        }
    }

    /**
     * BST Key iterator class that extends the node iterator.
     */
    class MyBSTKeyIterator extends MyBSTNodeIterator<K> {

        MyBSTKeyIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node key
         *
         * @return K the next key
         */
        public K next() {
            return super.nextNode().getKey();
        }
    }

    /**
     * BST value iterator class that extends the node iterator.
     */
    class MyBSTValueIterator extends MyBSTNodeIterator<V> {

        /**
         * Call the constructor method from node iterator
         *
         * @param first The initial value that next points
         */
        MyBSTValueIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node value
         *
         * @return V the next value
         */
        public V next() {
            return super.nextNode().getValue();
        }
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTKeyIterator getKeyIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTKeyIterator(curr);
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTValueIterator getValueIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTValueIterator(curr);
    }
}