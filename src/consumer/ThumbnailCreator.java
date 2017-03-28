package consumer;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class ThumbnailCreator implements Runnable {

    private Thread t;
    private String threadName = "[ThumbnailCreator]";

    ThumbnailCreator(){
        System.out.println(threadName + " Creating.");
    }

    public void run(){
        try{
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            System.out.println(threadName + " Interrupted.");
        }
    }

    public void start(){
        System.out.println(threadName + " Starting." );
        if (t == null) {
            t = new Thread (this);
            t.start ();
        }
    }
}
