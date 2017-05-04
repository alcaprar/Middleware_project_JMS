package consumer;

import consumer.listener.ThumbnailCreatorListener;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class ThumbnailCreator implements Runnable{
    private Thread t;
    private String threadName = "[ThumbnailCreator]";

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static public String NEW_IMAGES_TOPIC = "NewImagesTopic";

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public ThumbnailCreator(){
        System.out.println(threadName + " Creating.");
    }

    public void run(){
        try{
            // create and start connection
            ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            conn = cf.createQueueConnection();
            conn.start();
            // create queue session
            ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // get the Topic object
            Topic newImageTopic = (Topic) ctx.lookup(NEW_IMAGES_TOPIC);

            // create consumer
            MessageConsumer receiver = ses.createConsumer(newImageTopic);

            // create listener object
            ThumbnailCreatorListener listener = new ThumbnailCreatorListener();

            // register the listener object with consumer
            receiver.setMessageListener(listener);

            System.out.println(threadName + "is ready, waiting for messages...");
            System.out.println("...press Ctrl+c to shutdown...");
            while(true){
                Thread.sleep(1000);
            }

        }catch (Exception e){
            System.out.println(threadName + " Stopped.");
            e.printStackTrace();
        }
    }

    public void start(){
        System.out.println(threadName + " Starting.");
        if (t == null) {
            t = new Thread (this);
            t.start ();
        }
    }
}
