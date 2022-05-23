package MainP2;

public class BoundedBuffer {
    private String[] items;
    private Status[] status;
    private String searchString, replaceString;
    private int positionRead, positionWrite, positionFind, max;

    public BoundedBuffer(int size, String searchString, String replaceString) {
        this.searchString = searchString;
        this.replaceString = replaceString;
        this.items = new String[size];
        this.status = new Status[size];
        for (int i = 0; i < status.length; i++) {
            status[i] = Status.Empty;
        }
    }

    public void put(String s) {
        synchronized (this) {
            while (status[positionWrite] != Status.Empty) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException exception) {

        }
        status[positionWrite] = Status.New;

        synchronized (this) {
        items[positionWrite++] = s;
        positionWrite %= items.length;
            notifyAll();
        }
    }

    public String get() {
        String s = "";

        synchronized (this) {
            while (status[positionRead] != Status.Checked) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException exception) {

        }

        s = items[positionRead];


        synchronized (this) {
        status[positionRead++] = Status.Empty;
        positionRead %= items.length;
        notifyAll();
        }
        return s;
    }

    public void change() {
        synchronized (this) {
            while (status[positionFind] != Status.New) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException exception) {

        }
        items[positionFind] = items[positionFind].replace(searchString, replaceString);
        synchronized (this) {
        status[positionFind] = Status.Checked;
        positionFind = (positionFind + 1) % items.length;
        notifyAll();
        }
    }
}

