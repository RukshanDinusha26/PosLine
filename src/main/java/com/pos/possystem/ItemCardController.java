/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ItemCardController implements Initializable {

   
    @FXML
    private Button add_btn;

    @FXML
    private AnchorPane form_container;

    @FXML
    private ImageView img_cont;

    @FXML
    private Label item_name;

    @FXML
    private Label item_price;

    @FXML
    private Spinner<?> item_qty;

    @FXML
    private Label qty_type;
    
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
