package middleware;

import consumer.ThumbnailCreator;

import java.io.*;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

public class Post extends HttpServlet{

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static public String TIMELINE_UPDATE_QUEUE = "TimelineUpdaterQueue";
    final static public String THUMBNAIL_CREATOR_QUEUE = "ThumbnailCreatorQueue";
    final static public String IMAGE_STORER_QUEUE = "ImageStorerQueue";

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public void init() throws ServletException{
        try {
            //1)Create and start connection
            ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            conn = cf.createQueueConnection();
            conn.start();
            //2) create queue session
            ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String text = request.getParameter("new_message");

        out.println("Post from: " + username);
        out.println(text);

        if(username!=null){
            try{
                //create MapMessage object
                MapMessage msg = ses.createMapMessage();
                msg.setString("username", username);
                msg.setString("messageText", text);

                //send the post to the queue to forward it to the right followers
                //3) get the Queue object
                Queue timelineUpdaterQueue = (Queue) ctx.lookup(TIMELINE_UPDATE_QUEUE);
                //4)create QueueSender object
                QueueSender timelineUpdaterSender = ses.createSender(timelineUpdaterQueue);
                timelineUpdaterSender.send(msg);

                //send the image to the queue to save it
                //3) get the Queue object
                Queue imageStorerQueue = (Queue) ctx.lookup(IMAGE_STORER_QUEUE);
                //4)create QueueSender object
                QueueSender imageStoreSender = ses.createSender(imageStorerQueue);
                imageStoreSender.send(msg);

                //send the image to the queue to minize it
                //3) get the Queue object
                Queue thumbnailCreatorQueue = (Queue) ctx.lookup(THUMBNAIL_CREATOR_QUEUE);
                //4)create QueueSender object
                QueueSender thumbnailCreatorSender = ses.createSender(thumbnailCreatorQueue);
                thumbnailCreatorSender.send(msg);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void destroy()
    {
        // do nothing.
    }
}