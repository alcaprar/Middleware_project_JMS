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

            String imageName = msg.getString("imageName");


            if(imageName!=null){
                System.out.println("[TC] Received image:" + imageName);

                //get the bytes
                byte[] imageContent = msg.getBytes("image");

                //create the new filename for the thumbnail
                String filename = uploadPath+ "temp" + imageName;
                String thumbnailFilename = uploadPath+"tb-"+imageName;

                // store it to the hard disk
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(imageContent);
                fos.close();

                //create thumbnail
                Thumbnails.of(new File(filename)).size(100,100).toFile(new File(thumbnailFilename));
            }else{
                System.out.println("[TC] ImageName is missing.");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
