/*
Building element of Linked List.
 */
class Node{
    int data;
    String val;
    Node next;

    /**
     * Constructor.
     * @param data data to be inserted.
     * @param v value to be inserted.
     */
    public Node(int data, String v) {
        this.data = data;
        this.next = null;
        this.val = v;
    }

    /**
     * Constructor.
     * @param data data.
     * @param n Next node.
     * @param v value.
     */
    public Node(int data, Node n, String v){
        this.data = data;
        this.next = n;
        this.val = v;
    }

    /**
     * get the data of node.
     * @return data.
     */
    public int getData() {
        return data;
    }

    /**
     * set the data.
     * @param data data.
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * sets the info of node.
     * @param val sets value of node.
     */
    public void setVal(String val) {
        this.val = val;
    }

    /**
     * returns value of node.
     * @return value of node.
     */
    public String getVal() {
        return val;
    }
}

/**
 * @author Jhanvi Arora, Zalak Patel.
 *
 * Class implementing LinkedList DataStructures and its operations.
 */
class LinkedList implements DT{
    Node head;
    int size = 0;

    /**
     * Constructor.
     */
    public LinkedList(){
        head = null;
    }

    /**
     * Constructor.
     * @param l1 Linkedlist
     */
    public LinkedList(LinkedList l1){

        if(l1.head==null){
            head = null;
        }else {
            head = null;
            Node t1, t2, t3;
            t2 = t3 = null;
            t1 = l1.head;
            while(t1 != null)
            {
                if (head == null)	// this happens only once
                {
                    t2 = new Node(t1.getData(), null);
                    head = t2;
                }
                else
                {
                    t3 = new Node(t1.getData(), null);
                    t2.next = t3;
                    t2 = t3;
                }
                t1 = t1.next;
            }
            t2 = t3 = null;
        }
    }

    /**
     * Clones and returns the LinkedList object.
     * @return
     */
    public LinkedList clone(){
        return new LinkedList(this);
    }

    /**
     * returns the size of Linkedlist
     * @return size of linkedlist.
     */
    public int size(){ return size;}
    
    @Override
    public boolean contains(int studentID) {
    	return find(studentID) != null;
    }

    /**
     * finds an element in the linked list.
     * @param x ID to find.
     * @return the node.
     */
    public Node find(int x){
        if(head == null){
            System.out.println("No node exists in the list!");
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
                size++;
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
                size++;
            }
        }
    }

    /**
     * Deletes node from root.
     * @return deleted node.
     */
    public Node deleteStart(){
        if(head == null){
            System.out.println("Head Null!");
            return null;
        }
        if(head.next == null){
            Node temp = head;
            head = null;
            size--;
            return temp;
        }
        Node temp = head;
        head = head.next;
        size--;
        return temp;
    }

    @Override
    public void remove(int ID) {
        Node temp = head, prev = null;
        if (temp != null && temp.data == ID) {
            head = temp.next;
            size--;
            return;
        }

        while (temp != null && temp.data != ID) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == null)
            return;
        prev.next = temp.next;
        size--;
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
        if(current != null && current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return current == null || current.next == null ? -1 :current.next.getData();
    }

    @Override
    public int prevKey(int ID) {
        Node current = head;
        while(current!=null && current.next!=null && current.next.getData()!=ID){
            current = current.next;
        }
        if(current != null && current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return current == null ? -1 : current.getData();
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
        if(current != null && current.next==null){
            System.out.println("Key not Found!");
            return -1;
        }
        return count-1;
    }

    @Override
    public String getValues(int k1) {
        Node temp = this.find(k1);
        if(temp != null) {
        	return temp.getVal();
        } else {
        	System.out.println("Student Information with given student ID is not found.");
        	return null;
        }
        
    }
}
