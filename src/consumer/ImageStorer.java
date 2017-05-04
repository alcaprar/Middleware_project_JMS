package consumer;

import consumer.listener.ImageStorerListener;
import consumer.listener.TimelineUpdaterListener;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class ImageStorer implements Runnable{
    private Thread t;
    private String threadName = "[ImageStorer]";

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static public String NEW_IMAGES_TOPIC = "NewImagesTopic";

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public ImageStorer(){
        System.out.println(threadName + " Creating.");
    }

    public void run(){
        try{
            // Create and start connection
            ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            conn = cf.createQueueConnection();
            conn.start();
            // create queue session
            ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // get the Topic object
            Topic newImagesTopic = (Topic) ctx.lookup(NEW_IMAGES_TOPIC);

            // create the consumer
            MessageConsumer receiver = ses.createConsumer(newImagesTopic);

            // create listener object
            ImageStorerListener listener = new ImageStorerListener();

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
