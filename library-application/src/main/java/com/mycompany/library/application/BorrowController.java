/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.application;

import static com.mycompany.library.application.BorrowWindow.overdue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structures.Book;
import structures.User;

/**
 *
 * @author Surender
 */
public class BorrowController {
    public TextField textFieldIsbn;
    public Text isbnValidationStatus;
    public Text copiesMessage;
    public Text welcomeMessage;
    public Accordion bookContainer;
    public Button borrowButton;
    public Button cancelButton;
    
    private String isbn;
    private Book book;
    
    public void checkISBN(){
        copiesMessage.setText("");
        isbn = textFieldIsbn.getText();
        borrowButton.setDisable(true);
        if(Pattern.compile("^(978)\\d{10}$").matcher(isbn).matches()){
            book = new Book(isbn);
            if(book.retrieveBookInfo()){
                isbnValidationStatus.setFill(Color.GREEN);
                isbnValidationStatus.setText("Found a book");
                if(book.getCopies() > 0){
                    copiesMessage.setFill(Color.FORESTGREEN);
                    copiesMessage.setText("There are "+book.getCopies()+" copies available");
                    if(!BorrowWindow.foundInBorrowedBooks(isbn)){
                        setBook();
                        borrowButton.setDisable(false);
                    }
                    else{
                        copiesMessage.setFill(Color.RED);
                        copiesMessage.setText("Already borrowed this book. Same person cannot borrow 2 copies");
                    }
                    
                }
                else{
                    copiesMessage.setFill(Color.CRIMSON);
                    copiesMessage.setText("There are no copies available");
                    resetWindow();
                }
            }
            else{
                isbnValidationStatus.setFill(Color.RED);
                isbnValidationStatus.setText("Book not found in records");
                resetWindow();
            }
            
        }
        else{
            isbnValidationStatus.setFill(Color.RED);
            isbnValidationStatus.setText("Invalid ISBN");
            resetWindow();
        }
    }
    
    public void setBook(){
        // get borrowedList
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
            BookController.setBook(book);
            BookController.interactiveMode = false;
            TitledPane tp = fxmlLoader.load();
            tp.setText(book.getName());
            tp.setExpanded(true);
//            tp.setCollapsible(false);                     
            bookContainer.getPanes().add(tp);
        }
        catch(IOException e){
            System.out.println("ddddd"+e);
        }     
    }
    
    public void resetWindow(){
        bookContainer.getPanes().clear();
        
    }
    
    public void finalizeBorrow(){
        User.addRecordToLedger(book);
        welcomeMessage.setFill(Color.GREEN);
        welcomeMessage.setText("You have borrowed the book successfully");
        cancelButton.setText("OK");
        borrowButton.setVisible(false);
    }
    
    public void onCancel() throws IOException{
        App.setRoot("primary");
    }
}
