/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structures.Book;
import structures.User;

/**
 *
 * @author Surender
 */
public class ReturnWindowController {
    public static ArrayList<Book> returnList = new ArrayList<>();
    public Text titleMessage;
    public Accordion booksContainer;
    public Text returnMessage;
    public Text overdueMessage;
    public Button cancelButton;
    public Button returnConfirm;
    
    public static float overdueTotal = 0;
    
    
    @FXML
    protected void initialize(){
        for(var book : returnList){
            book.retrieveBookInfo();
            System.out.println("book details: "+book.getName());
        }
        setBooks();
    }
    
    public void setBooks(){
        // get borrowedList
        ArrayList<TitledPane> bookDisplay = new ArrayList<>();
        try{
            for (var book : returnList){

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                BookController.setBook(book);
                BookController.interactiveMode = false;
                TitledPane tp = fxmlLoader.load();
                
                tp.setText(book.getName());
                bookDisplay.add(tp);
            }
            setDetails();
            String msg;
            if(overdueTotal > 0){
                msg= "Please pay an overdue penalty charge of $ " + String.valueOf(overdueTotal) + " at the Payment Counter.";
                overdueMessage.setFill(Color.RED);
                overdueMessage.setText(msg);
            }
            booksContainer.getPanes().addAll(bookDisplay);
        }
        catch(IOException e){
            System.out.println("ddddd"+e);
        }     
    }
    public void finalizeReturn(){
        System.out.println("Finalized return");
        for(var book : returnList){
            var isbn = book.getIsbn();
            User.removeRecordFromLedger(isbn);
        }
        titleMessage.setText("You have successfully returned books");
        titleMessage.setFill(Color.GREEN);
        cancelButton.setText("OK");
        returnConfirm.setVisible(false);
    }
    
    public void onCancel() throws IOException{
        BookController.interactiveMode = true;
        returnList.clear();
        returnConfirm.setVisible(true);
        titleMessage.setFill(Color.BLACK);
        App.setRoot("primary");
    }
    public void setDetails(){
        String msg = "Welcome, " + User.getFirstName() + ", you have successfully logged in";
        titleMessage.setText(msg);
        int returned = returnList.size();
        msg = "Returning " + returned + " books";
        returnMessage.setText(msg);
        
    }
}
