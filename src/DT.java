public interface DT {
    public void addElement(int ID, String value);
    public void remove(int ID);
    public void allKeys();
    public int nextKey(int ID);
    public int prevKey(int ID);
    public int rangeKey(int k1, int k2);
    public String getValues(int k1);
}
