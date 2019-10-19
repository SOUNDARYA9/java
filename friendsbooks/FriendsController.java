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
public class FriendsController {
        public static void sendAFriendRequest(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Id of the person you want to send the Friends request to:");
        int receiverId = scanner.nextInt();
        if (receiverId == user.getId()) {
            System.out.println("You cannot send request to your own self! Sorry!");
        } else {
            ArrayList allId = UserController.getAllID();
            if (!allId.contains(receiverId)) {
                System.out.println("No person found with this Id.");
            } else {
                boolean flag = FriendsController.insert(user.getId(), receiverId);
                if (flag) {
                    System.out.println("Friend request sent.");
                } else {
                    System.out.println("Some error. Please try again after some time.");
                }
            }
        }
    }

    public static boolean update(int senderId, int receiverId, int choice) {
        try {
            if (choice == 1) {
               Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
                String query = "update friends set IsAccepted = " + choice + ", IsProcessed = " + choice + ", LastUpdated = '"
                        + new java.sql.Timestamp(new java.util.Date().getTime()) + "' where SenderId=" + senderId + " and ReceiveId=" + receiverId;
                int r = statement.executeUpdate(query);
                if (r == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
                String query = "delete friends where SenderId=" + senderId + " and ReceiveId=" + receiverId;
                int r = statement.executeUpdate(query);
                if (r == 1) {
                    return false;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insert(int senderId, int receiverId) {
        try {
           Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into friends values "
                    + "('" + senderId + "', '" + receiverId + "', "
                    + 0 + ", '" + new java.sql.Timestamp(new java.util.Date().getTime()) + "', " + 0 + ")";
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

    public static int getNotiCount(User user) {
        int count = 0;
        try {
            ResultSet resultSet = null;
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select count(*) as total from friends where ReceiveId = '" + user.getId() + "' and IsProcessed = 'false'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static ResultSet getNotification(User user) {
        ResultSet resultSet = null;
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select f.SenderId, u.FirstName, u.LastName as total from friends f, user u where f.ReceiveId = '" + user.getId() + "' and f.IsProcessed = 'false' and f.SenderId = u.Id";
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void viewFriends(User user) {
        ResultSet resultSet = null;
        try {
            int option = 1;
            while (option != 0) {
                Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
                String query = "select u.Id, u.FirstName, u.LastName from friends f, user u where (f.ReceiveId = '" + user.getId() + "' and f.SenderId=u.Id) or (f.ReceiveId = u.Id and f.SenderId= '" + user.getId() + "') and f.IsAccepted = 1";
                resultSet = statement.executeQuery(query);
                System.out.println("Select a friend to view their profile:");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
                }
                System.out.println("0: To go back.");
                option = new Scanner(System.in).nextInt();
                if (option == 0) {
                    break;
                } else {
                    User friend = UserController.getUserById(option);
                    if (friend != null) {
                        System.out.println("Name: " + friend.getFirstName() + " " + friend.getLastName());
                        System.out.println("DOB: " + friend.getDob());
                        System.out.println("Gender: " + friend.getGender());
                        System.out.println("School: " + friend.getSchool());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    public static ArrayList getFriendsId(User user){
        ArrayList array = new ArrayList();
        try {        
            ResultSet resultSet=null;
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select u.Id, u.FirstName, u.LastName from friends f, user u where (f.ReceiveId = '" + user.getId() + "' and f.SenderId=u.Id) or (f.ReceiveId = u.Id and f.SenderId= '" + user.getId() + "') and f.IsAccepted = 1";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
            array.add(resultSet.getInt(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
    
}
