package MainP2;

import javax.swing.*;
import java.util.ArrayList;

public class Reader implements Runnable{
    private BoundedBuffer buffer;
    private int limit;
    private ArrayList<String> finalTextList;
    private JTextArea textArea;
    private Thread thread;
    public Reader(JTextArea textArea, BoundedBuffer buffer, int limit){
        this.buffer = buffer;
        this.limit = limit;
        this.finalTextList = new ArrayList<>();
        this.textArea = textArea;
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
        finalTextList.clear();
        for(int i = 0; i<limit; i++){
            String s = buffer.get();
            finalTextList.add(s);
            //System.out.println(s);
        }
        textArea.setText(String.join("\n",finalTextList));
        thread = null;
    }
}
