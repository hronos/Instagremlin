/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.Date;
/**
 *
 * @author dlennart
 */
public class Comment {
    private String user;
    private String comment;
    private Date date;

    public Comment() {
    }

    public String getUser() {
        System.out.println("Store user " +user);
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void commentAttr(String author, String comment, Date date)
    {
        this.user = user;
        this.comment = comment;
        this.date = date;
    }
}
