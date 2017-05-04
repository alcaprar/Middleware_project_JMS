package servlet;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

@MultipartConfig
public class Post extends HttpServlet{

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static private String NEW_POSTS_QUEUE = "NewPostsQueue";
    final static private String NEW_IMAGES_TOPIC ="NewImagesQueue";

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
        //retrieve parameters
        String username = request.getParameter("username");
        String text = request.getParameter("new_message");


        if(username!=null){
            try{
                //create MapMessage object to store the post
                MapMessage msg = ses.createMapMessage();
                msg.setString("username", username);
                msg.setString("text", text);
                msg.setString("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                //send the post to the queue to forward it to the right followers
                Queue newPostsQueue = (Queue) ctx.lookup(NEW_POSTS_QUEUE);

                MessageProducer timelineUpdaterSender = ses.createProducer(newPostsQueue);
                timelineUpdaterSender.send(msg);

                //if the image is sent, send it to the topic
                if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")){
                    // Retrieves <input type="file" name="image">
                    Part filePart = request.getPart("image");
                    // Internet explorer fix.
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    //get the file as an inputStream
                    InputStream fileContent = filePart.getInputStream();
                    //convert the inputStream to a byte array in order to send it in a message (inputStream is not serializable)
                    byte[] targetArray = new byte[fileContent.available()];
                    //fileContent.read(targetArray);

                    //create the message to send
                    MapMessage imageMsg = ses.createMapMessage();
                    imageMsg.setString("imageName", fileName);
                    imageMsg.setBytes("image", targetArray);

                    //send to the topic
                    Topic newImagesTopic = (Topic) ctx.lookup(NEW_IMAGES_TOPIC);
                    MessageProducer newImageSender = ses.createProducer(newImagesTopic);
                    newImageSender.send(imageMsg);
                }

                //send a response back to the browser
                PrintWriter out = response.getWriter();
                out.println("Post added.");

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