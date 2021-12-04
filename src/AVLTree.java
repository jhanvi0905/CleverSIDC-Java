
public class AVLTree {
	private AVLNode rootNode;
	private int totalNodes;

	public int getTreeHeight() {
		return rootNode == null ? 0 : rootNode.height;
	}

	public AVLNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(AVLNode rootNode) {
		this.rootNode = rootNode;
	}

	public int getTotalNodes() {
		return totalNodes;
	}

	public void setTotalNodes(int totalNodes) {
		this.totalNodes = totalNodes;
	}

	public boolean containsKey(int studentID) {
		return containsKey(rootNode, studentID);
	}

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

	public boolean insertNode(String studentInfo, int studentID) {
		if (!containsKey(rootNode, studentID)) {
			rootNode = insertNode(rootNode, studentInfo, studentID);
			totalNodes++;
			return true;
		}
		return false;
	}

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

	private AVLNode executeLeftLeftCase(AVLNode rootNode) {
		return rotateRight(rootNode);
	}

	private AVLNode executeRightRightCase(AVLNode rootNode) {
		return rotateLeft(rootNode);
	}

	private AVLNode executeRightLeftCase(AVLNode rootNode) {
		rootNode.rightNode = rotateRight(rootNode.rightNode);
		return executeRightRightCase(rootNode);
	}

	private AVLNode executeLeftRightCase(AVLNode rootNode) {
		rootNode.leftNode = rotateLeft(rootNode.leftNode);
		return executeLeftLeftCase(rootNode);
	}

	private AVLNode rotateLeft(AVLNode rootNode) {
		AVLNode newParent = rootNode.rightNode;
		rootNode.rightNode = newParent.leftNode;
		newParent.leftNode = rootNode;
		updateHeightBalanceFactor(rootNode);
		updateHeightBalanceFactor(newParent);
		return newParent;
	}

	private AVLNode rotateRight(AVLNode rootNode) {
		AVLNode newParent = rootNode.leftNode;
		rootNode.leftNode = newParent.rightNode;
		newParent.rightNode = rootNode;
		updateHeightBalanceFactor(rootNode);
		updateHeightBalanceFactor(newParent);
		return newParent;
	}

	private void updateHeightBalanceFactor(AVLNode rootNode) {
		int leftNodeHeight = (rootNode.leftNode == null) ? -1 : rootNode.leftNode.height;
		int rightNodeHeight = (rootNode.rightNode == null) ? -1 : rootNode.rightNode.height;
		rootNode.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
		rootNode.balanceFactor = rightNodeHeight - leftNodeHeight;
	}

	public boolean deleteNode(int studentID) {
		if (containsKey(rootNode, studentID)) {
			rootNode = deleteNode(rootNode, studentID);
			totalNodes--;
			return true;
		}
		return false;
	}

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

	private AVLNode findMin(AVLNode rootNode) {
		while (rootNode.leftNode != null)
			rootNode = rootNode.leftNode;
		return rootNode;
	}

	private AVLNode findMax(AVLNode rootNode) {
		while (rootNode.rightNode != null)
			rootNode = rootNode.rightNode;
		return rootNode;
	}

	private void allKeys(AVLNode node) {
		if (node == null) {
			return;
		}
		allKeys(node.leftNode);
		System.out.printf("%s ", node.studentID + " --- ");
		allKeys(node.rightNode);
	}

	private String getValues(int studentID) {
		if (rootNode == null)
			return null;
		AVLNode foundNode = getStudentInfo(rootNode, studentID);
		if (foundNode == null) {
			return "Key " + studentID + " doesn't exists.";
		}
		return "Value for Key " + studentID + " is :" + foundNode.studentInfo;
	}

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

	private int nextKey(int studentID) {
		AVLNode foundNode = getStudentInfo(rootNode, studentID);
		if (foundNode == null || foundNode.getRightNode() == null) {
			return 0;
		}
		return foundNode.getRightNode().getStudentID();
	}

	private int prevKey(int studentID) {
		AVLNode foundNode = getStudentInfo(rootNode, studentID);
		if (foundNode == null || foundNode.getLeftNode() == null) {
			return 0;
		}
		return foundNode.getLeftNode().getStudentID();
	}
	
	private int rangeKey(int key1, int key2) {
		for(int i=0; i< totalNodes; i++) {
			
		}
		return 0;
	}

	class AVLNode implements PrintableNode {
		private int studentID;
		private String studentInfo;
		private int height;
		private int balanceFactor;
		private AVLNode leftNode;
		private AVLNode rightNode;

		public AVLNode(int studentID, String studentInfo) {
			this.studentID = studentID;
			this.studentInfo = studentInfo;
		}

		public int getStudentID() {
			return studentID;
		}

		public void setStudentID(int studentID) {
			this.studentID = studentID;
		}

		public String getStudentInfo() {
			return studentInfo;
		}

		public void setStudentInfo(String studentInfo) {
			this.studentInfo = studentInfo;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getBalanceFactor() {
			return balanceFactor;
		}

		public void setBalanceFactor(int balanceFactor) {
			this.balanceFactor = balanceFactor;
		}

		public AVLNode getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(AVLNode leftNode) {
			this.leftNode = leftNode;
		}

		public AVLNode getRightNode() {
			return rightNode;
		}

		public void setRightNode(AVLNode rightNode) {
			this.rightNode = rightNode;
		}
	}

	public static void main(String args[]) {
		int[] arr = { 90, 33, 89, 67, 35, 1, 9, 23, 77, 30 };
		AVLTree avl = new AVLTree();
		for (int i = 0; i < arr.length; i++) {
			avl.insertNode(("z" + i), arr[i]);
		}
		System.out.println();
		avl.allKeys(avl.rootNode);
		System.out.println();
		System.out.println(avl.getValues(23));
		System.out.println(avl.getValues(24));
		
		System.out.println("Successor for key 24 : " + avl.nextKey(24));
		System.out.println("Successor for key 30 : " + avl.nextKey(30));
		System.out.println("Successor for key 35 : " + avl.nextKey(35));
		System.out.println("Successor for key 33 : " + avl.nextKey(33));
		
		System.out.println("Successor for key 24 : " + avl.prevKey(24));
		System.out.println("Successor for key 30 : " + avl.prevKey(30));
		System.out.println("Successor for key 35 : " + avl.prevKey(35));
		System.out.println("Successor for key 33 : " + avl.prevKey(33));
	}

}
