package middleware;

// Import required java libraries
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

import data.*;
import data.Post;

import static consumer.TimelineUpdater.CONNECTION_FACTORY;

public class Timeline extends HttpServlet{

    private InitialContext ctx;
    private QueueConnectionFactory cf;
    private QueueConnection conn;
    private QueueSession ses;

    public void init() throws ServletException{
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        Users users = new Users();
        String username = request.getParameter("username");

        if(users.isValidUsername(username)){
            //recover posts from queue
            ArrayList<Post> posts = new ArrayList<Post>();

            try{


                //1)Create and start connection
                ctx = new InitialContext();
                cf = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
                conn = cf.createQueueConnection();
                conn.start();
                //2) create queue session
                ses = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                //3) get the Queue object
                Queue timelineUpdaterQueue = (Queue) ctx.lookup(username+"Queue");

                //4)create QueueReceiver
                QueueReceiver receiver = ses.createReceiver(timelineUpdaterQueue);
                conn.start();
                MapMessage msg = (MapMessage) receiver.receiveNoWait();
                while(msg!=null){
                    Post post = new Post(msg.getString("username"), msg.getString("text"), msg.getString("time"));
                    posts.add(post);
                    conn.start();
                    msg = (MapMessage) receiver.receiveNoWait();
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    try { conn.close(); }
                    catch (JMSException e) { }
                }
            }

            request.setAttribute("username", username);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/WEB-INF/timeline.jsp").forward(request, response);
        }else{
            request.setAttribute("error", "Username not found.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }


    }

    public void destroy()
    {
        // do nothing.
    }
}