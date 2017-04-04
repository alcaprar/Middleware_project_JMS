package middleware;

import consumer.ThumbnailCreator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

public class Post extends HttpServlet{

    final static public String CONNECTION_FACTORY = "InstaTweetConnectionFactory";
    final static public String NEW_POSTS_TOPIC = "NewPostsTopic";

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
        String imageByte = request.getParameter("image");

        out.println("Post from: " + username);
        out.println(text);

        if(username!=null){
            try{
                //create MapMessage object
                MapMessage msg = ses.createMapMessage();
                msg.setString("username", username);
                msg.setString("text", text);
                msg.setString("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                msg.setString("image", imageByte);

                //send the post to the queue to forward it to the right followers

                Topic newPostsTopic = (Topic) ctx.lookup(NEW_POSTS_TOPIC);

                MessageProducer timelineUpdaterSender = ses.createProducer(newPostsTopic);
                timelineUpdaterSender.send(msg);

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