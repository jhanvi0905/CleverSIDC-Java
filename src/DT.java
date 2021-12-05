public interface DT {
    public void addElement(int studentID, String value);
    public void remove(int studentID);
    public void allKeys();
    public int nextKey(int studentID);
    public int prevKey(int studentID);
    public int rangeKey(int k1, int k2);
    public String getValues(int k1);
    public boolean contains(int studentID);
}
