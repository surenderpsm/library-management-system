/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Surender
 */
public class Book {
    private String isbn;
    private String name;
    private int copies;
    private String author;
//    private float rating;
    private String publisher;
    private String borrowDate;
    private float overdue;
    
    public Book(String isbn) {
        this.isbn = isbn;
    }
    
    public Book(){
        
    }

    public Book(String isbn, String name, int copies, String author, String publisher, String overdue, String borrowDate) {
        this.isbn = isbn;
        this.name = name;
        this.copies = copies;
        this.author = author;
//        this.rating = rating;
        setPublisher(publisher);
        this.overdue = Float.valueOf(overdue);
        this.borrowDate = borrowDate;
    }
    
    public void setBook(String isbn, String name, int copies, String author, String publisher, String overdue, String borrowDate) {
        this.isbn = isbn;
        this.name = name;
        this.copies = copies;
        this.author = author;
        setPublisher(publisher);
        this.overdue = Float.valueOf(overdue);
        this.borrowDate = borrowDate;
    }
    
    public void setBook(String isbn, String name, int copies, String author, String publisher) {
        this.isbn = isbn;
        this.name = name;
        this.copies = copies;
        this.author = author;
        setPublisher(publisher);
        this.overdue = 0;
        this.borrowDate = "";
    }
    
    public float getOverdue() {
        return overdue;
    }

    public void setOverdue(float overdue) {
        this.overdue = overdue;
    }
    
    public String getBorrowDate(){
        return borrowDate;
    }
    
    public void setBorrowDate(String date){
        this.borrowDate = date;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher.substring(0, publisher.length()-1);
        
    }
    
    public boolean retrieveBookInfo(){
        String query = "select books.isbn, books.name, books.copies, books.author, books.publisher from books where books.isbn="+ isbn;
        try{
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                setBook(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));                
            }
            else{
                System.out.println("No record found");
                return false;
            }
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return true;
    }    
    
    public static Book testObject(){
        Book book = new Book("9782221112223432");
        book.setAuthor("J.K. Rowling");
        book.setName("Harry Potter and the Evil Sanitizer");
        book.setCopies(10);
        book.publisher = "Penguin Publishers";
//        book.setRating((float) 4.55);
        book.setBorrowDate("2022-01-02");
        book.setOverdue(2);
        return book;
    }
    
}
