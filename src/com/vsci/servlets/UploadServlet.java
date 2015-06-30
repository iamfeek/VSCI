package com.vsci.servlets;

import com.vsci.methods.CryptoException;
import com.vsci.methods.EncryptUtility;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 5151364782256414593L;

    private static final String DATA_DIRECTORY = "upload_temp";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = -1;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return;
        } else {
            //getServletContext().getRequestDispatcher("/done.jsp").forward(request, response);
            response.setStatus(200);
            handler(request, response);
        }


    }


    private void handler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

//        // Sets the directory used to temporarily store files that are larger
//        // than the configured size threshold. We use temporary directory for
//        // java
        File repository = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");

        factory.setRepository(repository);

//        // Sets the size threshold beyond which files are written directly to
//        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;

//        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        String fileName = "", password = "";

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();


            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("fileName"))
                        fileName = item.getString() + ".txt";
                    else
                        password = item.getString();
                } else {
                    try {
                        //Writes Test.txt that has encrypted values in it and will write to DATA_DIRECTORY in server root
                        System.out.println("File name: " + fileName);
                        System.out.println("Password: " + password);
                        EncryptUtility.encrypt(EncryptUtility.buildKey(password), item, new File(uploadFolder + File.separator + fileName));
                    } catch (CryptoException ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }

            // displays done.jsp page after upload finished
        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}