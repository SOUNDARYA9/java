/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author soundaryalanka
 */
public class HashtagController {
     
    public static Hashtag getHashtag(String hashtag){
        Hashtag h=null;
        try {
            ResultSet resultSet = null;
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select Id, Count, PostId from hashtag where Hashtag like '"+hashtag+"'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                h=new Hashtag(resultSet.getInt(1),hashtag,resultSet.getInt(2),resultSet.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return h;
    }
    
    public static void updateCountandPostId(Hashtag h){
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "update hashtag set Count = '"+h.getCount()+"', PostId = '"+h.getPostId()+"'  where Id="+h.getId();
            int r = statement.executeUpdate(query);
            if (r == 1) {
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void insert(Hashtag h){
                try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into Hashtag values "
                    + "(" + 0 + ", '" + h.getHashtag() + "', '" + h.getCount() + "', '"
                    + h.getPostId() + "')";
            int r = statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void getHashtagInTrend(){
        try {
            int option=1;
            while(option!=0){
            ResultSet resultSet = null;
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select * from hashtag order by Count desc limit 3";
            resultSet = statement.executeQuery(query);
            int i=1;
            System.out.println("Select hashtag to view related posts:");
            while (resultSet.next()) {
                System.out.println(i+". "+resultSet.getString(2));
                i++;
            }
                System.out.println("0. To go back to previous menu.");
                option = new Scanner(System.in).nextInt();
                resultSet.beforeFirst();
                i=1;
                if(option ==0){
                    break;
                }else{
                    while(resultSet.next()){
                        if(option == i){
                            PostController.viewPost(resultSet.getString(2));
                            break;
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
