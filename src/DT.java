/**
 * Common interface consisting declaration of functions to be performed on
 * system
 *
 */
public interface DT {
	/**
	 * Adds new Elements with student ID and student information
	 * 
	 * @param student ID
	 * @param student information
	 */
	public void addElement(int studentID, String value);

	/**
	 * Removes element with student ID passed
	 * 
	 * @param studentID student ID
	 */
	public void remove(int studentID);

	/**
	 * Returns all elements present
	 *
	 */
	public void allKeys();

	/**
	 * Finds successor student ID of given student ID
	 * 
	 * @return successor student ID
	 */
	public int nextKey(int studentID);

	/**
	 * Finds predecessor of the given student ID
	 *
	 * @return predecessor student ID
	 */
	public int prevKey(int studentID);

	/**
	 * Finds number of keys present between provided two keys
	 *
	 * @param key1 range start
	 * @param key2 range end
	 * @return number of nodes present
	 */
	public int rangeKey(int k1, int k2);

	/**
	 * Gets student information corresponding to student ID passed
	 *
	 * @param student ID
	 * @return student information
	 */
	public String getValues(int k1);

	/**
	 * Checks if student with given student ID already exists in the system or not
	 */
	public boolean contains(int studentID);
}
