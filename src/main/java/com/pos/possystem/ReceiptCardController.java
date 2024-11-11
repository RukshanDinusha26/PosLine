/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ReceiptCardController implements Initializable {

    
    @FXML
    private Label discount;

    @FXML
    private Label item_name;

    @FXML
    private Label item_total_qty;
    
    @FXML
    private Label tot_price;

    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    
    private receipt_Data receipt_data;
    
    public void setData(receipt_Data receipt_data){
        this.receipt_data = receipt_data;
        
        item_name.setText(receipt_data.getName());
        item_total_qty.setText(String.valueOf(receipt_data.getPrice()));
        tot_price.setText(String.valueOf(receipt_data.getTotalPrice()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
