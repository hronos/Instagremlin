<%-- 
    Document   : admin_users
    Created on : Oct 21, 2014, 7:57:15 PM
    Author     : dlennart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Users</title>
    </head>
    <body>
        <h1>Existing users</h1>
        <% java.util.LinkedList<String> results = (java.util.LinkedList<String>) 
            request.getAttribute("users");
            
        if (results == null){
        %>
        <p>No users</p>
        <% } 
        else {
            Iterator<String> iterator;
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                String last_name = iterator.next().toString();
                String first_name = iterator.next().toString();
                String username = iterator.next().toString();
                %>
                <p>Username: <%=username%> 
                    &nbsp; Last name: <%=last_name%> 
                    &nbsp; First name: <%=first_name%>
                </p>
           <% }
        }
        %>
        
        
        
            
    </body>
</html>
