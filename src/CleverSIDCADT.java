import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

class CleverSIDC {
	DT dataStructure;
	int sizeCount = 0;
	int sizeThreshold;
	int flag = 0;

	public void SetSIDCThreshold(int size) {
		sizeThreshold = size;
		dataStructure = size <= 1000 ? new LinkedList() : new AVLTree();
	}

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

	public void convertToAVL() {
		AVLTree temp = new AVLTree();
		LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
		while (ll.size() != 0) {
			Node tempNode = ll.deleteStart();
			temp.insertNode(tempNode.getVal(), tempNode.getData());
		}
		dataStructure = temp;
		System.out.println("Linked List converted to AVL TREE!");
		temp.allKeys();
	}

	public void allKeys(CleverSIDC cs) {
		cs.dataStructure.allKeys();
	}

	public void remove(CleverSIDC cs, int key) {
		if (sizeCount == 1001) {
			convertToLL();
		}
		cs.dataStructure.remove(key);
		sizeCount--;
	}

	public void convertToLL() {
		LinkedList lTemp = new LinkedList();
		AVLTree alTemp = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
		while (alTemp.getTotalNodes() != 0) {
			AVLTree.AVLNode toDelete = alTemp.deleteRoot();
			lTemp.addElement(toDelete.getStudentID(), toDelete.getStudentInfo());
		}
		dataStructure = lTemp;
		System.out.println("AVL Tree converted to LinkedList");
		lTemp.allKeys();
	}

	public String getValues(CleverSIDC cs, int key) {
		return cs.dataStructure.getValues(key);
	}

	public int nextKey(CleverSIDC cs, int key) {
		return cs.dataStructure.nextKey(key);
	}

	public int prevKey(CleverSIDC cs, int key) {
		return cs.dataStructure.prevKey(key);
	}

	public int rangeKey(CleverSIDC cs, int key1, int key2) {
		return cs.dataStructure.rangeKey(key1, key2);
	}

	public boolean contains(CleverSIDC cs, int key) {
		return cs.dataStructure.contains(key);
	}

	public static int generateRandomDigits(CleverSIDC cs) {
		int m = (int) Math.pow(10, 7);
		int randomNum;
		randomNum = m + new Random().nextInt(9 * m);
		while (cs.contains(cs, randomNum)) {
			randomNum = m + new Random().nextInt(9 * m);
		}
		return randomNum;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner inputSc = new Scanner(System.in);
		CleverSIDC cs = new CleverSIDC();
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
			System.out.println("Previous Key for 99960892 > " + cs.prevKey(cs, 99960892));
			System.out.println("Next Key for 99997635 > " + cs.nextKey(cs, 99997635));
			System.out.println("Existence of 65862 > " + cs.contains(cs, 65862));
			System.out.println("Removing 65862");
			cs.remove(cs, 65862);
			System.out.println("Existence of 65862 > " + cs.contains(cs, 65862));
			System.out.println("Existence of 83747069 > " + cs.contains(cs, 83747069));
			System.out.println("Existence of 21084975 > " + cs.contains(cs, 21084975));
			System.out.println("Size of the Student tracking system > " + cs.sizeCount);
			System.out.println("Number of keys between 03326261 and 03322659 > " + cs.rangeKey(cs, 3326261, 33237174));
		} else {
			System.out.println("Please provide threshold value for SIDC : ");
			int threshold = inputSc.nextInt();
			cs.SetSIDCThreshold(threshold);
			System.out.println("Provide Y to randomly generate the data or N to insert the data : ");
			String randomOrUserGiven = inputSc.next();
			if (randomOrUserGiven.equalsIgnoreCase("Y")) {
				System.out.println("Randomly generating " + threshold + " student IDs and trying to insert them.");
				for (int i = 0; i < threshold; i++) {
					int studentID = generateRandomDigits(cs);
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
			}
		}
		inputSc.close();
	}
}
