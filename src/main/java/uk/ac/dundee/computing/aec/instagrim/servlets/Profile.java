/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.datastax.driver.core.Cluster;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;



/**
 *
 * @author dlennart
 */
@WebServlet(urlPatterns = {
    "/Profile",
    "/Profile/*",
    })
@MultipartConfig
public class Profile extends HttpServlet {

    /**
     * Processes requests for HTTP <code>GET</code>
     * 
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private HashMap CommandsMap = new HashMap();
    Cluster cluster=null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        System.out.println("arg0 "+args[0]);
        System.out.println("arg1 "+args[1]);
        System.out.println("arg2 "+args[2]);
        DisplayProfile(args[2], request, response);
       
        
    }
    private void DisplayProfile(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       User usr = new User();
       usr.setCluster(cluster);
       String FirstName = usr.getFirstName(User);
       RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
       FirstName = usr.getFirstName(User);
       request.setAttribute("first_name", FirstName);
       request.setAttribute("reqargs", User);
       
       rd.forward(request, response);
       
    }
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    private void error(String mess, HttpServletResponse response,HttpServletRequest request ) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println ("request: " +request);
        out.println("<h2>" + mess + "</h2>");
        out.close();

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
