/**
 * AVL Tree representation of Student Tracking system, provides add, delete,
 * retrieve functions
 *
 */
public class AVLTree implements DT {
	private AVLNode rootNode;
	private int totalNodes;

	/**
	 * Gets Height of Tree
	 * 
	 * @return height
	 */
	public int getTreeHeight() {
		return rootNode == null ? 0 : rootNode.height;
	}

	/**
	 * Gets Root node of AVL Tree
	 * 
	 * @return root node
	 */
	public AVLNode getRootNode() {
		return rootNode;
	}

	/**
	 * Sets root node of AVL tree
	 * 
	 * @param rootNode root node
	 */
	public void setRootNode(AVLNode rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * Gets total nodes present in tree
	 * 
	 * @return total nodes
	 */
	public int getTotalNodes() {
		return totalNodes;
	}

	/**
	 * Sets total nodes in the tree
	 * 
	 * @param totalNodes total nodes
	 */
	public void setTotalNodes(int totalNodes) {
		this.totalNodes = totalNodes;
	}

	@Override
	public boolean contains(int studentID) {
		return containsKey(rootNode, studentID);
	}

	/**
	 * Recursive function to find existence of student ID
	 * 
	 * @param rootNode  Root node at each iteration
	 * @param studentID student ID to find
	 * @return true if it exists or else false
	 */
	public boolean containsKey(AVLNode rootNode, int studentID) {
		if (rootNode == null)
			return false;
		if (studentID < rootNode.getStudentID())
			return containsKey(rootNode.leftNode, studentID);
		else if (studentID > rootNode.getStudentID()) {
			return containsKey(rootNode.rightNode, studentID);
		}
		return true;
	}

	/**
	 * Inserts new node with given student information in the AVL tree
	 * 
	 * @param studentInfo student fname, lname, DOB
	 * @param studentID   student ID in 8 digits number format
	 * @return true if insertion is successful or else false
	 */
	public boolean insertNode(String studentInfo, int studentID) {
		if (!containsKey(rootNode, studentID)) {
			rootNode = insertNode(rootNode, studentInfo, studentID);
			totalNodes++;
			return true;
		}
		return false;
	}

	/**
	 * Recursive function to insert the node at the right place
	 * 
	 * @param rootNode    root node at each iteration for drill down
	 * @param studentInfo student information like fname, lname, dob
	 * @param studentID   student ID in 8 digits format
	 * @return
	 */
	private AVLNode insertNode(AVLNode rootNode, String studentInfo, int studentID) {

		if (rootNode == null)
			return new AVLNode(studentID, studentInfo);

		if (studentID < rootNode.getStudentID())
			rootNode.leftNode = insertNode(rootNode.leftNode, studentInfo, studentID);
		else if (studentID > rootNode.getStudentID()) {
			rootNode.rightNode = insertNode(rootNode.rightNode, studentInfo, studentID);
		}
		updateHeightBalanceFactor(rootNode);
		return balanceAVLTree(rootNode);
	}

	/**
	 * Balances AVL tree with correct levels when new node is inserted or any node
	 * is deleted
	 * 
	 * @param rootNode root node of the tree
	 * @return balance node
	 */
	private AVLNode balanceAVLTree(AVLNode rootNode) {
		if (rootNode.balanceFactor == -2) {
			return (rootNode.leftNode.balanceFactor <= 0) ? executeLeftLeftCase(rootNode)
					: executeLeftRightCase(rootNode);
		} else if (rootNode.balanceFactor == 2) {
			return (rootNode.rightNode.balanceFactor >= 0) ? executeRightRightCase(rootNode)
					: executeRightLeftCase(rootNode);
		}
		return rootNode;
	}

	/**
	 * Performs balancing when there is issue in left left side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return AVL node rotated correctly
	 */
	private AVLNode executeLeftLeftCase(AVLNode rootNode) {
		return rotateRight(rootNode);
	}

	/**
	 * Performs balancing when there is issue in right right side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return AVL node rotated correctly
	 */
	private AVLNode executeRightRightCase(AVLNode rootNode) {
		return rotateLeft(rootNode);
	}

	/**
	 * Performs balancing when there is issue in right left side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return AVL node rotated correctly
	 */
	private AVLNode executeRightLeftCase(AVLNode rootNode) {
		rootNode.rightNode = rotateRight(rootNode.rightNode);
		return executeRightRightCase(rootNode);
	}

	/**
	 * Performs balancing when there is issue in left right side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return AVL node rotated correctly
	 */
	private AVLNode executeLeftRightCase(AVLNode rootNode) {
		rootNode.leftNode = rotateLeft(rootNode.leftNode);
		return executeLeftLeftCase(rootNode);
	}

	/**
	 * Rotates node given to the left side
	 * 
	 * @param rootNode node to be rotated
	 * @return new parent after rotation
	 */
	private AVLNode rotateLeft(AVLNode rootNode) {
		AVLNode newParent = rootNode.rightNode;
		rootNode.rightNode = newParent.leftNode;
		newParent.leftNode = rootNode;
		updateHeightBalanceFactor(rootNode);
		updateHeightBalanceFactor(newParent);
		return newParent;
	}

	/**
	 * Rotates node given to the right side
	 * 
	 * @param rootNode node to be rotated
	 * @return new parent after rotation
	 */
	private AVLNode rotateRight(AVLNode rootNode) {
		AVLNode newParent = rootNode.leftNode;
		rootNode.leftNode = newParent.rightNode;
		newParent.rightNode = rootNode;
		updateHeightBalanceFactor(rootNode);
		updateHeightBalanceFactor(newParent);
		return newParent;
	}

	/**
	 * Updates height and balance factor of node given in input
	 * 
	 * @param rootNode node to be balanced
	 * @return
	 */
	private void updateHeightBalanceFactor(AVLNode rootNode) {
		int leftNodeHeight = (rootNode.leftNode == null) ? -1 : rootNode.leftNode.height;
		int rightNodeHeight = (rootNode.rightNode == null) ? -1 : rootNode.rightNode.height;
		rootNode.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
		rootNode.balanceFactor = rightNodeHeight - leftNodeHeight;
	}

	/**
	 * Deletes node with a given student ID
	 * 
	 * @param rootNode  current root of the AVL tree at each iterations
	 * @param studentID student ID
	 * @return deleted node
	 */
	private AVLNode deleteNode(AVLNode rootNode, int studentID) {
		if (rootNode == null)
			return null;

		if (studentID < rootNode.getStudentID())
			rootNode.leftNode = deleteNode(rootNode.leftNode, studentID);
		else if (studentID < rootNode.getStudentID()) {
			rootNode.rightNode = deleteNode(rootNode.rightNode, studentID);
		} else {
			if (rootNode.leftNode == null) {
				return rootNode.rightNode;
			} else if (rootNode.rightNode == null) {
				return rootNode.leftNode;
			} else {
				if (rootNode.leftNode.height > rootNode.rightNode.height) {
					AVLNode successorNode = findMax(rootNode.leftNode);
					rootNode.studentID = successorNode.studentID;
					rootNode.studentInfo = successorNode.studentInfo;
					rootNode.leftNode = deleteNode(rootNode.leftNode, successorNode.studentID);
				} else {
					AVLNode successorNode = findMin(rootNode.rightNode);
					rootNode.studentID = successorNode.studentID;
					rootNode.studentInfo = successorNode.studentInfo;
					rootNode.rightNode = deleteNode(rootNode.rightNode, successorNode.studentID);
				}
			}
		}
		updateHeightBalanceFactor(rootNode);
		return balanceAVLTree(rootNode);
	}

	/**
	 * Delete the Root of the AVL tree
	 * 
	 * @return deleted node
	 */
	public AVLNode deleteRoot() {
		if (rootNode == null) {
			return null;
		} else {
			AVLNode alTemp = new AVLNode(rootNode.studentID, rootNode.getStudentInfo());
			remove(rootNode.studentID);
			return alTemp;
		}
	}

	/**
	 * Finds minimum element in left side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return minimum element
	 */
	private static AVLNode findMin(AVLNode rootNode) {
		while (rootNode.leftNode != null)
			rootNode = rootNode.leftNode;
		return rootNode;
	}

	/**
	 * Finds maximum element in right side
	 * 
	 * @param rootNode root node of the AVL tree
	 * @return maximum element
	 */
	private static AVLNode findMax(AVLNode rootNode) {
		while (rootNode.rightNode != null)
			rootNode = rootNode.rightNode;
		return rootNode;
	}

	/**
	 * Returns all keys of the AVL tree in a sorted order (In order traversal)
	 * 
	 * @param node node at current iteration
	 */
	public void allKeys(AVLNode node) {
		if (node == null) {
			return;
		}
		allKeys(node.leftNode);
		System.out.printf("%s ", node.studentID + " --- ");
		allKeys(node.rightNode);
	}

	public String getValues(int studentID) {
		if (rootNode == null)
			return null;
		AVLNode foundNode = getStudentInfo(rootNode, studentID);
		if (foundNode == null) {
			return "Key " + studentID + " doesn't exists.";
		}
		return "Value for Key " + studentID + " is :" + foundNode.studentInfo;
	}

	/**
	 * Finding student information recursively by passing student ID
	 * 
	 * @param rootNode root node to consider at each iteration
	 * @param studentID student ID
	 * @return
	 */
	private AVLNode getStudentInfo(AVLNode rootNode, int studentID) {
		if (rootNode == null)
			return null;
		if (studentID < rootNode.getStudentID())
			return getStudentInfo(rootNode.leftNode, studentID);
		else if (studentID > rootNode.getStudentID()) {
			return getStudentInfo(rootNode.rightNode, studentID);
		} else if (studentID == rootNode.getStudentID()) {
			return rootNode;
		}
		return null;
	}

	
	@Override
	public void addElement(int studentID, String value) {
		if (!containsKey(rootNode, studentID)) {
			rootNode = insertNode(rootNode, value, studentID);
			totalNodes++;
		}
	}

	@Override
	public void remove(int studentID) {
		if (containsKey(rootNode, studentID)) {
			rootNode = deleteNode(rootNode, studentID);
			totalNodes--;
			return;
		}
	}

	@Override
	public void allKeys() {
		allKeys(this.getRootNode());
	}

	@Override
	public int nextKey(int studentID) {
		if (findSuccessorElement(rootNode, null, studentID) != null)
			return findSuccessorElement(rootNode, null, studentID).studentID;
		else {
			System.out.println("Element " + studentID + " not found!");
			return -1;
		}
	}

	@Override
	public int prevKey(int ID) {
		if (findPredecessorElement(rootNode, null, ID) != null)
			return findPredecessorElement(rootNode, null, ID).studentID;
		else {
			System.out.println("Element " + ID + " not found!");
			return -1;
		}
	}

	/**
	 * Finds successor element of given key
	 * 
	 * @param rootNode root node of AVL tree
	 * @param successor previous successor node
	 * @param key updated successor
	 * @return
	 */
	public static AVLNode findSuccessorElement(AVLNode rootNode, AVLNode successor, int key) {
		if (rootNode == null)
			return successor;

		if (rootNode.studentID == key) {
			if (rootNode.rightNode != null)
				return findMin(rootNode.rightNode);
		} else if (key < rootNode.studentID) {
			successor = rootNode;
			return findSuccessorElement(rootNode.leftNode, successor, key);
		} else
			return findSuccessorElement(rootNode.rightNode, successor, key);

		return successor;
	}

	/**
	 * Finds predecessor element of given key
	 * 
	 * @param rootNode root node of AVL tree
	 * @param successor previous predecessor node
	 * @param key updated predecessor
	 * @return
	 */
	public static AVLNode findPredecessorElement(AVLNode rootNode, AVLNode predecessor, int key) {
		if (rootNode == null)
			return predecessor;

		if (rootNode.studentID == key) {
			if (rootNode.leftNode != null)
				return findMax(rootNode.leftNode);
		} else if (key < rootNode.studentID)
			return findPredecessorElement(rootNode.leftNode, predecessor, key);
		else {
			predecessor = rootNode;
			return findPredecessorElement(rootNode.rightNode, predecessor, key);
		}
		return predecessor;
	}

	
	@Override
	public int rangeKey(int key1, int key2) {
		if (rootNode == null) {
			System.out.println("There are no records present in the tree.");
			return 0;
		}
		if (getStudentInfo(rootNode, key1) == null) {
			System.out.println("Key 1 doesn't exists!");
			return 0;
		} else if (getStudentInfo(rootNode, key2) == null) {
			System.out.println("Key 2 doesn't exists!");
			return 0;
		}
		return rangeRecursive(rootNode, key1, key2);
	}

	/**
	 * Recursive function to find number of nodes present in given range
	 * 
	 * @param node current node in iteration
	 * @param low lower limit
	 * @param high higher limit
	 * @return number of nodes in range
	 */
	private int rangeRecursive(AVLNode node, int low, int high) {
		if (node == null)
			return 0;
		if (node.studentID > low && node.studentID < high)
			return 1 + this.rangeRecursive(node.leftNode, low, high) + this.rangeRecursive(node.rightNode, low, high);
		else if (node.studentID < low)
			return this.rangeRecursive(node.rightNode, low, high);
		else
			return this.rangeRecursive(node.leftNode, low, high);
	}

	/**
	 * Node class of AVL Tree
	 *
	 */
	class AVLNode {
		private int studentID;
		private String studentInfo;
		private int height;
		private int balanceFactor;
		private AVLNode leftNode;
		private AVLNode rightNode;

		/**
		 * Parameterized constructor of AVL tree
		 * 
		 * @param studentID student ID
		 * @param studentInfo student information
		 */
		public AVLNode(int studentID, String studentInfo) {
			this.studentID = studentID;
			this.studentInfo = studentInfo;
		}

		/**
		 * Gets student Id of node
		 * 
		 * @return student id
		 */
		public int getStudentID() {
			return studentID;
		}

		/**
		 * Sets student id of node
		 * 
		 * @param studentID student id to be set
		 */
		public void setStudentID(int studentID) {
			this.studentID = studentID;
		}

		/**
		 * Gets student information
		 * 
		 * @return student information
		 */
		public String getStudentInfo() {
			return studentInfo;
		}

		/**
		 * Sets student information
		 * 
		 * @param studentInfo student information
		 */
		public void setStudentInfo(String studentInfo) {
			this.studentInfo = studentInfo;
		}

		/**
		 * Gets height of the node in AVL tree
		 * 
		 * @return height of node
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * Sets height of the node in AVL tree
		 * 
		 * @param height of node to be set
		 */
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		 * Gets balance factor of the node in AVL tree
		 * 
		 * @return balance factor
		 */
		public int getBalanceFactor() {
			return balanceFactor;
		}

		/**
		 * Sets balance factor of the node in AVL tree
		 * 
		 * @param balanceFactor balance Factor
		 */
		public void setBalanceFactor(int balanceFactor) {
			this.balanceFactor = balanceFactor;
		}

		/**
		 * Gets Left node of current node
		 * 
		 * @return left node
		 */
		public AVLNode getLeftNode() {
			return leftNode;
		}

		/**
		 * Sets Left node of current node
		 * 
		 * @param left node
		 */
		public void setLeftNode(AVLNode leftNode) {
			this.leftNode = leftNode;
		}

		/**
		 * Gets Right node of current node
		 * 
		 * @return left node
		 */
		public AVLNode getRightNode() {
			return rightNode;
		}

		/**
		 * Sets Right node of current node
		 * 
		 * @param right node
		 */
		public void setRightNode(AVLNode rightNode) {
			this.rightNode = rightNode;
		}
	}
}
