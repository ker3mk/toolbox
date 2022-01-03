package file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class FileImportServlet extends HttpServlet {


    private final String uploadPath ="/tmp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(404, "Not Found");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {

            List<FileItem> items = upload.parseRequest(req);
            Iterator<FileItem> it = items.iterator();
            while(it.hasNext()){

                FileItem item = it.next();

                if(item.isFormField()){

                    System.out.println("Form field " + item.getFieldName());
                } else {

                    InputStream is = item.getInputStream();
                    if(is == null){
                        System.out.println("File Not Selected...");
                        return ;
                    }

                    File file = new File(uploadPath +File.separator+ item.getName());
                    if(!new File(uploadPath).isDirectory()){
                        new File(uploadPath).mkdirs();
                    }
                    OutputStream bos = new FileOutputStream(file);

                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    while((bytesRead = is.read(buffer, 0, 8192)) != -1){
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.close();
                    is.close();
                    return ;

                }
            }
        } catch (Exception e) {
            System.out.println("Error");
            resp.getWriter().write("Error: " + e.getMessage());
        }


    }

}
