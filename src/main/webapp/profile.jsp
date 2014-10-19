

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
            String username = (String) request.getAttribute("username");
        %>
        <p><%=FirstName %></p>
        <p><%=Args %></p>
        <p><%=username %></p>
        <p><image src="Avatar/dlennart"</p>
        <h3>Profile picture Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Avatar/<%=username %>">
                File to upload: <input type="file" name="upfile"><br/>

                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form><h3>File Upload</h3>
            

        
    </body>
</html>
