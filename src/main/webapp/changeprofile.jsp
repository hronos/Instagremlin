<%-- 
    Document   : changeprofile
    Created on : Oct 19, 2014, 3:35:14 PM
    Author     : dlennart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update profile</title>
    </head>
    <body>
        <h1>Update Profile</h1>
        <%@ include file="menu.jsp" %>
        <%
            String FirstName = (String) request.getAttribute("first_name");
            String LastName = (String) request.getAttribute("last_name");
            String username = (String) request.getAttribute("username");
        %>
        <form method="POST"  action="/Instagrim/Profile">
                <ul>
                    <li>User Name <input type="text" name="username" value="<%=username%>" disabled="true"></li>
                    <li>Password <input type="password" name="password"></li>
                    <li>First Name <input type="text" name="first_name" value="<%=FirstName%>"></li>
                    <li>Last Name <input type="text" name="last_name" value="<%=LastName%>"></li>
                </ul>
                <br/>
                <input type="submit" value="Update"> 
            </form>
    </body>
</html>
