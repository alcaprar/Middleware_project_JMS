package consumer.listener;

import net.coobird.thumbnailator.Thumbnails;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by vsywn9 on 4/3/2017.
 */
public class ThumbnailCreatorListener implements MessageListener{

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

                String filename = uploadPath+imageName;
                String thumbnailFilename = uploadPath+"tb-"+imageName;

                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(imageContent);
                fos.close();

                //create thumbnail
                Thumbnails.of(new File(filename)).size(100,100).toFile(new File(thumbnailFilename));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
