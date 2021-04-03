package cs146F20.dang.project4;

/**
 * Creates a Dictionary in the form of a Red Black Tree
 * @author chloedang
 *
 * @param <Key>
 */
public class RedBlackTree<Key extends Comparable<Key>> {	
		private static RedBlackTree.Node<String> root;
		
		/**
		 * Node class to assign attributes to nodes in the tree
		 * @author chloedang
		 *
		 * @param <Key>
		 */
		public static class Node<Key extends Comparable<Key>> { //changed to static 
			
			  Key key;  // key instantiation		  
			  Node<String> parent; // parent instantiation
			  Node<String> leftChild; // left child node instantiation
			  Node<String> rightChild; // right child instantiation
			  boolean isRed; // boolean to mark true if color is red
			  int color; // integer to denote red or black
			  
			  /**
			   * Constructor for initializing node attributes
			   * @param data
			   */
			  public Node(Key data){
				  this.key = data; // data initialization
				  leftChild = null; // left child initialization
				  rightChild = null; //right child initialization
			  }		
			  
			  /**
			   * compares strings using compareTo
			   * @param n
			   * @return integer less than, equal to, or greater than 0
			   */
			  public int compareTo(Node<Key> n){ 	//this < that  <0
			 		return key.compareTo(n.key);  	//this > that  >0
			  }
			  
			  /**
			   * checks if node is a leaf
			   * @return true if a leaf, false if not
			   */
			  public boolean isLeaf(){
				  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
				  if (this.equals(root)) return false;
				  if (this.leftChild == null && this.rightChild == null){
					  return true;
				  }
				  return false;
			  }
		}
		
		/**
		 * checks if node is a leaf
		 * @param n
		 * @return true if a leaf, false if not
		 */
		 public boolean isLeaf(RedBlackTree.Node<String> n){
			  if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
			  if (n.equals(root)) return false;
			  if (n.leftChild == null && n.rightChild == null){
				  return true;
			  }
			  return false;
		  }
		
		 /**
		  * Visitor interface that extends Comparable 
		  * @author chloedang
		  *
		  * @param <Key>
		  */
		public interface Visitor<Key extends Comparable<Key>> {
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		/**
		 * Prints out the node that is visited if it exists
		 * @param n
		 */
		public void visit(Node<Key> n){
			if(n == null) {
				return;
			}
			System.out.println(n.key);
		}
		
		/**
		 * prints the data in the nodes in the tree in a preorder order
		 */
		public void printTree(){  //preorder: visit, go left, go right
			RedBlackTree.Node<String> currentNode = root;	
			printTree(currentNode);
		}
		
		/**
		 * prints the data in the nodes in the tree in a preorder order
		 * @param node
		 */
		public void printTree(RedBlackTree.Node<String> node){
			System.out.print(node.key);
			if (node.isLeaf()){ //if the node is a leaf, do not print
				return;
			}
			if(node.leftChild != null) // if the nodes left child isnt null
				printTree(node.leftChild);
			if(node.rightChild != null) // if the nodes right child isnt null
				printTree(node.rightChild);
		}
		
		/**
		 * Constructor for RedBlackTree to initialize root as null
		 */
		public RedBlackTree() {
			root = null;
		}
		
		/**
		 * Place a new node in the RB tree with data the parameter and color it red. 
		 * @param data
		 */
		public void addNode(String data){  	//this < that  <0.  this > that  >0
			Node<String> n = new Node<String>(data);
			Node<String> p = null;
			Node<String> c = root;
			
			// go down the tree from the root until the bottom is reached
			while(c != null) {
				p = c;
				if(n.compareTo(c) < 0) 
					c = c.leftChild;
				else
					c = c.rightChild;
			}

			n.parent = p;
			if(p == null)
				root = n;
			
			else if (n.compareTo(p) < 0)
				p.leftChild = n;
			
			else 
				p.rightChild = n;
			
			// set the leaf null pointers
			n.leftChild = null;
			n.rightChild = null;
			// color the node red
			n.isRed = true;
			// after adding the node, fix the tree
			fixTree(n);
		}	

		/**
		 * Place a new node in the RB tree with data the parameter and color it red.
		 * @param data
		 */
		public void insert(String data){
			addNode(data);	
		}
		
		/**
		 * searches for a key
		 * @param k
		 * @return node if it exists. null otherwise
		 */
		public RedBlackTree.Node<String> lookup(String k){ 
			Node<String> currentNode = root;
			
			// while the bottom of the tree has not been reached
			while (currentNode != null) { 
				if (currentNode.key.equals(k)) {
					return currentNode;
				}
				// reassign currentNode to its right or left child accordingly
				else if (currentNode.key.compareTo(k) < 0) {
					currentNode = currentNode.rightChild;
				}
				else {
					currentNode = currentNode.leftChild;
				}
			}
			// if the node is not found, return null
			return null;
		}
	 	
