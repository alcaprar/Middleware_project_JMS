package consumer.listener;

import javax.jms.*;
import javax.naming.InitialContext;

import data.*;

import java.util.ArrayList;

import static servlet.Post.CONNECTION_FACTORY;

/**
 * Created by vsywn9 on 4/3/2017.
 */
public class TimelineUpdaterListener implements MessageListener{

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public void onMessage(Message m) {
        try{
            MapMessage msg = (MapMessage) m;

            String username = msg.getString("username");
            String text = msg.getString("text");
            String time = msg.getString("time");
            String imageName = msg.getString("imageName");

            System.out.println("Message received from:" + username);
            System.out.println(text);

            Users users = new Users();

            ArrayList<String> followers = users.getFollowers(username);

            ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            conn = cf.createQueueConnection();
            conn.start();
            //2) create queue session
            ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            for(String follower: followers){
                //create MapMessage object
                MapMessage message = ses.createMapMessage();
                message.setString("username", username);
                message.setString("text", text);
                message.setString("time", time);
                if(imageName!=null){
                    message.setString("imageName",  imageName);
                }

                //send the post to the queue to forward it to the right followers
                //3) get the Queue object
                String userQueue = follower+"Queue";
                Queue timelineUpdaterQueue = (Queue) ctx.lookup(userQueue);
                //4)create QueueSender object
                QueueSender timelineUpdaterSender = ses.createSender(timelineUpdaterQueue);
                timelineUpdaterSender.send(message);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
