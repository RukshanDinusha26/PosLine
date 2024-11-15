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
    
    private final int maxStock = 20;
    
    public void setData(ItemData item_data){
        this.item_data = item_data;
        
        item_name.setText(item_data.getItem_name());
        item_id.setText(String.valueOf(item_data.getItemid()));
        item_stock.setText(String.valueOf(item_data.getItem_stock()));
        item_unit_type.setText(item_data.getItem_unit_type());
        
        updateStockProgress(item_data.getItem_stock());
    }
    
    private void updateStockProgress(int currentStock) {
        if (currentStock < 0) {
            currentStock = 0;
        }

        double stockPercentage = Math.min((double) currentStock / maxStock, 1.0); 

        item_stock_progress.setProgress(stockPercentage);
        
        item_stock_progress.getStyleClass().removeAll("low-stock", "medium-stock", "high-stock");

        if (stockPercentage < 0.3) {
            item_stock_progress.getStyleClass().add("low-stock"); 
        } else if (stockPercentage < 0.7) {
            item_stock_progress.getStyleClass().add("medium-stock");
        } else {
            item_stock_progress.getStyleClass().add("high-stock"); 
        }
        
        // TO DO - make the maxStock changeble by the user.
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
