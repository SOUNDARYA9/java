/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;

import java.sql.Date;
import java.util.Scanner;

/**
 *
 * @author soundaryalanka
 */
public class Login {
    static Scanner scanner = new Scanner(System.in);
    public static void loginAccount(){
        
        System.out.println("Enter the User Account ID: ");
        String useraccountID = scanner.next();
        System.out.println("Enter password for the Account: ");
        String password = scanner.next();
        User user = UserController.SelectUserAccountIdAndPassword(useraccountID, password);
        if(user!=null){
            FriendsBooks.afterLoggedIn(user);
        }else{
            System.out.println("Login unsuccessful. Please try again.");
        }
        }
        
    public static void registerAccount(){
       
        System.out.println("Please enter your First Name:");
        String firstName = scanner.next();
        
        System.out.println("Please enter your Last Name:");
        String lastName = scanner.next();
        
        System.out.println("Please enter your Date of Birth (YYYY-MM-DD):");
        Date dob=Date.valueOf(scanner.next());
        
        System.out.println("Please enter your Gender as: M for Male and F for Female");
        char gender = scanner.next().toUpperCase().charAt(0);
        
        System.out.println("Please enter your School Name:");
        String school = scanner.next();
        String useraccountID="";
         while(true){
        System.out.println("Please enter an Account ID: (ID must be between 3 and 10 characters and must contain at least one letter, one digit and one special\n" +
"character from {#, ?, !, *}");
        useraccountID = scanner.next();       
        AccountIDValidator validator = new AccountIDValidator();
        
            if(!validator.validateString(useraccountID)){
               
                System.out.println("Please enter Account ID fullfilling all the conditions.");
            }else{
               
                if(!validator.validateUnique(useraccountID)){
                    System.out.println("Hello");
                    System.out.println("Account ID is already in use. Please select another Account ID.");
                }else
                    break;
            }
        }
        String password="";
        while(true){
            System.out.println("Please enter password: ");
            password = scanner.next();
        
            if(password.equalsIgnoreCase(useraccountID)){
                System.out.println("Password should not be same as Account ID.");
            }else{
                break;
            }
        }
        User user = new User(firstName, lastName, dob, gender, school, useraccountID, password);
        user = UserController.insert(user);
        if(user!=null){
            System.out.println("You have registered successfully. Your Account ID is: "+user.getUserAccountId());
        }else{
            System.out.println("You are not registered. Please try again.");
        }        
    }   
  
}