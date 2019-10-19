/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author soundaryalanka
 */
public class CommentController {
     public static void insert(Comment c){
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into comment values "
                    + "(" + 0 + ", '" + c.getComment() + "', '" + c.getPostId() + "', '"
                    + c.getPostId() +"', '"+new java.sql.Timestamp(new java.util.Date().getTime()) +"')";
            int r = statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ResultSet getComments(int postId){
        ResultSet resultSet = null;
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select u.FirstName, c.Comment from comment c, user u where PostId = '"+postId+"' and u.Id=c.PostedBy order by DatePosted";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
