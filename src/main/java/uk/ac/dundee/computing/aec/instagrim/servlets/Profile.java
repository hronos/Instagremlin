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
import javax.servlet.http.HttpSession;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import com.datastax.driver.core.Row;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;



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
    
    public Profile()
    {
        super();
        CommandsMap.put("Display", 1);
        CommandsMap.put("Update", 2);
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        String username = "majed";
        if (lg.getlogedin()){
            username=lg.getUsername();
        }
        String args[] = Convertors.SplitRequestPath(request);
        int command = 0;
        try {
            command = (Integer) CommandsMap.get(args[3]);
             
        } catch (Exception et) {
           
            error("Bad Operator ", response);
            return;
        }
        
        try
        {
            switch (command) 
            {
            case 1:
                DisplayProfile(username, request, response);
                break;
            case 2:
                updateProfile(username, request, response);
                break;
            default:
                error("Bad Operator", response);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            error("OutOfBounds", response);
        }
    }
    
    private void updateProfile(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        String username = "majed";
        if (lg.getlogedin()){
            username=lg.getUsername();
        }
        if (username == User){
            User usr = new User();
            usr.setCluster(cluster);
            ResultSet rs = usr.getUserData(User);
            RequestDispatcher rd = request.getRequestDispatcher("/changeprofile.jsp");
            for (Row row : rs){
                request.setAttribute("first_name", row.getString("first_name"));
                request.setAttribute("last_name", row.getString("last_name"));
                request.setAttribute("username", row.getString("login"));
            }
            rd.forward(request, response);
        }else {
            authError(request, response);
        }
    }
    
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        String username = "majed";
        if (lg.getlogedin()){
            username=lg.getUsername();
        }
        String last_name = request.getParameter("last_name");
        String first_name = request.getParameter("first_name");
        User usr = new User();
        usr.setCluster(cluster);
        usr.updateUserData(username, last_name, first_name);
        
    }
    private void DisplayProfile(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       User usr = new User();
       usr.setCluster(cluster);
       ResultSet rs = usr.getUserData(User);
       String FirstName = null; 
       String LastName = null;
       
       
       for (Row row : rs){
        FirstName = row.getString("first_name");
        LastName = row.getString("last_name");
        
       }
       
       RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
       request.setAttribute("first_name", FirstName);
       request.setAttribute("last_name", LastName);
       request.setAttribute("username", User);
       
       rd.forward(request, response);
       
    }
    
    private void authError(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.setAttribute("msg", "You have no rights to edit this profile");
        RequestDispatcher rd = request.getRequestDispatcher("/msg.jsp");
        rd.forward(request, response);
    }
    
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
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
