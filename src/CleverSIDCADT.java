import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Jhanvi Arora, Zalak Patel.
 *
 * class implementing clever dynamic data structure.
 */
class CleverSIDC {
    /*
    Adapter dataStructure that switches between LinkedList and AVL.
     */
	DT dataStructure;
	/*
	Maintains count of number of elements added.
	 */
	int sizeCount = 0;
	/*
	Threshold of number of elements to be inserted
	 */
	int sizeThreshold;

    /**
     * Set threshold of number of elements to be inserted.
     * @param size threshold
     */
	public void SetSIDCThreshold(int size) {
		sizeThreshold = size;
		dataStructure = size <= 1000 ? new LinkedList() : new AVLTree();
	}

    /**
     * Adds element to CleverSIDC
     * @param cs CleverSIDC Object
     * @param ID ID to be inserted
     * @param value Value of student info
     */
	public void add(CleverSIDC cs, int ID, String value) {
		if (sizeCount == 1000) {
			convertToAVL();
		}
		if (!cs.contains(cs, ID)) {
			cs.dataStructure.addElement(ID, value);
			sizeCount++;
			System.out.println("Student ID : " + ID + " added.");
		} else {
			System.out.println("Key " + ID + " already exists!");
		}
	}

    /**
     * Converts LL to AVL.
     */
	public void convertToAVL() {
		AVLTree temp = new AVLTree();
		LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
		while (ll!=null && ll.size() != 0) {
			Node tempNode = ll.deleteStart();
			temp.insertNode(tempNode.getVal(), tempNode.getData());
		}
		dataStructure = temp;
		System.out.println("Linked List converted to AVL TREE!");
		temp.allKeys();
	}

    /**
     * Prints all the IDs inserted in CleverSIDC.
     * @param cs CleverSIDC object
     */
	public void allKeys(CleverSIDC cs) {
		cs.dataStructure.allKeys();
	}

    /**
     * Removes an element with given key.
     * @param cs CleverSIDC object.
     * @param key key to be removed.
     */
	public void remove(CleverSIDC cs, int key) {
		if (sizeCount == 1001) {
			convertToLL();
		}
		cs.dataStructure.remove(key);
		sizeCount--;
	}

    /**
     * Converts AVL to LL when size decreases.
     */
	public void convertToLL() {
		LinkedList lTemp = new LinkedList();
		AVLTree alTemp = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
		while (alTemp.getTotalNodes() != 0) {
			AVLTree.AVLNode toDelete = alTemp.deleteRoot();
			lTemp.addElement(toDelete.getStudentID(), toDelete.getStudentInfo());
		}
		dataStructure = lTemp;
		System.out.println("AVL Tree converted to LinkedList!");
		lTemp.allKeys();
	}

    /**
     * gets value for a given key (ID).
     * @param cs CleverSIDC Object.
     * @param key key to get values of.
     * @return String containing info.
     */
	public String getValues(CleverSIDC cs, int key) {
		return cs.dataStructure.getValues(key);
	}

    /**
     * Gets successor key for a given key.
     * @param cs CleverSDC Object.
     * @param key key to find next of.
     * @return next key.
     */
	public int nextKey(CleverSIDC cs, int key) {
		return cs.dataStructure.nextKey(key);
	}
    /**
     * Gets predecessor key for a given key.
     * @param cs CleverSDC Object.
     * @param key key to find previous of.
     * @return prev key.
     */
	public int prevKey(CleverSIDC cs, int key) {
		return cs.dataStructure.prevKey(key);
	}

    /**
     * Finds count of keys between two given keys.
     * @param cs CleverSIDC object.
     * @param key1 key1.
     * @param key2 key2.
     * @return count.
     */
	public int rangeKey(CleverSIDC cs, int key1, int key2) {
		return cs.dataStructure.rangeKey(key1, key2);
	}

    /**
     * boolean if it contains a given key.
     * @param cs CleverSIDC object.
     * @param key Key.
     * @return boolean if it does contain.
     */
	public boolean contains(CleverSIDC cs, int key) {
		return cs.dataStructure.contains(key);
	}

    /**
     * generates random 8 digit to insert in CleverSIDC
     * @param cs CleverSIDC object.
     * @return integer generated.
     */
	public static int generate(CleverSIDC cs) {
		int m = (int) Math.pow(10, 7);
		int randomNum;
		randomNum = m + new Random().nextInt(9 * m);
		while (cs.contains(cs, randomNum)) {
			randomNum = m + new Random().nextInt(9 * m);
		}
		return randomNum;
	}
	
	/**
     	* Driver function to read file, take user input to feed into clever SIDC.
	*
     	*/
	public static void main(String[] args) throws FileNotFoundException {
		Scanner inputSc = new Scanner(System.in);
		CleverSIDC cs = new CleverSIDC();
		System.out.println("Please provide threshold value for SIDC : ");
		int threshold = inputSc.nextInt();
		cs.SetSIDCThreshold(threshold);
		System.out.println("Provide Y to read the input from file or else provide N.");
		String inputType = inputSc.next();

		if (inputType.equalsIgnoreCase("Y")) {
			System.out.println("Please provide file name to read the data : ");
			String inputFileStr = inputSc.next();
			File currentWorkingDir = new File(System.getProperty("user.dir"));
			File inputFile = new File(currentWorkingDir.getAbsolutePath() + "/NASTA_test_files/" + inputFileStr);
			Scanner sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				Integer dataToInsert = Integer.parseInt(data);
				cs.add(cs, dataToInsert, "S_" + dataToInsert);
			}
			sc.close();
			System.out.println();
			System.out.println();
			System.out.println("Previous Key for 78829726 > " + cs.prevKey(cs, 78829726));
			System.out.println("Next Key for 40350612 > " + cs.nextKey(cs, 40350612));
			System.out.println("Existence of 89105565 > " + cs.contains(cs, 89105565));
			System.out.println("Removing 89105565");
			cs.remove(cs, 89105565);
			System.out.println("Existence of 89105565 > " + cs.contains(cs, 89105565));
			System.out.println("Existence of 83747069 > " + cs.contains(cs, 83747069));
			System.out.println("Size of the Student tracking system > " + cs.sizeCount);
			System.out.println("Number of keys between 22439726 and 69894475 > ");
			System.out.println(cs.rangeKey(cs, 22439726, 69894475));
		} else {
			System.out.println("Provide Y to randomly generate the data or N to insert the data : ");
			String randomOrUserGiven = inputSc.next();
			if (randomOrUserGiven.equalsIgnoreCase("Y")) {
				System.out.println("Randomly generating " + threshold + " student IDs and trying to insert them.");
				for (int i = 0; i < threshold; i++) {
					int studentID = generate(cs);
					System.out.println("Generated student ID : " + studentID);
					cs.add(cs, studentID, "S_" + studentID);
				}
				System.out.println("Size of the Student tracking system > " + cs.sizeCount);
				System.out.println("All keys present in the Student tracking system >");
				cs.allKeys(cs);
			} else {
				System.out.println("Provide student details for " + threshold + " number of records.");
				for (int i = 0; i < threshold; i++) {
					System.out.println("Enter student ID : ");
					int studentID = inputSc.nextInt();
					System.out.println("Enter student Info in format(Family Name, First Name, and DOB) : ");
					String studentInfo = inputSc.next();
					cs.add(cs, studentID, studentInfo);
				}
				System.out.println("All keys present in the Student tracking system >");
				cs.allKeys(cs);
				System.out.println();
				System.out.println("Get values for student ID 89898989 returns : " + cs.getValues(cs, 89898989));
			}
		}
		inputSc.close();
	}
}
