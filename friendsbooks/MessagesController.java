/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author soundaryalanka
 */
public class MessagesController {
     public static void sendMessage(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter id of the person you want to send message to:");
        int receiverId = scanner.nextInt();
        if (receiverId == user.getId()) {
            System.out.println("You cannot send request to your own self! Sorry!");
        } else {
            ArrayList allId = UserController.getAllID();
            if (!allId.contains(receiverId)) {
                System.out.println("No person found with this Id.");
            } else {
                getPreviousMessages(user.getId(),receiverId);
                System.out.println("Enter your message: (Only 20 characters)");
                String message = scanner.next();
                boolean flag = MessagesController.insert(user.getId(), receiverId,message);
                if (flag) {
                    System.out.println("Message sent.");
                } else {
                    System.out.println("Some error. Please try again after some time.");
                }
            }
        }
    }

    public static boolean insert(int senderId, int receiverId,String message) {
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into messages values "
                    + "('" + senderId + "', '" + receiverId + "', '" + new java.sql.Timestamp(new java.util.Date().getTime()) + "', " + 0 +", '" +message+"')";
            int r = statement.executeUpdate(query);
            if (r == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void getPreviousMessages(int id,int otherid){
        ResultSet resultSet = null;
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            Statement statement1= connect.createStatement();
            String query = "select SenderId, ReceiveId, Message from messages where (ReceiveId = '"+id+"' and SenderId = '"+otherid+"') or (SenderId = '"+id+"' and ReceiveId = '"+otherid+"') order by LastUpdated";
            String query1 = "select FirstName from user where Id = "+otherid;
            resultSet = statement.executeQuery(query);
            ResultSet rs1 = statement1.executeQuery(query1);
            String otherName="";
            while(rs1.next()){
                otherName=rs1.getString(1);
            }
            while (resultSet.next()) {
               if(resultSet.getInt(1) == id){
                   System.out.println("You : "+resultSet.getString(3));
               }else{
                   System.out.println(otherName+": "+resultSet.getString(3));
               }
                    
                    
               
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
     public static int getNotiCount(User user){
        int count =0;
        try {
            ResultSet resultSet = null;
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select count(*) as total from messages where ReceiveId = '"+user.getId()+"' and IsRead = 'false'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public static ResultSet getNotification(User user){
        ResultSet resultSet = null;
        try {
             Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select f.SenderId, u.FirstName, u.LastName, f.message from messages f, user u where f.ReceiveId = '"+user.getId()+"' and f.IsRead = 'false' and f.SenderId = u.Id";
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void updateIsRead(int senderId,  int receiveId){
             
            String query = "update messages set IsRead = "+1+" where SenderId="+senderId+" and ReceiveId="+receiveId;
            int r;
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            r = statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
