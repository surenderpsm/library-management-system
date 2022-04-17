/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import com.mycompany.library.application.PrimaryController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Surender
 */



public class Database {
    
    private static Connection connection;
    
    
    public static Connection getConnection() {
        return connection;
    }  
    
    /*
    Connects to database and loads driver when class is loaded in JVM
    */
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "surender", "sql");
            System.out.println("Database Connected");
        }
        catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex);
        }
    }
    
    
    
    /*
    
    Below countains implementations for calling each function defined in mysql-server
    
    */
        
//    static String testQuery(String registerNumber){
//        try {
//            CallableStatement callableStatement = connection.prepareCall("{? = call getName(?)}");
//            callableStatement.setString(2, registerNumber);
//            callableStatement.registerOutParameter(1, Types.VARCHAR);
//            callableStatement.execute();
//            return callableStatement.getString(1);
//            
//        }
//        catch (SQLException ex) {
//            System.out.println("hey "+ ex);
//        }    
//        return null;
//    }  
    
    
    
    
}