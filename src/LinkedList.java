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
class LinkedList implements DT{
    Node head;
    int size = 0;
    public LinkedList(){
        head = null;
    }
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
    public LinkedList clone(){
        return new LinkedList(this);
    }
    public int size(){ return size;}
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
