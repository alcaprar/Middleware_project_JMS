package middleware;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Post extends HttpServlet{


    public void init() throws ServletException
    {
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");

        //send the post to the queue to forward it to the right followers

        //send the image to the queue to save it

        //send the image to the queue to minize it

        response.sendRedirect(request.getContextPath()+"/timeline?username="+username);
    }

    public void destroy()
    {
        // do nothing.
    }
}