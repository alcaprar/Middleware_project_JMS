package consumer;


import javax.jms.*;
import javax.naming.InitialContext;

import consumer.listener.TimelineUpdaterListener;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class TimelineUpdater implements Runnable{

    private Thread t;
    private String threadName = "[TimelineUpdater]";

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static public String NEW_POSTS_TOPIC = "NewPostsTopic";

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public TimelineUpdater(){
        System.out.println(threadName + " Creating.");
    }

    public void run(){
        try{
            //1)Create and start connection
            ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            conn = cf.createQueueConnection();
            conn.start();
            //2) create queue session
            ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //3) get the Queue object
            Topic timelineUpdaterQueue = (Topic) ctx.lookup(NEW_POSTS_TOPIC);

            //4)create QueueReceiver
            MessageConsumer receiver = ses.createConsumer(timelineUpdaterQueue);

            //5) create listener object
            TimelineUpdaterListener listener = new TimelineUpdaterListener();

            //6) register the listener object with receiver
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

    public static void main(String args[]){
        TimelineUpdater t = new TimelineUpdater();
        t.start();
    }
}
