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
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comments</title>
        
    </head>
    <body>
        <%
            String picid = (String) request.getAttribute("picture");
            String likes = (String) request.getAttribute("likes");
            boolean liked = (boolean) request.getAttribute("liked");
            String user = (String) request.getAttribute("username");
            ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
            
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            String username = "majed";
            if (lg.getlogedin()){
                username=lg.getUsername();
            }
        %>
        <p>
            <img style="-webkit-user-select: none; cursor: zoom-in;" src="/Instagrim/Image/<%=picid%>">
        </p>
        <% if (liked == false) { %>
        <button class="like" id="like" name="<%=picid%>"value="<%=username%>">Like</button>
       <% } else {%>
       <button class="like" id="unlike" name="<%=picid%>" value="<%=username%>">Unlike</button>
       <% } %>
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
        <script type="text/javascript">
        $(document).ready(function(){
            $(document).on("click", ".like", function(){

                console.log("this is the click " + this.name +" id: " +this.value);
                
                var picid = this.name;
                var user = this.value;
                var liked = this.id;
                if (liked === "like"){
                    $.ajax({
                        type: "POST",
                        url: "/Instagrim/Image/Like/"+picid+"/"+user,
                        success: function(msg){
                            alert("Like added");
                            location.reload(true);
                        },
                    });
                } else {
                    $.ajax({
                        type: "DELETE",
                        url: "/Instagrim/Image/Like/"+picid+"/"+user,
                        success: function(msg){
                            alert("Like removed");
                            location.reload(true);
                        },
                    });
                }

            });
        });
        </script>
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
