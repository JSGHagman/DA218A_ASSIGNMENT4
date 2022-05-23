package Main;

public class Modifier implements Runnable {
    private BoundedBuffer buffer;
    private int length;
    private Thread thread;

    public Modifier(BoundedBuffer buffer, int length) {
        this.buffer = buffer;
        this.length = length;
    }

    public void startThread() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public Thread getThread(){
        return thread;
    }


    /**
     * Runs the thread, calls the method change in the buffer
     */
    @Override
    public void run() {
        for (int i = 0; i < length; i++) {
            buffer.change();
        }
        thread = null;
    }
}
