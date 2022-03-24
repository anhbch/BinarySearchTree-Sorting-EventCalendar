/**
 * Name: Anh Bach
 * Email: tbach@ucsd.edu
 * Sources used: Oracle, Lecture Slides
 * 
 * This file contains a MyBST class, which extends Comparable 
 * and use key and value to keep track the binary tree. 
 * Value can be accessed by key 
 */
import java.util.ArrayList;

/**
 * This class contains methods to get value, get key
 * insert, traverse  and remove nodes from binary search tree
 */
public class MyBST<K extends Comparable<K>,V>{
    MyBSTNode<K,V> root = null;
    int size = 0;

    /**
     * Find number of elements in the tree
     * @return size of the binary tree
     */
    public int size(){
        return size;
    }

    /**
     * Insert a new node containing the arguments key and value 
     * into the binary search tree.
     * @return value replaced by new value, otherwise null
     */
    public V insert(K key, V value){
        // Exception
        if (key == null) {
            throw new NullPointerException();
        }

        // When the tree is empty, create root 
        if (this.root == null || size() == 0) {
            this.root = new MyBSTNode<K,V>(key, value, null);
            // Update size
            this.size++;
            return null;
        }

        V toReturn;
        // When the tree is not empty
        MyBSTNode<K,V> curr = this.root;
        while (curr != null && curr.getKey() != key) {
            int result = key.compareTo(curr.key);
            //Go left
            if (result < 0) {
                if (curr.getLeft() == null) {
                    curr.left = new MyBSTNode<K,V> (key, value, curr);
                    toReturn = null;
                    // Update size
                    this.size++;
                   return null;
                }
                else {
                    // Set current's left child as current
                    curr = curr.getLeft();
                }
            }
            //Go right 
            else {
                if (curr.getRight() == null) {
                    curr.right = new MyBSTNode<K,V>(key, value, curr);
                    toReturn = null;
                    // Update size
                    this.size++;
                    return null;
                }
                else {
                    // Set current's right child as current
                    curr = curr.getRight();
                }
            }
        }
        // Replace the value of already existed key
        toReturn = curr.getValue();
        curr.setValue(value);
        return toReturn;
    }

    /**
     * Search for a node with key equal to key
     * @return value associated with that node
     */
    public V search(K key){
        // When key is null
        if (key == null) {
            return null;
        }
        V toReturn = null;
        // When key is not null
        MyBSTNode<K,V> curr = this.root;
        while (curr != null) {
            int result = key.compareTo(curr.key);
            // Key is equal to current key
            if (result == 0) {
                toReturn = curr.getValue();
                curr = null; // stop the loop
            }
            // Go left
            else if (result < 0) {
                curr = curr.getLeft();
            }
            // Go right
            else {
                curr = curr.getRight();
            }
        }
        return toReturn;
    }

    /**
     * Search for a node with key equal to key and remove 
     * from the tree and all references should be fixed
     * @return value associated with that node
     */
    public V remove(K key){
        // When key is null
        if (key == null) {
            return null;
        }

        V toReturn = null;
        MyBSTNode<K,V> curr = this.root;
        MyBSTNode<K,V> parent = null;

        // Search for the key
        while (curr != null && curr.getKey() != key) {
            int result = key.compareTo(curr.key);
            parent = curr;
            // Go left
            if (result < 0) {
                curr = curr.getLeft();
            }
            // Go right
            else {
                curr = curr.getRight();
            }
        }
        // If key is not found 
        if (curr == null) {
            curr = this.root;
            toReturn = null;
        }
         // If key is found 
        toReturn = curr.getValue();

        // Case 1: node is a leaf
        if (curr.getRight() == null && curr.getLeft() == null) {
            if (curr != root) {
                // If key is left child
                if (parent.getLeft().equals(curr)) {
                    parent.setLeft(null);
                }
                // If key is right child
                else {
                    parent.setRight(null);
                }
            }
            else {
                root = null;
            }
        }

        // Case 2: A node with 2 children
        if (curr.getRight() != null && curr.getLeft() != null) {
            MyBSTNode<K,V> successor = curr.successor();
            V sucValue = successor.getValue();
            // Copy value data from succesor to curr node
            curr.setValue(sucValue);
            // Recursively remove successor 
            remove(successor.getKey());
        }

        // Case 3: A node with only 1 child
        else {
            // Choose a child node
            MyBSTNode<K,V> child = (curr.getLeft() != null)? 
                curr.getLeft(): curr.getRight();
            // If node is not root, assign to grandparent  
            if (curr != root) {
                if (curr.equals(parent.getLeft())) {
                    parent.setLeft(child);
                }
                else {
                    parent.setRight(child);
                }
            }
            // if node is root, child becomes root 
            else {
                root = child;
            }
            // Update size 
            this.size--;
        }
        // Return removed value 
        return toReturn;
    }
    
