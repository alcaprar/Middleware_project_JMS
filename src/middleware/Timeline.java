package middleware;

// Import required java libraries
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

import middleware.Users;

public class Timeline extends HttpServlet{

    public void init() throws ServletException
    {
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");


        //recover posts from queue
        Array posts = null;

        Users users = new Users();
        ArrayList<String> following = users.getFollowing(username);

        request.setAttribute("username", username);
        request.setAttribute("following", following);
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/WEB-INF/timeline.jsp").forward(request, response);
    }

    public void destroy()
    {
        // do nothing.
    }
}