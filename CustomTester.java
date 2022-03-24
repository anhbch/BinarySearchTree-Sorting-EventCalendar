/**
 * Name: Anh Bach
 * Email: tbach@ucsd.edu
 * Sources used: PublicTester, Slides
 * 
 * This file contains all custom test for MyBST,
 * MyBSTIterator and MyCalender
 */

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

 /**
  * CustomTester class that contains all custom tests for 
  * MyBST, MyBSTIterator and MyCalender
  */
public class CustomTester {

    MyBST<Integer, String> tree = new MyBST<>();
    MyBST<Integer, Integer> emptyTree = new MyBST<>();

    private static String EXCEPTION = "Exception is thrown!";
     
    /**
     * Set up non-empty tree 
     */
    @Before
    public void setup(){
    /**
     * tree:
     *           20
     *         "Hello"
     *         /    \
     *       12       30
     *     "World"    "CSE"
     *     /    \   
     *    11    15
     *   "PA"  "Tree"
     * 
     */
        MyBST.MyBSTNode<Integer, String> root = 
                new MyBST.MyBSTNode(20, "Hello", null);
            MyBST.MyBSTNode<Integer, String> twelwe = 
                new MyBST.MyBSTNode(12, "World", root);
            MyBST.MyBSTNode<Integer, String> thirteen = 
                new MyBST.MyBSTNode(30, "CSE", root);
            MyBST.MyBSTNode<Integer, String> eleven = 
                new MyBST.MyBSTNode(11, "PA", twelwe);
            MyBST.MyBSTNode<Integer, String> fifteen = 
                new MyBST.MyBSTNode(15, "Tree", twelwe);
    
        // Create the tree
        this.tree.root = root;
        root.setLeft(twelwe);
        root.setRight(thirteen);
        twelwe.setLeft(eleven);
        twelwe.setRight(fifteen);
        // Set size
        tree.size = 5;
    }

    

    // ====== MyBST class ======

    /**
     * Test 1: insert when key is null
     */
    @Test
    public void testInsert(){
        try {
            tree.insert(null, "testInsert");
            fail();
        }
        catch (NullPointerException e) {
            System.out.println(EXCEPTION);
        }
    }

     /**
     * Test 2: insert when key already exists
     */
    @Test
    public void testInsert2() {
        assertEquals("CSE", tree.insert(30, "Replace"));
        tree.insert(30, "Replace");
        assertEquals("Replace", tree.root.getRight().getValue());
        // Check size
        assertEquals(5, tree.size());
    }

    /**
     * Test 3: search when key is null
     */
    @Test
    public void testSearch() {
        // Search(null) will return null
        assertNull(tree.search(null));
    }

    /**
     * Test 4: remove when node has 2 children
     */
    @Test
    public void testRemove() {
        assertEquals(tree.root.getLeft().getRight(), tree.root.getLeft().successor());
        assertEquals(5, tree.size());
        assertEquals("World", tree.remove(12));
        // Check size
        assertEquals(4, tree.size());
        // Check tree
        assertEquals("Tree", tree.root.getLeft().getValue());
        assertEquals("PA", tree.root.getLeft().getLeft().getValue());

        assertEquals("PA", tree.remove(11));
        assertEquals(3, tree.size());
    }

    /**
     * Test 5: Test in order of empty tree
     */
    @Test
    public void testInorder() {
        // An empty array should be returned
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> realArr = emptyTree.inorder();
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedArr = 
            new ArrayList<MyBST.MyBSTNode<Integer, Integer>>();
        assertEquals(expectedArr, realArr);
    }

    // ====== MyBSTNode class ======

    /**
     * Test 6: Test successor when it doesn't have right child
     */
    @Test
    public void testSuccessor() {
        // Expected node 20 (root)
        assertEquals(tree.root, tree.root.getLeft().getRight().successor());
    }

    /**
     * Test 7: Test successor when it doesn't have right child
     * and doesn't have successor
     */
    @Test
    public void testSuccessor2() {
        // Expected null
        assertNull(tree.root.getRight().successor());
    }

    // ====== MyBSTIterator class ======

    /**
     * Test 8: Test nextNode() when next is null
     */
    @Test
    public void testNextNode() {
        // Assign variable from emptyTree to the iterator
        MyBSTIterator<Integer, Integer> iter = new MyBSTIterator<>();
        iter.root = emptyTree.root;
        // Initialize the BST value iterator, start from root
        MyBSTIterator<Integer, Integer>.MyBSTValueIterator empty = 
            iter.new MyBSTValueIterator(iter.root);
        try {
            empty.nextNode();
            fail();
        }
        catch (NoSuchElementException e) {
            System.out.println(EXCEPTION);
        }
    }

    /**
     * Test 9: Test nextNode() when next is pointed to successor
     */
    @Test
    public void testNextNode2() {
        // Assign variable from tree to the iterator
        MyBSTIterator<Integer, String> iter = new MyBSTIterator<>();
        iter.root = tree.root;
        // Initialize the BST value iterator, start from root
        MyBSTIterator<Integer, String>.MyBSTValueIterator iterVisit = 
            iter.new MyBSTValueIterator(iter.root);
        
            iterVisit.nextNode();
            // Last visited node is root
            assertSame(tree.root, iterVisit.lastVisited);
            // next is pointed to successor
            assertSame(tree.root.getRight(), iterVisit.next);

            iterVisit.nextNode();
            // Last visited node is node 30
            assertSame(tree.root.getRight(), iterVisit.lastVisited);
            // next is pointed to successor 
            // There is no successor => Null
            assertNull(iterVisit.next);  
    }

    // ====== Calender class ======

    /**
     * Test 10: Test calender when start is bigger than end
     */
    @Test
    public void testCalender() {
        MyCalendar calender = new MyCalendar();
        try {
            calender.book(5, 1);
            fail();
        }
        catch (IllegalArgumentException e) {
            System.out.println(EXCEPTION);
        }
    }

}
