import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
class Node{
    int data;
    String val;
    Node next;

    public Node(int data, String v) {
        this.data = data;
        this.next = null;
        this.val = v;
    }
    public Node(int data, Node n, String v){
        this.data = data;
        this.next = n;
        this.val = v;
    }
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
interface DT{
    public void addElement(int ID, String value);
    public void remove(int ID);
    public void allKeys();
    public int nextKey(int ID);
    public int prevKey(int ID);
    public int rangeKey(int k1, int k2);
    public String getValues(int k1);
}
class LinkedList implements DT{
    Node head = null;


    public boolean insertBefore(int x, int y, String val){
        if(head == null){
            System.out.println("Head Null!");
            return false;
        }else{
            if(head.getData()==y){
                Node newNode = new Node(x,head, val);
                head = newNode;
                newNode = null;
                return true;
            }
            Node current = head;
            while(current.next!= null && current.next.getData()!=y){
                current=current.next;
            }
            System.out.println("Current Data: "+ current.getData());
            if(current.next==null){
                System.out.println("Not Found ");
                return false;
            }
            Node newNode = new Node(x,current.next, val);
            current.next = newNode;
            newNode = null;
            return  true;
        }
    }
    public boolean insertAfter(int x, int y, String val){
        if(head == null){
            System.out.println("Head Null!");
            return false;
        }else{
            Node current = head;
            while(current!= null && current.getData()!=y){
                current=current.next;
            }
            System.out.println("Current Data: "+ current.getData());
            if(current.next==null){
                System.out.println("Not Found ");
                return false;
            }
            Node newNode = new Node(x,current.next, val);
            current.next = newNode;
            newNode = null;
            return  true;
        }
    }
    public Node find(int x){
        if(head == null){
            System.out.println("Head Null!");
            return null;
        }
        if(head.getData() == x){
            return head;
        }else{
            Node current = head;
            while(current!=null ){
                if(current.getData()==x){
                    return current;
                }
                current= current.next;
            }
            return null;
        }
    }
    public boolean replace(int x, int y){
        Node toReplace = find(x);
        if(toReplace!=null){
            toReplace.setData(y);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void addElement(int ID, String value) {
        if(head==null)
        {
            head = new Node(ID,value);
        }
        else
        {
            if(head.getData() > ID)
            {
                Node node = new Node(ID,head, value);
                head=node;
            }
            else {

                Node tempNode = head;
                while(tempNode.next!=null && tempNode.next.getData() < ID)
                {
                    tempNode = tempNode.next;
                }
                Node temp1 = tempNode.next;
                Node temp = new Node(ID ,null, value);
                tempNode.next = temp;
                temp.next=temp1;
            }
        }
    }

    @Override
    public void remove(int ID) {
        Node temp = head, prev = null;
        if (temp != null && temp.data == ID) {
            head = temp.next;
            return;
        }

        while (temp != null && temp.data != ID) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == null)
            return;
        prev.next = temp.next;
    }

    @Override
    public void allKeys() {
        Node current = head;
        while(current!=null){
            System.out.print(current.getData()+"\t");
            current = current.next;
        }
    }

    @Override
    public int nextKey(int ID) {
        Node current = head;
        while(current!=null && current.getData()!=ID){
            current = current.next;
        }
        if(current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return current.next.getData();
    }

    @Override
    public int prevKey(int ID) {
        Node current = head;
        while(current!=null && current.next.getData()!=ID){
            current = current.next;
        }
        if(current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return current.getData();
    }

    @Override
    public int rangeKey(int k1, int k2) {
        Node current = head;
        int flag = 0;
        int count = 0;
        while(current!=null && current.getData()!=k2){
            if(current.getData()==k1){
                flag = 1;
            }
            current = current.next;
            if(flag == 1){
                count++;
            }
        }
        if(current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return count-1;
    }

    @Override
    public String getValues(int k1) {
        Node temp = this.find(k1);
        return temp.getVal();
    }
}
class AVL implements DT{

    @Override
    public void addElement(int ID, String value) {

    }

    @Override
    public void remove(int ID) {

    }

    @Override
    public void allKeys() {

    }

    @Override
    public int nextKey(int ID) {
        return -1;
    }

    @Override
    public int prevKey(int ID) {
        return -1;
    }

    @Override
    public int rangeKey(int k1, int k2) {
        return -1;
    }

    @Override
    public String getValues(int k1) {
        return null;
    }
}
class CleverSIDC{
    Object dataStructure;
    int sizeCount = 0;
    int sizeThreshold;

    public void SetSIDCThreshold (int size){
        sizeThreshold = size;
        dataStructure = new LinkedList();
    }
    public void add(CleverSIDC c, int ID, String value){
        if(sizeCount<sizeThreshold){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.addElement(ID, value);
            sizeCount++;
        }else{

        }
    }
    public void convertToAVL(){
        AVL temp = new AVL();

    }

    public void allKeys(CleverSIDC cs){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.allKeys();
        }
    }

    public void remove(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            ll.remove(key);
        }
    }

    public String getValues(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.getValues(key);
        }
        else{
            return null;
        }
    }
    public int nextKey(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.nextKey(key);
        }
        return -1;
    }

    public int prevKey(CleverSIDC cs,int key){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.prevKey(key);
        }
        return -1;
    }
    public int rangeKey(CleverSIDC cs, int key1, int key2){
        if(cs.dataStructure instanceof LinkedList){
            LinkedList ll = dataStructure instanceof LinkedList ? ((LinkedList) dataStructure) : null;
            return ll.rangeKey(key1, key2);
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("C:\\Users\\Jhanvi Arora\\Desktop\\project2\\NASTA_test_files\\NASTA_test_file1.txt");
        CleverSIDC cs = new CleverSIDC();
        cs.SetSIDCThreshold(1000);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            Integer dataToInsert = Integer.parseInt(data);
            cs.add(cs, dataToInsert, "empty");
        }
        sc.close();
        cs.allKeys(cs);
        System.out.println();
        System.out.println(cs.rangeKey(cs, 332247, 719504));
        System.out.println();
        System.out.println(cs.prevKey(cs, 316150));
        System.out.println(cs.nextKey(cs, 332538));
        cs.remove(cs, 65862);
        cs.allKeys(cs);
    }
}