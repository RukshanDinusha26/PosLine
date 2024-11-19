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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    @FXML
    private Button clear_item_btn;
    
    private MainController mainController;
    
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private receipt_Data receipt_data;
    private javafx.collections.ObservableList<receipt_Data> receiptList;

    public void setData(receipt_Data receipt_data,javafx.collections.ObservableList<receipt_Data> receiptList) {
        this.receiptList = receiptList;
        this.receipt_data = receipt_data;
        
        item_name.setText(receipt_data.getName());
        item_total_qty.setText(String.valueOf(receipt_data.getQuantity()) + ' '
                + receipt_data.getUnit());
        tot_price.setText(String.valueOf(receipt_data.getTotalPrice()));
        
        clear_item_btn.setOnAction(this::handleClearItem);
    }
    
    private void handleClearItem(ActionEvent event) {
        if (receiptList != null && receipt_data != null) {
            // Remove the current item from the list
            receiptList.remove(receipt_data);
            mainController.displayReceiptItems();
            mainController.displaySubTotal();
            mainController.displayChange();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
