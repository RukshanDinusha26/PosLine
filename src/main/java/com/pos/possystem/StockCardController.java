/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class StockCardController implements Initializable {

    
    @FXML
    private Label item_id;

    @FXML
    private Label item_name;

    @FXML
    private Label item_stock;

    @FXML
    private ProgressBar item_stock_progress;

    @FXML
    private Label item_unit_type;
    
    private ItemData item_data;
    
    public void setData(ItemData item_data){
        this.item_data = item_data;
        
        item_name.setText(item_data.getItem_name());
        item_id.setText(String.valueOf(item_data.getItemid()));
        item_stock.setText(String.valueOf(item_data.getItem_stock()));
        item_unit_type.setText(item_data.getItem_unit_type());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
