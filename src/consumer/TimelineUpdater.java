package consumer;

import javax.jms.*;
import javax.naming.*;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class TimelineUpdater implements Runnable{

    private Thread t;
    private String threadName = "[TimelineUpdater]";

    public static Context ictx = null;

    TimelineUpdater(){
        System.out.println(threadName + " Creating.");
    }

    public void run(){
        try{

            InitialContext ictx=new InitialContext();
            TopicConnectionFactory cf =(TopicConnectionFactory) ictx.lookup("jms/DurableConnectionFactory");
            TopicConnection con= cf.createTopicConnection();
            con.start();

            Connection cnx = cf.createConnection();
            Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic posts = null;
            MessageConsumer recv = sess.createConsumer(posts);
            cnx.start();
            Message message = recv.receive(10000);
            while (message!=null){
                System.out.println(threadName+" Received a msg.");
                message = recv.receive(10000);
            }
            cnx.close();
        }catch (Exception e){
            System.out.println(threadName + " Stopped.");
            System.out.println(e);
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
