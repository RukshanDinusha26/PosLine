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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ItemCard_2Controller implements Initializable {
    
    private MainController mainController;
    
    @FXML
    private Button add_btn;
    
    // Method to set the main controller reference
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddButtonAction() {
        if (mainController != null) {
            int quantity = Integer.parseInt(item_qty.getText());
            System.out.println(quantity);
            mainController.addItemToReceipt(item_data, quantity); // Add item to MainController's ObservableList
        } else {
            System.out.println("MainController reference not set.");
        }
    }

    @FXML
    private Label discount;

    @FXML
    private Label item_name;

    @FXML
    private Label item_price;

    @FXML
    private TextField item_qty;

    @FXML
    private Label qty_type;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    
    private ItemData item_data;
    
    public void setData(ItemData item_data){
        this.item_data = item_data;
        
        item_name.setText(item_data.getItemName());
        item_price.setText(String.valueOf(item_data.getPrice()));
        qty_type.setText(item_data.getUnitType());
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
