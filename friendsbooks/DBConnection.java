package friendsbooks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author soundaryalanka
 */
public class DBConnection {
    
    public static Connection DBConnect() 
    {
        Connection conn=null;
        
        try
        {
            final String db_url="jdbc:mysql://mis-sql.uhcl.edu/lankal3413";
            conn=(Connection) DriverManager.getConnection(db_url,"lankal3413","1681176");
            System.out.println("Success");
        }
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        
        return conn;
    }
}
