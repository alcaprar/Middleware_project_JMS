package consumer.listener;

import javax.jms.*;
import javax.naming.InitialContext;

import entity.Post;
import entity.User;

import java.util.ArrayList;

import static servlet.Post.CONNECTION_FACTORY;

/**
 * Created by vsywn9 on 4/3/2017.
 */
public class TimelineUpdaterListener implements MessageListener{


    public void onMessage(Message m) {
        try{
            MapMessage msg = (MapMessage) m;

            String username = msg.getString("username");
            String text = msg.getString("text");
            String time = msg.getString("time");
            String imageName = msg.getString("imageName");

            Post post = new Post(username, text, time, imageName);

            System.out.println("Message received from:" + username);
            System.out.println(text);

            User user = new User();
            user.load(username);
            System.out.println(user.getFans());
            for(String fan: user.getFans()){
                User fanUser = new User();
                fanUser.load(fan);
                fanUser.addPostToTimeline(post);
            }
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}
