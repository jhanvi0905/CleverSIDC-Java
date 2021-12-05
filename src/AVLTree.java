public class AVLTree implements DT{
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
	public AVLNode deleteRoot(){
		if(rootNode==null){
			return null;
		}else {
			AVLNode alTemp = new AVLNode(rootNode.studentID, rootNode.getStudentInfo());
			remove(rootNode.studentID);
			return alTemp;
		}
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

	public int nextKey(int studentID) {

		if(findSuccessor(rootNode, null, studentID)!=null)
			return findSuccessor(rootNode, null, studentID).studentID;
		else{
			System.out.println("Element "+studentID+" not found!");
			return -1;
		}
	}
	public static AVLNode findMinimum(AVLNode root)
	{
		while (root.leftNode != null) {
			root = root.leftNode;
		}

		return root;
	}

	public static AVLNode findSuccessor(AVLNode root, AVLNode succ, int key)
	{
		// base case
		if (root == null) {
			return succ;
		}
		if (root.studentID == key)
		{
			if (root.rightNode != null) {
				return findMinimum(root.rightNode);
			}
		}
		else if (key < root.studentID)
		{
			succ = root;
			return findSuccessor(root.leftNode, succ, key);
		}
		else {
			return findSuccessor(root.rightNode, succ, key);
		}
		return succ;
	}
	public static AVLNode findMaximum(AVLNode root)
	{
		while (root.rightNode != null) {
			root = root.rightNode;
		}

		return root;
	}
	public static AVLNode findPredecessor(AVLNode root, AVLNode prec, int key)
	{
		if (root == null) {
			return prec;
		}
		if (root.studentID == key)
		{
			if (root.leftNode != null) {
				return findMaximum(root.leftNode);
			}
		}
		else if (key < root.studentID) {
			return findPredecessor(root.leftNode, prec, key);
		}
		else {
			prec = root;
			return findPredecessor(root.rightNode, prec, key);
		}
		return prec;
	}

	@Override
	public int prevKey(int ID) {
		if(findPredecessor(rootNode, null, ID)!=null)
			return findPredecessor(rootNode, null, ID).studentID;
		else{
			System.out.println("Element "+ID+" not found!");
			return -1;
		}
	}
	
	public int rangeKey(int key1, int key2) {
		for(int i=0; i< totalNodes; i++) {
			
		}
		return 0;
	}

	class AVLNode {
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
}
