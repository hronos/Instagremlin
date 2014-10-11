

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <h1>Profile</h1>
        <%
            String FirstName = (String) request.getAttribute("first_name");
            String Args = (String) request.getAttribute("last_name");
        %>
        <p><%=FirstName %></p>
        <p><%=Args %></p>

        
    </body>
</html>
