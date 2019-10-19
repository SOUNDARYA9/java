/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;

import java.sql.Timestamp;

/**
 *
 * @author soundaryalanka
 */
public class Post {
     private int id;
    private String post;
    private int userId;
    private java.sql.Timestamp postedTime;

    public Post() {
    }

    public Post(String post, int userId) {
        this.post = post;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Timestamp postedTime) {
        this.postedTime = postedTime;
    }


    
}
