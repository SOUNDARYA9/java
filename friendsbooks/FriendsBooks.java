/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;
import static friendsbooks.Login.scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soundaryalanka
 */
public class FriendsBooks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
         int option = 1;
        System.out.println("*-----* Welcome to FriendsBook *------*.");
        System.out.println("Please select an option ");
        while (option != 0) {
            System.out.println("Already a member,Please Enter 1 to login.");
            System.out.println("Not a member yet! Please Enter 2 to Register.");
            System.out.println("Enter 0 to exit.");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    Login.loginAccount();
                    break;
                case 2:
                    Login.registerAccount();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Incorrect input. Please try again.");
                    break;
            }

        }

    }

    public static void afterLoggedIn(User user) {
        int option = 1;
        while (option != 9) {
            int notiCountFriends = FriendsController.getNotiCount(user);
            int notiCountMessage = MessagesController.getNotiCount(user);
            int notiCount = notiCountFriends+notiCountMessage;
            System.out.println("Welcome " + user.getFirstName() + "!");
            System.out.println("Select an option.");
            System.out.println("1. Select an update and post.");
            if (notiCount != 0) {
                System.out.println("2. Check notifications (" + notiCount + " new)");
            } else {
                System.out.println("2. Check notifications");
            }
            System.out.println("3. Create a new post.");
            System.out.println("4. Friends.");
            System.out.println("5. Update profile.");
            System.out.println("6. Send a message.");
            System.out.println("7. Send a friend request.");
            System.out.println("8. See Hashtag in trends.");
            System.out.println("9. Logout.");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    PostController.viewFriendsPost(user);
                    break;
                case 2:
                    ShowNotifications(user);
                    break;
                case 3:
                    PostController.createPost(user);
                    break;
                case 4:
                    FriendsController.viewFriends(user);
                    break;
                case 5:
                    user = UserController.updateProfile(user);
                    break;
                case 6:
                    MessagesController.sendMessage(user);
                    break;
                case 7:
                    FriendsController.sendAFriendRequest(user);
                    break;
                case 8:
                    HashtagController.getHashtagInTrend();
                    break;
                case 9:
                    System.out.println("You have successfully logged out.");
                    user = null;
                    break;
                default:
                    System.out.println("Incorrect input. Please try again.");
                    break;
            }
        }

    }

    public static void ShowNotifications(User user) {
        int option = 1;
        while (option != 0) {
            ResultSet rsf = FriendsController.getNotification(user);
            ArrayList<Integer> senderIdListF = new ArrayList<Integer>();
            ArrayList<String> senderfNameListF = new ArrayList<String>();
            ArrayList<String> senderlNameListF = new ArrayList<String>();
            int cnt = 1;
            ResultSet rsw = MessagesController.getNotification(user);
            ArrayList<Integer> senderIdListM = new ArrayList<Integer>();
            ArrayList<String> senderfNameListM = new ArrayList<String>();
            ArrayList<String> senderlNameListM = new ArrayList<String>();
            ArrayList<String> senderMesage = new ArrayList<String>();
            try {
                if(!rsf.wasNull()){
                while (rsf.next()) {
                    senderIdListF.add(rsf.getInt(1));
                    senderfNameListF.add(rsf.getString(2));
                    senderlNameListF.add(rsf.getString(3));
                }}
                if(!rsw.wasNull()){
                while (rsw.next()) {
                    senderIdListM.add(rsw.getInt(1));
                    senderfNameListM.add(rsw.getString(2));
                    senderlNameListM.add(rsw.getString(3));
                    senderMesage.add(rsw.getString(4));
                }}
                System.out.println("Select notification to view:");
                for (int i = 0; i < senderIdListF.size(); i++) {
                    System.out.println(cnt + ". You have a friend request from " + senderfNameListF.get(i) + " " + senderlNameListF.get(i) + " with id " + senderIdListF.get(i));
                    cnt++;
                }
                int cntAfterFrns=cnt;
                for (int i = 0; i < senderIdListM.size(); i++) {
                    System.out.println(cnt + ". You have a new message from " + senderfNameListM.get(i) + " " + senderlNameListM.get(i) + " with id " + senderIdListM.get(i));
                    cnt++;
                }
                int cntAfterMsg = cnt;
                if(senderIdListF.isEmpty() && senderIdListM.isEmpty()){
                    System.out.println("No new notifications.");
                }
                System.out.println("0. To go back.");
                option = scanner.nextInt();
                if (option == 0) {
                    break;
                } else {
                    if (!senderIdListF.isEmpty()) {
                        if (option >= 1 && option < cntAfterFrns) {
                            System.out.println("1. To accept");
                            System.out.println("0. To deny");
                            int choice = scanner.nextInt();
                            boolean flag = FriendsController.update(senderIdListF.get(option - 1), user.getId(), choice);
                            if (flag) {
                                System.out.println("Friend request accepted.");
                            } else {
                                System.out.println("Friend request denied.");
                            }

                        }
                    }
                    if(!senderIdListM.isEmpty()){
                        if(option >= cntAfterFrns && option < cntAfterMsg){
                            System.out.println(senderfNameListM.get(option-1)+": "+senderMesage.get(option-1));
                            MessagesController.updateIsRead(senderIdListM.get(option-1),user.getId() );
                            System.out.println("1. To reply");
                            System.out.println("0. To go back");
                            int choice = scanner.nextInt();
                            if(choice==1){
                                System.out.println("Enter your message:");
                                String message = scanner.next();
                                MessagesController.insert(user.getId(),senderIdListM.get(option-1) , message);
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FriendsBooks.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        Iterator<Integer> iterator = senderIdList.iterator();
//		while (iterator.hasNext()) {
//                    
//			System.out.println(iterator.next());
//		}

    }
}
