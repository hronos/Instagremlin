<%-- 
    Document   : comment
    Created on : Oct 26, 2014, 9:41:26 AM
    Author     : dlennart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comments</title>
    </head>
    <body>
        <%
            String picid = (String) request.getAttribute("picture");
            ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
        %>
        <p>
            <img style="-webkit-user-select: none; cursor: zoom-in;" src="/Instagrim/Image/<%=picid%>">
        </p>
        <%
            if (comments == null)
            {
        %>
        <h3>No comments</h3>
        <%
            }
            else
            {
                for (Comment comment : comments)
                {
        %>
        <h3><%=comment.getUser()%> wrote: </h3>
        <p>
            <%=comment.getComment()%>
        </p>
        <h3>On: <%=comment.getDate().toString()%></h3>
        <%
                }
            }
        %>
        
        <form method="POST"  action="/Instagrim/Image/Comment">
            <textarea name="comment" cols="50" rows="5">
            Enter your comment here
            </textarea>
            <br/>
            <input type="submit" value="Add"> 
            <input type="hidden" name="picid" value="<%=picid%>">
        </form>
    </body>
</html>
