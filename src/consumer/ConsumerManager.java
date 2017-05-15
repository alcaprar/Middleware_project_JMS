package consumer;

import entity.User;

import java.util.ArrayList;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class ConsumerManager {

    public static void main(String args[]) {
        try {
            User ale = new User();
            ale.setUsername("ale");
            ArrayList<String> aleFollowers = new ArrayList<String>();
            aleFollowers.add("alex");
            aleFollowers.add("jorge");
            ale.setFollowers(aleFollowers);
            ale.save();
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User alex = new User();
            alex.setUsername("alex");
            ArrayList<String> alexFollowers = new ArrayList<String>();
            alexFollowers.add("ale");
            alex.setFollowers(alexFollowers);
            alex.save();
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User jorge = new User();
            jorge.setUsername("jorge");
            ArrayList<String> jorgeFollowers = new ArrayList<String>();
            jorgeFollowers.add("ale");
            jorge.setFollowers(jorgeFollowers);
            jorge.save();
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            User sam = new User();
            sam.setUsername("sam");
            ArrayList<String> samFollowers = new ArrayList<String>();
            samFollowers.add("ale");
            samFollowers.add("alex");
            samFollowers.add("jorge");
            sam.setFollowers(samFollowers);
            sam.save();
        }catch (Exception e){
            e.printStackTrace();
        }
        ThumbnailCreator thumbnailCreator = new ThumbnailCreator();
        thumbnailCreator.start();

        ImageStorer imageStorer = new ImageStorer();
        imageStorer.start();

        TimelineUpdater timelineUpdater = new TimelineUpdater();
        timelineUpdater.start();
    }
}
