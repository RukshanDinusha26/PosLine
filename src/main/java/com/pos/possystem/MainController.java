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
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

    @FXML
    private TextField alpha_search_input;

    @FXML
    private TextField barcode_input;

    @FXML
    private Button item_add_btn;

    @FXML
    private TextField item_code_input;

    @FXML
    private Button item_delete_btn;

    @FXML
    private TextField item_discount_input;

    @FXML
    private TextField item_name_input;

    @FXML
    private ComboBox<String> item_unit_input;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ToggleButton toggle_btn;

    @FXML
    private TextField item_price_input;

    @FXML
    private TableView<ItemData> item_table;

    @FXML
    private Button item_update_btn;

    @FXML
    private Button items_mode;

    @FXML
    private Button report_mode;

    @FXML
    private Button sales_mode;

    @FXML
    private Button user_mode;

    @FXML
    private Button stock_mode;

    @FXML
    private HBox stock_panel;

    @FXML
    private VBox item_panel;

    @FXML
    private HBox user_panel;

    @FXML
    private HBox sales_panel;

    @FXML
    private VBox report_panel;

    @FXML
    private TableColumn<ItemData, String> table_alpha_search;

    @FXML
    private TableColumn<ItemData, String> table_barcode;

    @FXML
    private TableColumn<ItemData, String> table_code;

    @FXML
    private TableColumn<ItemData, String> table_discount;

    @FXML
    private TableColumn<ItemData, String> table_name;

    @FXML
    private TableColumn<ItemData, String> table_price;

    @FXML
    private TableColumn<ItemData, String> table_unit;

    @FXML
    private TextField unit_type;

    @FXML
    private TextField search_field;

    @FXML
    private VBox itemContainer;

    @FXML
    private Button logout_btn;

    @FXML
    private GridPane receipt_grid;

    private static final String DB_URL = "jdbc:mysql://localhost/appdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;

    private ObservableList<ItemData> cardListData = FXCollections.observableArrayList();
    private ObservableList<receipt_Data> receiptList = FXCollections.observableArrayList();

    public void itemAddBtn() {
        if (item_code_input.getText().isEmpty()
                || item_name_input.getText().isEmpty()
                || barcode_input.getText().isEmpty()
                || item_price_input.getText().isEmpty()
                || item_discount_input.getText().isEmpty()
                || alpha_search_input.getText().isEmpty()
                || item_unit_input.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all Blank fields to Add the item");
            alert.showAndWait();

        } else {

            String checkItemCode = "SELECT itemid FROM item WHERE itemid = '"
                    + item_code_input.getText() + "'";

            connect = database.connectDb();

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkItemCode);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(item_code_input.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO item"
                            + "(itemid,Name,Price,Barcode,Unit_type,Alpha_search,Discount)"
                            + "VALUES(?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, item_code_input.getText());
                    prepare.setString(2, item_name_input.getText());
                    prepare.setString(3, item_price_input.getText());
                    prepare.setString(4, barcode_input.getText());
                    prepare.setString(5, (String) item_unit_input.getSelectionModel().getSelectedItem());
                    prepare.setString(6, alpha_search_input.getText());
                    prepare.setString(7, item_discount_input.getText());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    itemDataShow();
                    itemInputClear();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void itemInputClear() {
        item_name_input.setText("");
        barcode_input.setText("");
        item_price_input.setText("");
        item_discount_input.setText("");
        alpha_search_input.setText("");
        item_unit_input.getSelectionModel().clearSelection();

    }
    
    public void itemSelectData(){
        
        ItemData itemdata = item_table.getSelectionModel().getSelectedItem();
        int n = item_table.getSelectionModel().getSelectedIndex();
        
        if((n-1) < -1) return;
        
        item_code_input.setText(String.valueOf(itemdata.getItemid()));
        item_name_input.setText(itemdata.getItem_name());
        barcode_input.setText(String.valueOf(itemdata.getItem_barcode()));
        item_price_input.setText(String.valueOf(itemdata.getItem_price()));
        item_discount_input.setText(String.valueOf(itemdata.getItem_discount()));
        alpha_search_input.setText(itemdata.getItem_alpha_search());
        item_unit_input.setValue(itemdata.getItem_unit_type());
         
    }

    public ObservableList<ItemData> itemDataList() {
        ObservableList<ItemData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ItemData itemData;

            while (result.next()) {
                itemData = new ItemData(result.getInt("Itemid"), result.getString("Name"), result.getDouble("Price"),
                        result.getInt("Barcode"), result.getString("Unit_type"), result.getString("Alpha_search"),
                        result.getDouble("Discount"));

                listData.add(itemData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<ItemData> itemListData;

    public void itemDataShow() {
        itemListData = itemDataList();
        System.out.println(itemListData.get(0).getItem_price());

        table_code.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        table_alpha_search.setCellValueFactory(new PropertyValueFactory<>("item_alpha_search"));
        table_barcode.setCellValueFactory(new PropertyValueFactory<>("item_barcode"));
        table_unit.setCellValueFactory(new PropertyValueFactory<>("item_unit_type"));
        table_price.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        table_discount.setCellValueFactory(new PropertyValueFactory<>("item_discount"));

        item_table.setItems(itemListData);

    }

    private String[] typeList = {"kg", "g", "l", "ml", "qty"};

    public void itemUnitTypeList() {
        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        item_unit_input.setItems(listData);

    }

    public void addItemToReceipt(ItemData item, int quantity) {

        receipt_Data receiptItem = new receipt_Data(item.getItemid(), item.getItem_name(), item.getItem_price(), quantity, item.getItem_unit_type());
        System.out.println("quantity is " + quantity);
        receiptList.add(receiptItem);
        System.out.println("Item added: " + receiptList.get(0).getPrice());
        displayReceiptItems();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == sales_mode) {
            sales_panel.setVisible(true);
            search_field.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
            user_panel.setVisible(false);
        } else if (event.getSource() == items_mode) {
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(true);
            report_panel.setVisible(false);
            search_field.setVisible(false);
            user_panel.setVisible(false);
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
            user_panel.setVisible(false);
        } else if (event.getSource() == user_mode) {
            sales_panel.setVisible(false);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
            search_field.setVisible(false);
            user_panel.setVisible(true);
        } else {
            sales_panel.setVisible(true);
            stock_panel.setVisible(false);
            item_panel.setVisible(false);
            report_panel.setVisible(false);
            search_field.setVisible(true);
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

    public void menuDisplayCard2() {

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

                if (column == 4) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                FXMLLoader load = new FXMLLoader(getClass().getResource("ItemCard_2.fxml"));
                AnchorPane pane = load.load();
                ItemCard_2Controller cardC = load.getController();
                cardC.setData(cardListData.get(q));
                cardC.setMainController(this);

                if (column == 1) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayReceiptItems() {

        int row = 0;
        int column = 0;

        for (int q = 0; q < receiptList.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("receiptCard.fxml"));
                AnchorPane pane = load.load();
                ReceiptCardController cardC = load.getController();
                cardC.setData(receiptList.get(q));
                System.out.println(receiptList.get(q).getQuantity());
                if (column == 1) {
                    column = 0;
                    row += 1;
                }

                receipt_grid.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(5));
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

        itemUnitTypeList();
        menuDisplayCard();
        itemDataShow();

        toggle_btn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Toggle ON: Displaying ItemCard2");
                menuDisplayCard2(); // Load ItemCard2 layout
            } else {
                System.out.println("Toggle OFF: Displaying ItemCard1");
                menuDisplayCard(); // Load ItemCard1 layout
            }

        });

    }
}
