/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;

/**
 *
 * @author soundaryalanka
 */
public class Hashtag {
     private int id;
    private String hashtag;
    private int count;
    private String postId;

    public Hashtag() {
    }

    public Hashtag(String hashtag, int count, String postId) {
        this.hashtag = hashtag;
        this.count = count;
        this.postId = postId;
    }

    public Hashtag(int id, String hashtag, int count, String postId) {
        this.id = id;
        this.hashtag = hashtag;
        this.count = count;
        this.postId = postId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    
    
}
