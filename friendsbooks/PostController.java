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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;


/**
 *
 * @author soundaryalanka
 */
public class PostController {
     public static void createPost(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your post:");
        String post = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            post += line + " ";
            if (line.length() <= 0) {
                break;
            }

        }
        Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
        Matcher mat = MY_PATTERN.matcher(post);
        ArrayList<String> strs = new ArrayList<String>();
        while (mat.find()) {
            strs.add(mat.group(1));
        }
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "insert into post values "
                    + "(" + 0 + ", '" + post + "', '" + user.getId() + "', '" + new java.sql.Timestamp(new java.util.Date().getTime()) + "')";
            int r = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (r == 1) {
                System.out.println("Post successfully posted.");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    if (!strs.isEmpty()) {
                        for (String s : strs) {
                            Hashtag h = HashtagController.getHashtag("#" + s);
                            if (h != null) {
                                //update
                                h.setPostId(h.getPostId() + generatedKeys.getInt(1) + ",");
                                h.setCount(h.getCount() + 1);
                                HashtagController.updateCountandPostId(h);
                            } else {
                                //insert
                                h = new Hashtag("#" + s, 1, generatedKeys.getInt(1) + ",");
                                HashtagController.insert(h);
                            }
                        }
                    }
                }
            } else {
                System.out.println("Some error. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewPost(String hashtag) {
        ResultSet resultSet = null;
        try { 
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select p.Post, u.FirstName, u.LastName from post p, user u where p.post like '%" + hashtag + "%' and p.UserId = u.Id";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Post by " + resultSet.getString(2) + ": " + resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewFriendsPost(User user) {
        ArrayList friendsId = FriendsController.getFriendsId(user);
        String friends = "";
        Object[] f = friendsId.toArray();
        for (int i = 0; i < f.length; i++) {
            if (i == f.length - 1) {
                friends += f[i] + "";
            } else {
                friends += f[i] + ", ";
            }
        }
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connect = DBConnection.DBConnect();
            Statement statement= connect.createStatement();
            String query = "select p.Id, p.Post, u.FirstName, u.LastName from post p, user u where p.UserId = u.Id and p.UserId in (" + friends + ") order by p.PostedTime desc limit 5";
            resultSet = statement.executeQuery(query);
            int option = 1;
            while (option != 0) {
                System.out.println("Select a post to view:");
                int i = 1;
                while (resultSet.next()) {
                    System.out.println(i + ": Post by " + resultSet.getString(3) + " " + resultSet.getString(4));
                    i++;
                }
                resultSet.beforeFirst();
                System.out.println("0. To go back.");
                option = scanner.nextInt();
                i = 1;
                if (option == 0) {
                    break;
                } else {
                    while (resultSet.next()) {
                        if (option == i) {
                            int choice = 1;
                            while (choice != 0) {
                                System.out.println(resultSet.getString(3) + " " + resultSet.getString(4) + ": " + resultSet.getString(2));
                                ResultSet rsComment = CommentController.getComments(resultSet.getInt(1));
                                if(rsComment !=null){
                                    while(rsComment.next()){
                                        System.out.println("Comment by "+rsComment.getString(1)+": "+rsComment.getString(2));
                                    }
                                }
                                System.out.println("1. To enter comment.");
                                System.out.println("0. To go back.");
                                choice = scanner.nextInt();
                                if (choice == 0) {
                                    break;
                                } else {
                                    if (choice == 1) {
                                        System.out.println("Enter your comment : ");
                                        String cmnt = scanner.next();
                                        Comment c = new Comment(cmnt,user.getId(),resultSet.getInt(1));
                                        CommentController.insert(c);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        i++;
                    }
                    resultSet.beforeFirst();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
