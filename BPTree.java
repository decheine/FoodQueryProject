/**
 * Filename:   BPTree.java
 * Project:    Final Project
 * Authors:    Sapan, Samuel Locke
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 * 
 * Due Date:   Before 10pm on December 12, 2018
 * Version:    1.0
 * 
 * Credits:    None
 * 
 * Bugs:       None
 */

package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu), Samuel Locke
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor = the number of children nodes for internal nodes of the tree
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        
        this.branchingFactor = branchingFactor;
        root = null;
    }
    
    
    /**
     * Inserts the key and value in the appropriate nodes in the tree
     * 
     * Note: key-value pairs with duplicate keys can be inserted into the tree.
     * 
     * @param key - the key to be inserted
     * @param value - the value to be inserted
     */
    @Override
    public void insert(K key, V value) {
        if(key == null) {
            return;
        }
        
        if(root == null) {
            root = new LeafNode();
            root.insert(key, value);
            
            return;
        }
        else {
            insertHelper(key, value, root);
            
            if(root.isOverflow()) {
                InternalNode newRoot = new InternalNode();
                
                int rootLength = root.keys.size();
                
                // adds median key of old root to new root
                newRoot.keys.add(root.keys.get((int) Math.floor(rootLength / 2)));
                
                // adds old root to left side
                newRoot.children.add(root);
                
                // splits old root and adds its new sibling to its right side
                newRoot.children.add(root.split());

                root = newRoot;
            }
        }
    }
    
    /**
     * Recursive helper method for insert. Traverses the tree until it finds the correct leaf node
     * for the key/value pair to be inserted into. Then on the recursive calls it checks if each
     * node in the path is overflown or not. If it is, it splits it and passes up its median value 
     * to its parent.
     * 
     * @param key - key to be inserted
     * @param value - value to be inserted
     * @param node - current node being inspected
     */
    @SuppressWarnings("unchecked")
    private void insertHelper(K key, V value, Node node) {
        int index = Collections.binarySearch(node.keys, key);
        
        // Collections.binarySearch returns (-index - 1) when key is not in list 
        // and index is where the key would have been
        if(index < 0) {
            index = ((-1 * index) - 1);
        }

        if(node instanceof BPTree.InternalNode) {
            InternalNode iNode = (InternalNode) node;
            Node childNode = iNode.children.get(index);
            
            insertHelper(key, value, childNode);
            
            // handles child nodes being overflown after insertion
            if(childNode.isOverflow()) {
                int childLength = childNode.keys.size();
                
                // retrieves median key value and adds it to the appropriate index
                iNode.keys.add(index, childNode.keys.get((int) Math.floor(childLength / 2)));
                
                // adds a reference to the right child of the newly added key in the step above
                iNode.children.add(index + 1, childNode.split());
            }
            
        }
        else {
            node.insert(key, value);
        }
    }
    
    /**
     * Gets the values that satisfy the given range 
     * search arguments.
     * 
     * Value of comparator can be one of these: 
     * "<=", "==", ">="
     * 
     * Example:
     *     If given key = 2.5 and comparator = ">=":
     *         return all the values with the corresponding 
     *      keys >= 2.5
     *      
     * If key is null or not found, return empty list.
     * If comparator is null, empty, or not according
     * to required form, return empty list.
     * 
     * @param key - key to be searched
     * @param comparator - comparator to be applied
     * @return list of values that are the result of the 
     * range search; if nothing found, return empty list
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<="))
            return new ArrayList<V>();
        
        ArrayList<V> rangeList = new ArrayList<V>();

        if(root != null) {
            rangeList.addAll(root.rangeSearch(key, comparator));
        }

        return rangeList;
    }
    
    /**
     * Returns a string representation for the tree
     * This method is provided to students in the implementation.
     * @return a string representation
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            @SuppressWarnings("unused")
            List<K> keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan, Samuel Locke
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children; // index is left child of key at index and index + 1 is its right child
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            keys = new ArrayList<K>();
            children = new ArrayList<Node>();
        }
        
        /**
         * Returns the first leaf key of the tree.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * Returns true if the number of keys in the node is greater than or equal to the branching 
         * factor. False otherwise.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(this.keys.size() >= branchingFactor) {
                return true;
            }
            
            return false;
        }
        
        /**
         * Finds the correct child of the node to be inserted into and then passes along the key
         * and value to that child's insert method.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            int index = Collections.binarySearch(this.keys, key);
            
            // Collections.binarySearch returns (-index - 1) when key is not in list and index is 
            // where the key would have been
            if(index < 0) { 
                index = ((-1 * index) - 1);
            }
            
            children.get(index).insert(key, value);
        }
        
        /**
         * Splits the node into two new nodes (a node to the left of the parent and a node to the 
         * right of the parent), giving the left node all keys and children whose index is 
         * < floor.(this.keys.size / 2) and giving the right node all of the keys and children
         * whose index is >= floor.(this.keys.size / 2).
         * The current node is then set to the left node while the right node is returned.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            int length = this.keys.size();
            
            InternalNode rightSibNode = new InternalNode(); // the node on the right side of the 
                                                            // parent of the current node
            
            InternalNode leftSibNode = new InternalNode(); // the node on the left side of the 
                                                           // parent of the current node
            
            int midFloor = (int) Math.floor(length / 2);
            
            for(int i = 0; i < length; ++i) {
                if(i < midFloor) { // leftSibNode has floor.(length / 2) keys
                                   // (e.g. if length == 5, leftSibNode has 2 keys)
                    leftSibNode.keys.add(this.keys.get(i));
                    leftSibNode.children.add(this.children.get(i));
                }
                else if(i == midFloor) {
                    leftSibNode.children.add(this.children.get(i));
                }
                else if(i >= midFloor) { // rightSibNode has ceiling.(length / 2) keys
                                        // (e.g. if length == 5, rightSibNode has 3 keys
                    rightSibNode.keys.add(this.keys.get(i));
                    rightSibNode.children.add(this.children.get(i));
                }
                
                if(i == (length - 1)) {
                    rightSibNode.children.add(this.children.get(i + 1));
                }
            }

            // set current node to be the leftSibNode
            this.keys = leftSibNode.keys;
            this.children = leftSibNode.children;

            return rightSibNode;
        }
        
        /**
         * Returns a list of all of the values possessed by this node's children whose corresponding 
         * keys satisfy the comparator in regards to the passed in key.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> rangeList = new ArrayList<V>();
            
            switch (comparator) {
                case "<=":
                    rangeList.addAll(children.get(0).rangeSearch(key, comparator));
                    break;
                    
                case ">=":
                    for(int i = 0; i < keys.size(); ++i) {
                        
                        // finds rightmost key that satisfies the comparator and retrieves its right 
                        // child
                        if(keys.get(i).compareTo(key) < 0) {
                            if(i == (keys.size() - 1)) {
                                rangeList.addAll(children.get(i + 1).rangeSearch(key, comparator));
                                break;
                            }
                            else{
                                continue;
                            }
                        }
                        else if(keys.get(i).compareTo(key) >= 0) {
                            rangeList.addAll(children.get(i).rangeSearch(key, comparator));
                            break;
                        }
                    }
                    break;
                    
                case "==":
                    for(int i = 0; i < keys.size(); ++i) {
                        // finds rightmost key that satisfies the comparator and retrieves its right 
                        // child
                        if(keys.get(i).compareTo(key) < 0) {
                            if(i == (keys.size() - 1)) {
                                rangeList.addAll(children.get(i + 1).rangeSearch(key, comparator));
                                break;
                            }
                            else{
                                continue;
                            }
                        }
                        else if(keys.get(i).compareTo(key) == 0) {
                            rangeList.addAll(children.get(i).rangeSearch(key, comparator));
                            break;
                        }
                        else if(keys.get(i).compareTo(key) > 0) {
                            rangeList.addAll(children.get(i).rangeSearch(key, comparator));
                            break;
                        }
                    }
                    break;
            }
            
            return rangeList;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan, Samuel Locke
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        @SuppressWarnings("unused")
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            keys = new ArrayList<K>();
            values = new ArrayList<V>();
            previous = null;
            next = null;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * Returns true if the number of keys in the node is greater than or equal to the branching 
         * factor. False otherwise.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(keys.size() >= branchingFactor) {
                return true;
            }
            
            return false;
        }
        
        /**
         * Uses a binary search of the keys list to find the appropriate index for the new key/value
         * pair. Inserts key and value into their respective lists at the given index. Assumes
         * the lists are sorted in ascending order.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            if(keys.size() > 0) {
                int index = Collections.binarySearch(this.keys, key);
                
                // Collections.binarySearch returns (-index - 1) when key is not in list and index 
                // is where the key would have been
                if(index < 0) { 
                    index = ((-1 * index) - 1);
                }
                
                this.keys.add(index, key);
                this.values.add(index, value);
            }
            else {
                this.keys.add(key);
                this.values.add(value);
            }
        }
        
        /**
         * Splits the node into two new nodes (a node to the left of the parent and a node to the 
         * right of the parent), giving the left node all key/value pairs whose index is 
         * < floor.(this.keys.size / 2) and giving the right node all of the key/value pairs 
         * whose index is >= floor.(this.keys.size / 2).
         * The current node is then set to the left node while the right node is returned.
         * Also sets this node's next value to the new right node and sets the new right node's
         * previous value to this node.
         * Is called AFTER a value has been inserted (i.e. the number of key/value pairs is greater
         * than or equal to the branching factor)
         * 
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            int length = this.keys.size();
            
            LeafNode rightSibNode = new LeafNode(); // the node on the right side of the parent of 
                                                    // the current node
            
            LeafNode leftSibNode = new LeafNode(); // the node on the left side of the parent of the 
                                                   // current node
            
            int midFloor = (int) Math.floor(length / 2);
            
            for(int i = 0; i < length; ++i) {
                if(i < midFloor) { // leftSibNode has floor.(length / 2) key/value pairs
                                   // (e.g. if length == 5, leftSibNode has 2 key/value pairs)

                    leftSibNode.keys.add(this.keys.get(i));
                    leftSibNode.values.add(this.values.get(i));
                }
                else if(i >= midFloor) { // rightSibNode has ceiling.(length / 2) key/value 
                                         // pairs (e.g. if length == 5, rightSibNode has 3 key/value 
                                         // pairs)

                    rightSibNode.keys.add(this.keys.get(i));
                    rightSibNode.values.add(this.values.get(i));
                }
            }

            // set current node to be the leftSibNode
            this.keys = leftSibNode.keys;
            this.values = leftSibNode.values;
            
            rightSibNode.next = this.next;
            
            this.next = rightSibNode;
            
            rightSibNode.previous = this;
            
            return rightSibNode;
        }
        
        /**
         * Returns a list of all of the values whose corresponding keys in this node satisfy the 
         * comparator in regards to the passed in key.
         * 
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> rangeList = new ArrayList<V>();
            
            for(int i = 0; i < keys.size(); ++i) {
                switch (comparator) {
                    case "<=":
                        if(keys.get(i).compareTo(key) <= 0) {
                            rangeList.add(values.get(i));
                        }
                        break;
                        
                    case ">=":
                        if(keys.get(i).compareTo(key) >= 0) {
                            rangeList.add(values.get(i));
                        }
                        break;
                        
                    case "==":
                        if(keys.get(i).compareTo(key) == 0) {
                            rangeList.add(values.get(i));
                        }
                        break;
                }
            }
            
            // checks if successor in linked list satisfies comparator
            if(next != null && next.keys != null && next.keys.size() >= 1) {
                switch (comparator) {
                    case "<=":
                        if(next.keys.get(0).compareTo(key) <= 0) {
                            rangeList.addAll(next.rangeSearch(key, comparator));
                        }
                        break;
                        
                    case ">=":
                        if(next.keys.get(0).compareTo(key) >= 0) {
                            rangeList.addAll(next.rangeSearch(key, comparator));
                        }
                        break;
                        
                    case "==":
                        if(next.keys.get(0).compareTo(key) == 0) {
                            rangeList.addAll(next.rangeSearch(key, comparator));
                        }
                        break;
                }
            }
            
            return rangeList;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    /*public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());

    }*/

} // End of class BPTree
