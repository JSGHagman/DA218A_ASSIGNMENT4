package Main;

public class Writer implements Runnable {
    private BoundedBuffer buffer;
    private String[] text;
    private Thread thread;
    public Writer(BoundedBuffer buffer, String[] text){
       this.buffer = buffer;
       this.text = text;
    }

    public void startThread(){
       if(thread == null){
        thread = new Thread(this);
        thread.start();
        }
    }

   public Thread getThread(){
        return thread;
   }

    @Override
    public void run() {
        int stringLength = text.length;
        for(int i = 0; i<stringLength; i++){
            String s = text[i];
            buffer.put(s);
        }
        thread = null;
    }
}
