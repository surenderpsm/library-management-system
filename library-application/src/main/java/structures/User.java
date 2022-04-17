/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Surender
 */
public class User {
    private static String firstName;
    private static String registerNumber;
    private static String lastName;
    
    
    static{
        // set sample user
        registerNumber = "19BCI0128";
        retrieveName();
    }


    
    public static boolean retrieveName(){
        // assumes registernumber is already set
        try{
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select firstname, lastname from students where registernumber='"+registerNumber+"'");
            if(rs.next()){
                setFirstName(rs.getString(1));
                setLastName(rs.getString(2));
                return true;
            }
            // does not exist, so reset register number and return false
            registerNumber = "";
            return false;
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return false;
    }
    


    public static String getRegisterNumber() {
        return registerNumber;
    }
    public static void setRegisterNumber(String registerNumber) {
        User.registerNumber = registerNumber;
    }
    
    
    public static String getFirstName() {        
        return firstName;
    }
    public static String getLastName() {
        return lastName;
    }

    
    private static void setFirstName(String firstName) {
        User.firstName = firstName;
    }
    private static void setLastName(String lastName) {
        User.lastName = lastName;
    }
        
    /*
    Get borrowed books list via a Database connection
    */
    public static ArrayList<Book> getBorrowedList(){
        // sample regno - 19BCI0128
        ArrayList<Book> bookList = new ArrayList<>();
        String query = "select books.isbn, books.name, books.copies, books.author, books.publisher, ledger.overduefee, ledger.dateborrowed from books, ledger where books.isbn=ledger.isbn and registernumber='"+ registerNumber +"'";
        try{
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));                
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return bookList;
    }
    
    public static void removeRecordFromLedger(String isbn){
        int copies;
        String query = "delete from ledger where isbn="+isbn+" and registernumber='"+ registerNumber +"'";
        String get_copies = "select copies from books where isbn="+isbn;
        
        try{
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate(query);
            ResultSet rs = statement.executeQuery(get_copies);
            rs.next();
                copies = rs.getInt(1);
                copies += 1;
            String increment_copies = "update books set copies="+copies+" where isbn="+isbn;
            statement.executeUpdate(increment_copies);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static void addRecordToLedger(Book book){
        String isbn = book.getIsbn();
        String query = "insert into ledger(isbn, registernumber) values("+isbn+", '"+registerNumber+"')";
        int copies = book.getCopies() - 1;
        String decrement_copies = "update books set copies="+copies+" where isbn="+isbn;
        try{
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(decrement_copies);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    
}