		/**
		 * returns the sibling node of the parameter If the sibling does not exist, then return null.
		 * @param n
		 * @return
		 */
		public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n){ 
			
			if (n.parent != null && n.parent.leftChild != null && n.parent.rightChild != null) {	
				if (n == n.parent.leftChild) {
					return n.parent.rightChild;
				}
				else {
					return n.parent.rightChild;
				}
			}
			return null;
		}
		
		/**
		 * returns the aunt of the parameter or the sibling of the parent node. If the aunt node does not exist, then return null.
		 * @param n
		 * @return
		 */
		public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n){
			//
			Node<String> grandparent = getGrandparent(n);
			
			if(grandparent != null) {
				if(n.parent == grandparent.leftChild) {
					return grandparent.rightChild;
				}
				else {
					return grandparent.leftChild;
				}
			}
			return null;
		}
		
		/**
		 * Similar to getAunt() and getSibling(), returns the parent of your parent node, if it doesn’t exist return null.
		 * @param n
		 * @return
		 */
		public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n){
			if (n.parent != null && n.parent.parent != null) {
				return n.parent.parent;
			}
			return null;
		}
		
		/**
		 * rotate left around the node parameter
		 * @param n
		 */
		public void rotateLeft(RedBlackTree.Node<String> n){
			
			Node<String> y = n.rightChild;
			n.rightChild = y.leftChild;
			
			if(y.leftChild != null) {
				y.leftChild.parent = n;
			}
			
			y.parent = n.parent;
			
			if(n.parent == null) {
				root = y;
			}
			else if(n == n.parent.leftChild) {
				n.parent.leftChild = y;
			}
			else {
				n.parent.rightChild = y;
			}
			
			y.leftChild = n;
			n.parent = y;
		}
		
		/**
		 * rotate right around the node parameter
		 * @param n
		 */
		public void rotateRight(RedBlackTree.Node<String> n){
			//
			Node<String> x = n.leftChild;
			n.leftChild = x.rightChild;
			
			if(x.rightChild != null) {
				x.rightChild.parent = n;
			}
			
			x.parent = n.parent;
			
			if(n.parent == null) {
				root = x;
			}
			else if(n == n.parent.rightChild) {
				n.parent.rightChild = x;
			}
			else {
				n.parent.leftChild = x;
			}
			
			x.rightChild = n;
			n.parent = x;
		}
		
		/**
		 * recursively traverse the tree to make it a Red Black tree.
		 * @param current
		 */
		public void fixTree(RedBlackTree.Node<String> current) {
			// initialize the aunt, parent, and grandparent of the current node passed as a parameter
			Node<String> aunt = getAunt(current);
			Node<String> parent = current.parent;
			Node<String> grandparent = getGrandparent(current);

			if(current == root) { //  current node is root
				current.isRed = false; // make current node black
			} 
			else if(parent != null && !parent.isRed) { // if parent is black, the tree is balanced 

			} 
			else if(current != null && parent != null && grandparent != null && current != root 
					&& current.isRed && parent.isRed) { // if the current node is red and the parent node is red, the tree is unbalanced and needs to be modified

				if(aunt == null || !aunt.isRed) { // if aunt is black or empty
					
					// four cases stated in the project pdf to fix the tree accordingly
					if(parent == grandparent.leftChild
							&& current == parent.rightChild) { 
						rotateLeft(parent);
						fixTree(parent);
					} else if (parent == grandparent.rightChild 
							&& current == parent.leftChild) { 
						rotateRight(parent);
						fixTree(parent);
					} else if(parent == grandparent.leftChild 
							&& current == parent.leftChild) { 
						parent.isRed = false;
						grandparent.isRed = true;
						rotateRight(grandparent);
						return;
					} else if(parent == grandparent.rightChild 
							&& current == parent.rightChild) { 
						parent.isRed = false;
						grandparent.isRed = true;
						rotateLeft(grandparent);
						return;
					}
				} else if(getAunt(current).isRed) { // else if the aunt is red
					parent.isRed = false;
					aunt.isRed = false;
					grandparent.isRed = true;
					fixTree(grandparent);
				}
			}
		}
		
		/**
		 * check if the node is empty
		 * @param n
		 * @return
		 */
		public boolean isEmpty(RedBlackTree.Node<String> n){
			if (n.key == null){
				return true;
			}
			return false;
		}
		 
		/**
		 * check if the node is the left child
		 * @param parent
		 * @param child
		 * @return
		 */
		public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
		{
			if (child.compareTo(parent) < 0 ) {//child is less than parent
				return true;
			}
			return false;
		}

		/**
		 * visits node in a preorder order
		 * @param v
		 */
		public void preOrderVisit(Visitor<String> v) {
		   	preOrderVisit(root, v);
		}
		 
		/**
		 * visits node in a preorder order
		 * @param n
		 * @param v
		 */
		private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		  	if (n == null) {
		  		return;
		  	}
		  	v.visit(n);
		  	if (n.leftChild != null) {
		  		preOrderVisit(n.leftChild, v);
		  	}
		  	if (n.rightChild != null) {
		  		preOrderVisit(n.rightChild, v);
		  	}
		}	
	}

