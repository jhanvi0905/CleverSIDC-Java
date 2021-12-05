import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CleverSIDC{
    Object dataStructure;
    int sizeCount = 0;
    int sizeThreshold;
    int flag = 0;

    public void SetSIDCThreshold (int size){
        sizeThreshold = size;
        dataStructure = new LinkedList();
    }
    public void add(CleverSIDC c, int ID, String value){
        if(sizeCount == 1000){
            convertToAVL();
        }
        if(c.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.addElement(ID, value);
            sizeCount++;
        }else{
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            al.addElement(ID, value);
            sizeCount++;
        }
    }
    public void convertToAVL(){
        AVLTree temp = new AVLTree();
        LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
        LinkedList tempList = ll.clone();
        while(ll.size()!=0){
            Node toDelete = ll.deleteStart();
            temp.insertNode(toDelete.getVal(), toDelete.getData());
        }
        dataStructure = temp;
        System.out.println("Linked List converted to AVL TREE!");
        temp.allKeys();
    }

    public void convertToLL(){
        LinkedList lTemp = new LinkedList();
        AVLTree alTemp = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
        while(alTemp.getTotalNodes()!=0){
            AVLTree.AVLNode toDelete = alTemp.deleteRoot();
            lTemp.addElement(toDelete.getStudentID(), toDelete.getStudentInfo());
        }
        dataStructure = lTemp;
        System.out.println("AVL Tree converted to LinkedList");
        lTemp.allKeys();
    }

    public void allKeys(CleverSIDC cs){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.allKeys();
        }else{
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            al.allKeys();
        }
    }

    public void remove(CleverSIDC cs,int key){
        if(sizeCount==1001){
            convertToLL();
        }
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.remove(key);
            sizeCount--;
        }else{
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            al.remove(key);
            sizeCount--;
        }
    }

    public String getValues(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.getValues(key);
        }
        else{
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            return al.getValues(key);
        }
    }
    public int nextKey(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.nextKey(key);
        }else {
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            return al.nextKey(key);
        }
    }

    public int prevKey(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList) {
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.prevKey(key);
        }else{
            AVLTree al = dataStructure instanceof AVLTree ? ((AVLTree) dataStructure) : null;
            return al.prevKey(key);
        }
    }
    public int rangeKey(CleverSIDC cs, int key1, int key2){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.rangeKey(key1, key2);
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("C:\\Users\\Jhanvi Arora\\Desktop\\project2\\NASTA_test_files\\NASTA_test_fileTry.txt");
        CleverSIDC cs = new CleverSIDC();
        cs.SetSIDCThreshold(50000);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            Integer dataToInsert = Integer.parseInt(data);
            cs.add(cs, dataToInsert, "empty");
        }
        sc.close();
        cs.allKeys(cs);
        System.out.println();
        System.out.println();
        System.out.println(cs.prevKey(cs, 99960892));
        System.out.println(cs.nextKey(cs, 99997635));
        cs.remove(cs, 65862);
        cs.remove(cs, 182965);
    }
}