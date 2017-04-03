package consumer.listener;

import javax.jms.*;
/**
 * Created by vsywn9 on 4/3/2017.
 */
public class TimelineUpdaterListener implements MessageListener{
    public void onMessage(Message m) {
        try{
            MapMessage msg = (MapMessage) m;

            System.out.println("Message received from:" + msg.getString("username"));
            System.out.println(msg.getString("messageText"));
        }catch(JMSException e){System.out.println(e);}
    }
}
