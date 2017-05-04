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

            String imageName = msg.getString("imageName");

            if(imageName!=null){
                System.out.println("[IS] Received image:" + imageName);

                //get the bytes
                byte[] imageContent = msg.getBytes("image");

                //store the image to the hard disk
                FileOutputStream fos = new FileOutputStream(uploadPath+imageName);
                fos.write(imageContent);
                fos.close();
            }else{
                System.out.println("[IS] ImageName is missing.");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
