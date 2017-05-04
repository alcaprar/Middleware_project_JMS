package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import entity.User;

public class Timeline extends HttpServlet{

    public void init() throws ServletException{
        // do nothing.
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");

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