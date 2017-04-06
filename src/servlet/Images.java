package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vsywn9 on 4/6/2017.
 */
public class Images extends HttpServlet {

    private String uploadPath = "C:/instatweet/images/";

    public void init(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String thumbnails = request.getParameter("thumbnails");
        String filename = request.getParameter("filename");

        if(thumbnails!=null){
            uploadPath = uploadPath + "/thumbnails/";
        }

        filename = uploadPath + filename;

        ServletContext cntx = request.getServletContext();
        // retrieve mimeType dynamically
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setContentType(mime);
        File file = new File(filename);
        response.setContentLength((int)file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();


    }
}
