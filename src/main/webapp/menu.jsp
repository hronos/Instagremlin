<%-- 
    Document   : menu
    Created on : Oct 26, 2014, 7:18:25 PM
    Author     : dlennart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<nav>
                <ul id="menu" class="menu">
                        <%

                    LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                    if (lg != null) {
                        String user = lg.getUsername();
                        if (lg.getlogedin()) {
                            %>
                    <li><a href="/Instagrim/upload.jsp">Upload</a></li>
                    <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                    <li><a href="/Instagrim/Profile/<%=lg.getUsername()%>/Display">View Profile</a></li>
                    <li><a href="/Instagrim/Profile/<%=lg.getUsername()%>/Update">Edit Profile</a></li>
                    <li><a href="/Instagrim/Logout">Logout</a></li>
                    <%  }
                    }else{
                    %>
                    <li><a href="/Instagrim/Images/">Latest Images</a></li>
                    <li><a href="/Instagrim/register.jsp">Register</a></li>
                    <li><a href="/Instagrim/login.jsp">Login</a></li>
                    <%
                    }%>
                </ul>
            </nav>
