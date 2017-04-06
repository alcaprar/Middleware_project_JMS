package consumer.listener;

import javax.jms.*;
import java.io.FileOutputStream;

/**
 * Created by vsywn9 on 4/3/2017.
 */
public class ImageStorerListener implements MessageListener{

    private String uploadPath = "C:/instatweet/images/";

    public void onMessage(Message m) {
        try{
            MapMessage msg = (MapMessage) m;

            String username = msg.getString("username");
            String imageName = msg.getString("imageName");


            System.out.println("[IS] Message received from:" + username);
            System.out.println(imageName);

            if(imageName!=null){
                //save image
                byte[] imageContent = msg.getBytes("image");

                FileOutputStream fos = new FileOutputStream(uploadPath+imageName);
                fos.write(imageContent);
                fos.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
