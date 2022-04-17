package com.mycompany.library.application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.regex.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structures.User;

public class PrimaryController {
    public TextField tfRegisterNumber;
    public Text statusPrint;
    public Button OK;
    
    
    public void checkRegisterNumberExists() throws IOException{
        String regNo = tfRegisterNumber.getText();
        System.out.println("regno"+regNo);
        if(Pattern.compile("^\\d{2}[A-Za-z]{3}\\d{4}$").matcher(regNo).matches()){
            System.out.println(regNo.toUpperCase());
            User.setRegisterNumber(regNo.toUpperCase());
            if(User.retrieveName()){
                App.setRoot("books-display");
            }
            else{
                statusPrint.setText("Register Number does not exist");
                statusPrint.setFill(Color.RED);
            } 
        }
        else{
            statusPrint.setText("Invalid Register Number");
            statusPrint.setFill(Color.RED);
        }
    }
    
    public void validateRegisterNumber() throws IOException{
        String regNo = tfRegisterNumber.getText();
        if(Pattern.compile("^\\d{2}[A-Za-z]{3}\\d{4}$").matcher(regNo).matches()){
            statusPrint.setText("Valid Register Number");
            statusPrint.setFill(Color.GREEN);
            OK.setDisable(false);
        }
        else{
            statusPrint.setText("Invalid Register Number");
            statusPrint.setFill(Color.RED);
            OK.setDisable(true);
        } 
    }
}
