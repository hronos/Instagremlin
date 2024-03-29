/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;


/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password, String first_name, String last_name){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name) Values(?,?,?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword,first_name, last_name));
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
    return false;  
    }
    
    public ResultSet getUserData(String username){
            Session session = cluster.connect("instagrim");
            ResultSet rs = null;
            PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
            BoundStatement boundStatement = new BoundStatement(ps);
            
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind(username));
            session.close();
            if (rs.isExhausted()) {
                System.out.println("No Data returned");
                
            } else {
                return rs;
            }
            return rs;
        }
    public void updateUserData(String username, String last_name, String first_name){
        
        Session session = cluster.connect("instagrim");
        Statement update = QueryBuilder.update("instagrim", "userprofiles")
                .with(QueryBuilder.set("last_name", last_name))
                .and(QueryBuilder.set("first_name", first_name))
                .where((QueryBuilder.eq("login", username)));
        System.out.println("Statement: " + update);
        session.execute(update);
        session.close();
    }
    
    public void deleteUser(String username){
        try{
            Session session = cluster.connect("instagrim");
            Statement delete = QueryBuilder.delete()
                    .from("instagrim", "userprofiles")
                    .where((QueryBuilder.eq("login", username)));
            System.out.println("Statement" + delete);
            session.execute(delete);
            session.close();
        } catch (Exception e){}
    }
    
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    
       

    
}
