/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structures.Book;

/**
 *
 * @author Surender
 */
public class BookController {
    public Text bookTitle;
    public Text bookISBN;
    public Text bookAuthor;
    public Text bookPublisher;
    public Text bookBorrow;
    public Text bookOverdue;
    public Button returnButton;
    public Text labelOverdue;
    public Text labelBorrow;

    public static boolean interactiveMode = true;
    private static Book bookBuffer;
    
    @FXML
    protected void initialize(){
        try{
            System.out.println("Initialising a book..."+bookBuffer.getName());
            bookTitle.setText(bookBuffer.getName());
            bookISBN.setText(bookBuffer.getIsbn());
            bookISBN.setId("isbn"+bookBuffer.getIsbn());
            bookAuthor.setText(bookBuffer.getAuthor());
            bookPublisher.setText(bookBuffer.getPublisher());
            bookBorrow.setText(bookBuffer.getBorrowDate());
            bookOverdue.setText("$ "+Float.toString(bookBuffer.getOverdue()));
            bookOverdue.setId("overdue"+bookBuffer.getIsbn());
            if(bookBuffer.getOverdue() > 0){
                bookOverdue.setFill(Color.RED);
            }
            returnButton.setId(bookBuffer.getIsbn());
            returnButton.setVisible(interactiveMode);
            bookOverdue.setVisible(interactiveMode);
            labelOverdue.setVisible(interactiveMode);
            labelBorrow.setVisible(interactiveMode);
            bookBorrow.setVisible(interactiveMode);
        }
        catch(Exception e){
            System.out.println("Hello from initialize...");
        }
    }
    
    
    public static void setBook(Book book){
        System.out.println("Book has been set by static: "+book.toString());
        bookBuffer = book;
    }
    @FXML
    public void returnBook(MouseEvent e) throws IOException{
        System.out.println("Added to return ");
        Button clicked = (Button) e.getSource();
        String isbn = clicked.getId();
        Text node = (Text) App.getScene().lookup("#isbn"+isbn);
        System.out.println("xxxxxxx-"+node.getText());
        ReturnWindowController.returnList.add(new Book(node.getText()));
        node = (Text) App.getScene().lookup("#overdue"+isbn);
        ReturnWindowController.overdueTotal = Float.parseFloat(node.getText().substring(1));
        System.out.println("overdue-total: "+Float.parseFloat(node.getText().substring(1)));
        App.setRoot("return-window");
    }
}
