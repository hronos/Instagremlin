/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import java.nio.ByteBuffer;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;


/**
 *
 * @author dlennart
 */
@WebServlet(name = "Avatar", urlPatterns = {"/Avatar", "/Avatar/*"})
@MultipartConfig

public class Avatar extends HttpServlet {
    Cluster cluster=null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    private void DisplayAvatar(HttpServletResponse response, String user) throws ServletException, IOException {
        
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        
        Pic p = tm.getAvatar(user);
        System.out.println("Length = " + p.getLength());
        
        OutputStream out = response.getOutputStream();

        response.setContentType("image/png");
        //response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        DisplayAvatar(response, args[2]); 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        User usr = new User();
        for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());

            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            if (i > 0) {
                byte[] b = new byte[i + 1];
                is.read(b);
                System.out.println("Length : " + b.length);
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                tm.insertAvatar(b, type, filename, args[2]);
                is.close();
            }
            request.setAttribute("msg", "avatar uploaded");
            RequestDispatcher rd = request.getRequestDispatcher("/msg.jsp");
            rd.forward(request, response);
        }

    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
