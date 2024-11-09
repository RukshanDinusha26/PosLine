/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {
    
    @FXML
    private TextField alpha_search;

    @FXML
    private TextField barcode;


    @FXML
    private Button item_add;

    @FXML
    private TextField item_code;

    @FXML
    private Button item_delete;

    @FXML
    private TextField item_discount;

    @FXML
    private TextField item_name;

    @FXML
    private TextField item_price;

    @FXML
    private TableView<?> item_table;

    @FXML
    private Button item_update;

    @FXML
    private Button items_mode;

    @FXML
    private Button report_mode;

    @FXML
    private Button sales_mode;

    @FXML
    private Button stock_mode;
    
    @FXML
    private HBox stock_panel;
    
    @FXML
    private HBox item_panel;
    
    @FXML
    private HBox sales_panel;
    
    @FXML
    private VBox report_panel;

    @FXML
    private TableColumn<?, ?> table_alpha_search;

    @FXML
    private TableColumn<?, ?> table_barcode;

    @FXML
    private TableColumn<?, ?> table_code;

    @FXML
    private TableColumn<?, ?> table_discount;

    @FXML
    private TableColumn<?, ?> table_name;

    @FXML
    private TableColumn<?, ?> table_price;

    @FXML
    private TableColumn<?, ?> table_unit;

    @FXML
    private TextField unit_type;
    
    @FXML
    private TextField search_field;

    @FXML
    private VBox itemContainer; // This is the VBox placeholder in your FXML
    
    @FXML
    private Button logout_btn;

    private static final String DB_URL = "jdbc:mysql://localhost/appdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @FXML
    public void initialize() {
        populateItems();
    }

    private void populateItems() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT item_name, price, unit_type FROM item")) {

            while (rs.next()) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(744, 77);
                anchorPane.setStyle("-fx-background-color: white;");

                Label itemLabel = new Label(rs.getString("item_name"));
                Label pricePerWeightLabel = new Label(rs.getString("price"));
                TextField priceField = new TextField();
                Label unitLabel = new Label(rs.getString("unit"));
                Label priceLabel = new Label(rs.getString("price"));

                itemLabel.setLayoutX(35.0);
                itemLabel.setLayoutY(29.0);
                pricePerWeightLabel.setLayoutX(157.0);
                pricePerWeightLabel.setLayoutY(28.0);
                priceField.setLayoutX(290.0);
                priceField.setLayoutY(26.0);
                priceField.setPrefWidth(62.0);
                unitLabel.setLayoutX(356.0);
                unitLabel.setLayoutY(28.0);
                priceLabel.setLayoutX(477.0);
                priceLabel.setLayoutY(27.0);
                Label addLabel = new Label("+");
                addLabel.setLayoutX(663.0);
                addLabel.setLayoutY(29.0);
                addLabel.setStyle("-fx-font-size: 18px; -fx-font-family: Arial Black;");

                anchorPane.getChildren().addAll(itemLabel, pricePerWeightLabel, priceField, unitLabel, priceLabel, addLabel);

                itemContainer.getChildren().add(anchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void switchForm(ActionEvent event){
        if(event.getSource()== sales_mode){
            sales_panel.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        }else if(event.getSource()== items_mode){
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(true);
            report_panel.setVisible(false);
            search_field.setVisible(false);
        }else if(event.getSource()== stock_mode){
            sales_panel.setVisible(false);
            stock_panel.setVisible(true);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        }else if(event.getSource()== report_mode){
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(true);
            search_field.setVisible(false);
        }else{
            sales_panel.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        }
    }
    
    public void logout() throws IOException{
                    Alert alert;
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Logout");
                    alert.showAndWait();
                    
                    // HIDE LOGIN PAGE
                    logout_btn.getScene().getWindow().hide();
                    
                    // GET HOME PAGE
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    
                    
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(root);
                    
                    stage.setScene(scene);
                    stage.show();
    }
}

