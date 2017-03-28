package consumer;

import consumer.ThumbnailCreator;
import consumer.ImageStorer;
import consumer.TimelineUpdater;

/**
 * Created by vsywn9 on 3/28/2017.
 */
public class ConsumerManager {

    public static void main(String args[]) {
        ThumbnailCreator thumbnailCreator = new ThumbnailCreator();
        thumbnailCreator.start();

        ImageStorer imageStorer = new ImageStorer();
        imageStorer.start();

        TimelineUpdater timelineUpdater = new TimelineUpdater();
        timelineUpdater.start();
    }
}
