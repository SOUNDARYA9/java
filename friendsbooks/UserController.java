/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;


import static friendsbooks.Login.scanner;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.chart.PieChart;
/**
 *
 * @author soundaryalanka
 */
public class UserController {
    
    public static User insert(User user) {
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into user values "
                    + " ( " + 0 + " ', '" + user.getUserAccountId() + "', '" + user.getPassword() + "', '"
                    + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getDob() + "', '"
                    + user.getGender() + "', '" + user.getSchool() + "', '"+new java.sql.Timestamp(new java.util.Date().getTime())+"', '"+new java.sql.Timestamp(new java.util.Date().getTime())+")";
            int r = statement.executeUpdate(query);
            if (r == 1) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User SelectUserAccountIdAndPassword(String useraccountId, String password) {
        User user = new User();
        try {
            ResultSet resultSet = null;
             Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select * from user where UserAccountID = '" + useraccountId + "' and password = '" + password + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                while (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                    user.setUserAccountId(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstName(resultSet.getString(4));
                    user.setLastName(resultSet.getString(5));
                    user.setDob(resultSet.getDate(6));
                    user.setGender(resultSet.getString(7).charAt(0));
                    user.setSchool(resultSet.getString(8));
                    user.setLastUpdated(resultSet.getTimestamp(9));

                }
            }

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList getAllUserAccountID() {
        ArrayList<String> allUserAccountId = new ArrayList<String>();

        try {
            ResultSet resultSet = null;
            DBConnection dbc=new DBConnection();
            
          Connection connect = dbc.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select UserAccountID from user";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                
                allUserAccountId.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUserAccountId;
    }
    
     public static ArrayList getAllID() {
        ArrayList<Integer> allId = new ArrayList<Integer>();

        try {
            ResultSet resultSet = null;
             Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select Id from user";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                allId.add(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allId;
    }

    public static User updateProfile(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select an option:");
        System.out.println("1. Update First Name.");
        System.out.println("2. Update Last Name.");
        System.out.println("3. Update School.");
        System.out.println("4. Update Gender.");
        System.out.println("0. Go to previous menu.");
        int choice = scanner.nextInt();
        User tmpUser=user;
        switch (choice) {
            case 1: {
                System.out.println("Please enter new First Name:");
                String fname = scanner.next();
                if (!fname.equals(user.getFirstName())) {
                    user.setFirstName(fname);
                    tmpUser = UserController.update(user);
                }
                break;
            }
            case 2: {
                System.out.println("Please enter new Last Name:");
                String lname = scanner.next();
                if (!lname.equals(user.getLastName())) {
                    user.setLastName(lname);
                    tmpUser = UserController.update(user);
                }
                break;
            }
            case 3: {
                System.out.println("Please enter new school Name:");
                String sname = scanner.next();
                if (!sname.equals(user.getSchool())) {
                    user.setSchool(sname);
                    tmpUser = UserController.update(user);
                }
                break;
            }
            case 4: {
                System.out.println("Please enter updated gender:");
                String gender = scanner.next();
                if (!gender.equals(user.getGender())) {
                    user.setGender(gender.charAt(0));
                    tmpUser = UserController.update(user);
                }
                break;
            }
            case 0:{
                break;
            }
            default: {
                System.out.println("Incorrect choice. Please try again");
                break;
            }
        }
        if(tmpUser!=null){
            System.out.println("Data successfully updated.");
            return tmpUser;
        }else{
            System.out.println("Data not updated. Please try again.");
            return user;
        }
    }
    
    public static User update(User user){
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "update user set FirstName = '"+user.getFirstName()+"', LastName = '"+user.getLastName()+"', Gender = '"+
                    user.getGender()+"', School = '"+user.getSchool()+"', LastUpdated = '"+new java.sql.Timestamp(new java.util.Date().getTime())+"' where Id="+user.getId();
            int r = statement.executeUpdate(query);
            if (r == 1) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean updateLastLastNotiChecked(User user){
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "update user set LastNotiChecked = '"+new java.sql.Timestamp(new java.util.Date().getTime())+"' where Id="+user.getId();
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
    
    public static User getUserById(int id){
        User user =new User();
         try {
            ResultSet resultSet = null;
             Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select * from user where Id="+id;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
               user.setId(resultSet.getInt(1));
                    user.setFirstName(resultSet.getString(4));
                    user.setLastName(resultSet.getString(5));
                    user.setDob(resultSet.getDate(6));
                    user.setGender(resultSet.getString(7).charAt(0));
                    user.setSchool(resultSet.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
}
