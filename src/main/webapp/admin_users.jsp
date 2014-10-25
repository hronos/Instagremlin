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
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin - Users</title>
    <script type="text/javascript">
    $(document).ready(function(){
        $(document).on("click", ".remove", function(){
            
            console.log("this is the click" + this.name);
            var user = this.name;
            if (confirm('Are you sure you want to Delete User '+user+'?')) {
                $.ajax({
                    type: "DELETE",
                    url: "/Instagrim/Admin/Users/"+user,
                    success: function(msg){
                        alert("User has been deleted");
                        location.reload(true);
                    },
                });
            } else {}
        });
    });
        </script>
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
                    <button class="remove" id="15" name="<%=username%>">x</button>
                </p>
                
           <% }
        }
        %>
        
        
      <button class="remove" id="15">x</button>

 
            
    </body>
</html>
