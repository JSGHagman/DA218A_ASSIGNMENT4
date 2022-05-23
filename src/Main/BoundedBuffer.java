package Main;

public class BoundedBuffer {
    private String[] items;
    private Status[] status;
    private String searchString, replaceString;
    private int positionRead, positionWrite, positionFind, max;

    /**
     * This is the constructor for this class.
     * @param size - the size of the buffer.
     * @param searchString - The string to search for.
     * @param replaceString - The string the user wishes to replace the searched string with.
     */
    public BoundedBuffer(int size, String searchString, String replaceString) {
        this.searchString = searchString;
        this.replaceString = replaceString;
        this.items = new String[size];
        this.status = new Status[size];
        for (int i = 0; i < status.length; i++) {
            status[i] = Status.Empty;
        }
    }

    /**
     * This method takes a string as parameter and places it in the buffer
     * The status is then updated to new
     * If the position to write is not empty the thread will wait.
     * @param s
     */
    public synchronized void put(String s) {
            while (status[positionWrite] != Status.Empty) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            status[positionWrite] = Status.New;
            items[positionWrite++] = s;
            positionWrite %= items.length;
            notifyAll();
    }

    /**
     * This method returns the string to the writer which then places it in a final string list
     * which is later displayed in the gui
     * If the position to get is not checked, the thread will wait inside the while loop.
     * The status is then updated to empty
     * @return String
     */
    public synchronized String get() {
            while (status[positionRead] != Status.Checked) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String string = items[positionRead];
            status[positionRead++] = Status.Empty;
            positionRead %= items.length;
            notifyAll();
            return string;
    }

    /**
     * This method replaces the seacrh string with the replace string
     * The status is then updated to checked
     * Will only change the value of the position in the array if the status has been set to New
     */
    public synchronized void change() {
            while (status[positionFind] != Status.New) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[positionFind] = items[positionFind].replace(searchString, replaceString);
            status[positionFind] = Status.Checked;
            positionFind = (positionFind + 1) % items.length;
            notifyAll();
        }
    }

