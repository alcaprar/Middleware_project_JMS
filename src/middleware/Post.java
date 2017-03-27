package middleware;

// Import required java libraries
import java.io.*;
import java.lang.reflect.Array;
import javax.servlet.*;
import javax.servlet.http.*;

public class Post extends HttpServlet{

    private String message;

    public void init() throws ServletException
    {
        // Do required initialization
        message = "Hello World";
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        //System.out.println("ciao");
        // Set response content type
        //response.setContentType("text/html");

        String username = request.getParameter("username");

        //recover posts from queue
        Array posts = null;

        request.setAttribute("posts", posts);

        response.sendRedirect(request.getContextPath()+"/timeline?username="+username);
    }

    public void destroy()
    {
        // do nothing.
    }
}