    /**
     * Find in-order traversal of the tree
     * @return Array of in-order traversal of the tree, 
     * null if tree is empty
     */
    public ArrayList<MyBSTNode<K, V>> inorder(){
        // When tree is 0, return empty array
        if (this.root == null || size () == 0) {
            return new ArrayList<>();
        }

        ArrayList<MyBSTNode<K, V>> arr = new ArrayList<>();
        MyBSTNode<K,V> curr = this.root;
        // Find the smallest node
        while (curr != null && curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        // Add the smallest node to array
        arr.add(curr);
        // Traverse up an find the successor of nodes
        while (curr.successor() != null) {
            curr = curr.successor();
            arr.add(curr);
        }
        return arr;
    }

    /**
     * This class contains methods to access key, value
     * set key, value and finding predecessor and successor 
     * of the binary search tree
     */
    static class MyBSTNode<K,V>{
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K,V> parent;
        private MyBSTNode<K,V> left = null;
        private MyBSTNode<K,V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         * @param key the key the MyBSTNode<K,V> will
         * @param value the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent; 
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey(){
            return key;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue(){
            return value;
        }

        /**
         * Return the parent
         * @return the parent
         */
        public MyBSTNode<K,V> getParent(){
            return parent;
        }

        /**
         * Return the left child 
         * @return left child
         */
        public MyBSTNode<K,V> getLeft(){
            return left;
        }

        /**
         * Return the right child 
         * @return right child
         */
        public MyBSTNode<K,V> getRight(){
            return right;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         * @param newKey the key to be stored
         */
        public void setKey(K newKey){
            this.key = newKey;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         * @param newValue the data to be stored
         */
        public void setValue(V newValue){
            this.value = newValue;
        }

        /**
         * Set the parent
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K,V> newParent){
            this.parent = newParent;
        }

        /**
         * Set the left child
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K,V> newLeft){
            this.left = newLeft;
        }

        /**
         * Set the right child
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K,V> newRight){
            this.right = newRight;
        }

        /**
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor(){
            // When right child is not null
            if(this.getRight() != null){
                // Set right child as current node
                MyBSTNode<K,V> curr = this.getRight();
                // When current's left child is not null
                // then traverse the left subtree
                while(curr.getLeft() != null){
                    // Set current's left child as current node
                    curr = curr.getLeft();
                }
                return curr;
            }
            // When right child is null
            else{
                // Create variable parent to hold node's parent
                MyBSTNode<K,V> parent = this.getParent();
                // Set the node as current node
                MyBSTNode<K,V> curr = this;
                // When parent is not null and current node is 
                // equal to parent's right child
                while(parent != null && curr == parent.getRight()){
                    // Set parent as current
                    curr = parent;
                    // Move up by set parrent as current parrent node's parent
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /**
         * This method returns the in order predecessor of current node object.
         * It can be served as a helper method when implementing inorder().
         * @return the predecessor of current node object
         */
        public MyBSTNode<K, V> predecessor(){
            // When left child is node null 
            if (this.getLeft() != null) {
                // Set left child as current node
                MyBSTNode<K,V> curr = this.getLeft();
                // When current's right child is not null then 
                // traverse the right subtree
                while(curr.getRight() != null){
                    // Set current's right child as current node
                    curr = curr.getRight();
                }
                return curr;
            }
            else {
                // Create variable parent to hold node's parent
                MyBSTNode<K,V> parent = this.getParent();
                // Set the node as current node
                MyBSTNode<K,V> curr = this;
                // When parent is not null and current node is 
                // equal to parent's left child
                while(parent != null && curr == parent.getLeft()){
                    // Set parent as current
                    curr = parent;
                    // Move up by set parrent as current parrent node's parent
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /** This method compares if two node objects are equal.
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj){
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K,V> comp = (MyBSTNode<K,V>)obj;
            
            return( (this.getKey() == null ? comp.getKey() == null : 
                this.getKey().equals(comp.getKey())) 
                && (this.getValue() == null ? comp.getValue() == null : 
                this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         * @return "Key:Value" that represents the node object
         */
        public String toString(){
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}