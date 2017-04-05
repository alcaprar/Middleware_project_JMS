package consumer.listener;

import data.Users;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.ArrayList;

import static middleware.Post.CONNECTION_FACTORY;

/**
 * Created by vsywn9 on 4/3/2017.
 */
public class ImageStorerListener implements MessageListener{


    public void onMessage(Message m) {
        try{
            MapMessage msg = (MapMessage) m;

            String username = msg.getString("username");
            String text = msg.getString("text");
            String time = msg.getString("time");
            String imageName = msg.getString("imageName");


            System.out.println("[IS] Message received from:" + username);
            System.out.println(text);
            System.out.println(imageName);

            if(imageName!=null){
                //save image
                byte[] imageContent = msg.getBytes("image");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
