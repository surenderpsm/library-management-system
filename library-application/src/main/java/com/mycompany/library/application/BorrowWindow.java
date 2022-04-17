package com.mycompany.library.application;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.IOException;
import structures.Book;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structures.User;

/**
 *
 * @author Surender
 */
public class BorrowWindow {
    
    public Text welcomeMessage;
    public Text borrowedMessage;
    public Text overdueMessage;
    public Accordion booksContainer;
    public static ArrayList<Book> borrowedList;   
    public Button borrowMoreButton;
    public Button returnAllButton;
    
    public static boolean enableReturnSelected;
    public static float overdue;
    private static int borrowed;
    
    @FXML
    protected void initialize(){
        borrowedList = User.getBorrowedList();
        setDetails();
        BookController.interactiveMode = true;
        setBooks();
    }
    
    public void setDetails(){
        String msg = "Welcome, " + User.getFirstName() + ", you have successfully logged in";
        welcomeMessage.setText(msg);
        borrowed = borrowedList.size();
        msg = "You have borrowed ";
        if(borrowed > 0){
            msg += Integer.toString(borrowed) + " books.";
        }
        else{
            msg += "no books.";
        }
        borrowedMessage.setFill(Color.BLACK);
        borrowMoreButton.setDisable(false);
        if (borrowed >= 4){
            borrowMoreButton.setDisable(true);
            msg = "You have reached a borrow limit of 4 books. Return some books to borrow more.";
            borrowedMessage.setFill(Color.RED);
        }
        else if(borrowed == 0){
            returnAllButton.setDisable(true);
        }
        borrowedMessage.setText(msg);
    }
    
    public void returnAll() throws IOException{
        System.out.println("return all");
        ReturnWindowController.returnList.clear();
        ReturnWindowController.returnList.addAll(borrowedList);
        ReturnWindowController.overdueTotal = overdue;
        App.setRoot("return-window");
    }

    public void borrowMore() throws IOException{
        int amt = 4 - borrowed;
        App.setRoot("borrow");
    }
    
    public void setBooks(){
        // get borrowedList
        overdue = 0;
        ArrayList<TitledPane> bookDisplay = new ArrayList<>();
        try{
            for (var book : borrowedList){
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                BookController.setBook(book);
                TitledPane tp = fxmlLoader.load();
                
                tp.setText(book.getName());
                bookDisplay.add(tp);
                overdue += book.getOverdue();
                
            }
            setDetails();
            String msg;
            if(overdue > 0){
                msg= "You need to pay an overdue penalty charge of $ " + String.valueOf(overdue) + ".";
                overdueMessage.setFill(Color.RED);
            }
            else{
                msg="Congratulations, you have no penalty charges!";
                overdueMessage.setFill(Color.GREEN);
            }
            overdueMessage.setText(msg);
            booksContainer.getPanes().addAll(bookDisplay);
        }
        catch(IOException e){
            System.out.println("ddddd"+e);
        }     
    }
    
    public static boolean foundInBorrowedBooks(String isbn){
        for(var book : borrowedList){
            if(book.getIsbn().equals(isbn)){
                return true;
            }
        }
        return false;
    }
    
    public void onCancel() throws IOException{
        App.setRoot("primary");
    }
}
