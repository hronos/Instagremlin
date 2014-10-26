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
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import uk.ac.dundee.computing.aec.instagrim.stores.Comment;


/**
 *
 * @author dlennart
 */
public class SocialModel {
    Cluster cluster;
    
    public SocialModel() {
    }
    
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    public ArrayList<Comment> fetchComments(java.util.UUID picid)
    {
        Session session = cluster.connect("instagrim");
        
        Statement select = QueryBuilder.select()
                .all()
                .from("comment")
                .where(QueryBuilder.eq("picid", picid));
        
        ResultSet rs = null;
        rs = session.execute(select);
        System.out.println(rs);
        ArrayList<Comment> comments = new ArrayList<Comment>();
        if (rs.isExhausted()) 
        {
            System.out.println("No Images returned");
            return null;
        }
        else
        {
            for (Row row : rs) {
                
                String user = row.getString("user");
                System.out.println("user " +user);
                String text = row.getString("comment");
                Date date = row.getDate("date");
                Comment comment = new Comment();
                comment.setUser(user);
                comment.setComment(text);
                comment.setDate(date);
                comments.add(comment);
            }
        }
        return comments;
    }
    
    public void addComment(String user, String id, String comment)
    {
        Session session = cluster.connect("instagrim");
        Date date = new Date();
        java.util.UUID picid = UUID.fromString(id);
        Statement insert = QueryBuilder.insertInto("instagrim", "comment")
                .value("picid", picid)
                .value("user", user)
                .value("comment", comment)
                .value("date", date);
        session.execute(insert);
        session.close();
    }
    
    public void deleteComment(String id, Date date){
        Session session = cluster.connect("instagrim");
        java.util.UUID picid = UUID.fromString(id);
        Statement delete = QueryBuilder.delete()
                .from("instagrim", "comment")
                .where(QueryBuilder.eq("picid", picid))
                .and(QueryBuilder.eq("date", date));
        session.execute(delete);
        session.close();
    }
    
    public void addLike(String id, String user){
        
        Session session = cluster.connect("instagrim");
        java.util.UUID picid = UUID.fromString(id);
        Statement insert = QueryBuilder.insertInto("instagrim", "like")
                .value("picid", picid)
                .value("user", user);
        session.execute(insert);
        session.close();
    }
    
    public void removeLike(String id, String user){
        java.util.UUID picid = UUID.fromString(id);
        Session session = cluster.connect("instagrim");
        Statement delete = QueryBuilder.delete()
                .from("instagrim", "like")
                .where(QueryBuilder.eq("picid", picid))
                .and(QueryBuilder.eq("user", user));
        session.execute(delete);
        session.close();
    }
    
    public String countLikes(String id){
        java.util.UUID picid = UUID.fromString(id);
        Session session = cluster.connect("instagrim");
        Statement count = QueryBuilder.select()
                .countAll()
                .from ("like")
                .where(QueryBuilder.eq("picid", picid));
        ResultSet rs = null;
        rs = session.execute(count);
        String counter = rs.toString();
        return counter;
    }
    
    public boolean checkLiked(String id, String user){
        java.util.UUID picid = UUID.fromString(id);
        Session session = cluster.connect("instagrim");
        Statement select = QueryBuilder.select()
                .all()
                .from("like")
                .where(QueryBuilder.eq("user", user))
                .and(QueryBuilder.eq("picid", picid));
        ResultSet rs = null;
        rs = session.execute(select);
        
        if (rs.isExhausted()){
            return false;
        }else{
            return true;
        }
    }
}
