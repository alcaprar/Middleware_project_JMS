package servlet;

// Import required java libraries
import java.io.*;
import java.util.ArrayList;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

import entity.User;


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
        String username = request.getParameter("username");

        System.out.println(username + " connected.");
        //recover posts from mongodb
        User user = new User();
        user.load(username);

        request.setAttribute("username", username);
        request.setAttribute("posts", user.getTimeline());
        request.getRequestDispatcher("/WEB-INF/timeline.jsp").forward(request, response);
    }

    public void destroy()
    {
        // do nothing.
    }
}