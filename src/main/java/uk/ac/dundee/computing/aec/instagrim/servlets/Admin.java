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
import com.datastax.driver.core.ResultSet;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 *
 * @author dlennart
 */
@WebServlet(urlPatterns = {
    "/Admin",
    "/Admin/*",
    })
@MultipartConfig
public class Admin extends HttpServlet {
    
    private HashMap CommandsMap = new HashMap();
    Cluster cluster=null;
    
    public Admin()
    {
        super();
        CommandsMap.put("Users", 1);
        CommandsMap.put("Images", 2);
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String args[] = Convertors.SplitRequestPath(request);
        int command = 0;
        System.out.println("arg0 "+args[0]);
        System.out.println("arg1 "+args[1]);
        System.out.println("arg2 "+args[2]);
        
        try {
            command = (Integer) CommandsMap.get(args[2]);     
        } catch (Exception e) {   
            error("Bad Operator ", response);
            return;
        }
        System.out.println("arg0 "+args[0]);
        System.out.println("arg1 "+args[1]);
        System.out.println("arg2 "+args[2]);
       
        
        try
        {
            switch (command) 
            {
            case 1:
                showUsers(request, response);
                break;
            case 2:
                //showPictures(request, response);
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String args[] = Convertors.SplitRequestPath(request);
        int command = 0;
        System.out.println("arg0 "+args[0]);
        System.out.println("arg1 "+args[1]);
        System.out.println("arg2 "+args[2]);
        System.out.println("arg3 "+args[3]);
        try {
            command = (Integer) CommandsMap.get(args[2]);     
        } catch (Exception e) {   
            error("Bad Operator ", response);
            return;
        }
        System.out.println("arg0 "+args[0]);
        System.out.println("arg1 "+args[1]);
        System.out.println("arg2 "+args[2]);
        System.out.println("arg3 "+args[3]);
       
        
        try
        {
            switch (command) 
            {
            case 1:
                deleteUser(request, response, args[3]);
                break;
            case 2:
                
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
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response, String user) 
            throws ServletException, IOException{
        System.out.println("Delete method called for " +user);
    }
    private void showUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        Session session = cluster.connect("instagrim");
        LinkedList results = new LinkedList();
        ResultSet rs = null;
        
        Statement select = QueryBuilder.select("login","last_name","first_name")
                .from("instagrim", "userprofiles");
        System.out.println("Statement: " + select);
        rs = session.execute(select);
        session.close();
        
        for ( Row row : rs ) {
            results.push(row.getString("login"));
            results.push(row.getString("first_name"));
            results.push(row.getString("last_name"));
            System.out.println("Row " +row);
            System.out.println("Username " +row.getString("login"));
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/admin_users.jsp");
        request.setAttribute("users", results);
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
