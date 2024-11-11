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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

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
    private GridPane menu_gridPane;

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

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ObservableList<ItemData> cardListData = FXCollections.observableArrayList();

    public void switchForm(ActionEvent event) {
        if (event.getSource() == sales_mode) {
            sales_panel.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        } else if (event.getSource() == items_mode) {
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(true);
            report_panel.setVisible(false);
            search_field.setVisible(false);
        } else if (event.getSource() == stock_mode) {
            sales_panel.setVisible(false);
            stock_panel.setVisible(true);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        } else if (event.getSource() == report_mode) {
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(true);
            search_field.setVisible(false);
        } else {
            sales_panel.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
        }
    }

    public ObservableList<ItemData> menuGetData() {

        String sql = "SELECT * FROM Item";

        ObservableList<ItemData> listData = FXCollections.observableArrayList();
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ItemData item;

            while (result.next()) {
                item = new ItemData(result.getInt("Itemid"), result.getString("Name"), result.getDouble("Price"), result.getString("Unit_type"));
                listData.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("ItemCard.fxml"));
                AnchorPane pane = load.load();
                ItemCardController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);
                
                GridPane.setMargin(pane,new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() throws IOException {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize() is called"); // Debug statement
        menuDisplayCard();
    }
}
