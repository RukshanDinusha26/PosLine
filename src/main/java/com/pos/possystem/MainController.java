/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import javafx.scene.Scene;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import net.sf.jasperreports.engine.design.JasperDesign;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
    private Label transaction_count;

    @FXML
    private TextField item_discount_input;

    @FXML
    private LineChart<?, ?> income_chart;

    @FXML
    private TextField item_name_input;

    @FXML
    private ComboBox<String> item_unit_input;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private TextField barcode_scanner_input;

    @FXML
    private GridPane user_grid_pane;

    @FXML
    private GridPane stock_grid_pane;

    @FXML
    private BorderPane primary_stage;

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
    private Label change_amount;

    @FXML
    private TextField payed_input;

    @FXML
    private VBox itemContainer;

    @FXML
    private TextField new_user_id;

    @FXML
    private TextField new_user_password;

    @FXML
    private TextField confirm_password;

    @FXML
    private Button process_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private GridPane receipt_grid;

    @FXML
    private Label sub_total;

    @FXML
    private Label total;

    @FXML
    private Label Discount;

    @FXML
    private TextField stock_input;

    @FXML
    private Label today_income;

    @FXML
    private Label monthly_income;

    @FXML
    private Label low_stock;

    @FXML
    private TextField stock_item_code_input;

    private String currentMode = "sales_mode";

    private String count;

    LocalDate currentDate = LocalDate.now();

    private StringBuilder barcodeBuffer = new StringBuilder();

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
    private ObservableList<User_data> userList = FXCollections.observableArrayList();
    private ObservableList<ItemData> stockList = FXCollections.observableArrayList();

    public void displayIncomeChart() {
        income_chart.getData().clear();
        income_chart.setLegendVisible(false);

        String sql = "SELECT DATE(date_time),SUM(total_amount) FROM receipt GROUP BY YEAR(DATE(date_time)), MONTH(DATE(date_time));";
        connect = database.connectDb();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMMM"); // Format for year and month name

            while (result.next()) {
                // Parse the date and format it as "yyyy MMMM" (e.g., "2024 November")
                Date date = result.getDate(1);
                String formattedDate = dateFormat.format(date);

                // Add the formatted date and total income to the chart
                chart.getData().add(new XYChart.Data<>(formattedDate, result.getFloat(2)));
            }

            income_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculateLowStockItems() {

        int stockThreshold = 5;
        String sql = "SELECT COUNT(*) AS lowStockCount FROM stock WHERE stock <= ?";

        connect = database.connectDb();
        String lowStockCount = "0";

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, stockThreshold);
            result = prepare.executeQuery();

            while (result.next()) {
                lowStockCount = String.valueOf(result.getInt("lowStockCount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lowStockCount;
    }

    public void displayLowStockItems() {
        String lowStockCount = calculateLowStockItems();

        low_stock.setText(lowStockCount);
    }

    public String calculateMonthlyIncome() {
        String sql = "SELECT SUM(total_amount) AS income FROM receipt "
                + "WHERE YEAR(date_time) = YEAR(CURDATE()) "
                + "AND MONTH(date_time) = MONTH(CURDATE());";

        connect = database.connectDb();
        String income = "0.00";

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                income = String.valueOf(result.getDouble("income")); // Get income as a double
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return income;
    }

    public void displayMonthlyIncome() {
        String monthlyIncome = calculateMonthlyIncome();

        // Assuming `monthly_income_label` is the UI component to display monthly income
        monthly_income.setText("Rs. " + monthlyIncome);
    }

    public String calculateTodayIncome() {
        String sql = "SELECT SUM(total_amount) AS income FROM receipt WHERE DATE(date_time) = CURDATE();";

        connect = database.connectDb();
        String income = "0.00"; // Default to 0 in case no transactions are found

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                income = String.valueOf(result.getDouble("income")); // Get income as a double
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return income;
    }

    public void displayTodayIncome() {
        String income = calculateTodayIncome();

        // Assuming `income_label` is the UI component to display today's income
        today_income.setText("Rs. " + income);
    }

    public String calculateTodayTransactions() {
        String sql = "SELECT COUNT(*) AS count FROM receipt WHERE DATE(date_time) = '"
                + currentDate + "';";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                count = String.valueOf(result.getInt("count"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void displayTodayTransactions() {
        String count = calculateTodayTransactions();

        transaction_count.setText(count);

    }
    
    
    public void generateMonthlyReport(){
                    try{
                        HashMap map = new HashMap();
                        map.put("current_date", currentDate);

                        JasperDesign jDesign = JRXmlLoader.load("\\C:\\Users\\Lenovo\\Documents\\PosSystem\\target\\classes\\com\\pos\\possystem\\dailyReport.jrxml");
                        JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                        JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                        PrintService selectedPrinter = PrintServiceLookup.lookupDefaultPrintService();
                        if (selectedPrinter != null) {
                            System.out.println("Selected Printer: " + selectedPrinter.getName());
                        } else {
                            System.out.println("No default printer set!");
                        }

                        JasperViewer.viewReport(jPrint, false);
                                }
                    catch(Exception e){
                        e.printStackTrace();
                    }
    }
    
    public void generateDailyReport(){
                    try{
                        HashMap map = new HashMap();
                        map.put("current_date", String.valueOf(currentDate));

                        JasperDesign jDesign = JRXmlLoader.load("\\C:\\Users\\Lenovo\\Documents\\PosSystem\\target\\classes\\com\\pos\\possystem\\dailyReport.jrxml");
                        JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                        JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);


                        JasperViewer.viewReport(jPrint, false);
                                }
                    catch(Exception e){
                        e.printStackTrace();
                    }
    }

    public void addReceiptbtn() {
        if (calculateSubTotal() == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Add items to Receipt to Process the Order!");
            alert.showAndWait();
        } else {
            if (payed_input.getText().isEmpty() || Double.parseDouble(payed_input.getText()) < calculateSubTotal()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Enter the right amount payed");
                alert.showAndWait();
                connect = database.connectDb();
            } else {
                try {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to process this order?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        
                        String paymentMethod = "Cash";

                        String receiptSql = "INSERT INTO receipt (sub_total, total_amount, pay_method,amount_payed,change_amount) VALUES(?,?,?,?,?);";

                        prepare = connect.prepareStatement(receiptSql, statement.RETURN_GENERATED_KEYS);

                        prepare.setDouble(1, calculateSubTotal());
                        prepare.setDouble(2, calculateSubTotal());
                        prepare.setString(3, paymentMethod);
                        prepare.setDouble(4, Double.parseDouble(payed_input.getText()));
                        prepare.setDouble(5, calculateChange());

                        prepare.executeUpdate();

                        ResultSet generatedKeys = prepare.getGeneratedKeys();
                        int receiptId = 0;
                        if (generatedKeys.next()) {
                            receiptId = generatedKeys.getInt(1);
                        }

                        String itemSql = "INSERT INTO receipt_items (receipt_id,itemid,quantity,total_price)"
                                + "VALUES(?,?,?,?);";

                        prepare = connect.prepareStatement(itemSql);

                        for (receipt_Data item : receiptList) {

                            prepare.setInt(1, receiptId);
                            prepare.setInt(2, item.getID());
                            prepare.setInt(3, item.getQuantity());
                            prepare.setDouble(4, item.getTotalPrice());
                            prepare.addBatch();
                        }
                        prepare.executeBatch();

                        HashMap map = new HashMap();
                        map.put("getReceipt", receiptId);

                        URL url = getClass().getResource("receipt.jrxml");

                        JasperDesign jDesign = JRXmlLoader.load("\\C:\\Users\\Lenovo\\Documents\\PosSystem\\target\\classes\\com\\pos\\possystem\\receipt.jrxml");
                        JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                        JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                        PrintService selectedPrinter = PrintServiceLookup.lookupDefaultPrintService();
                        if (selectedPrinter != null) {
                            System.out.println("Selected Printer: " + selectedPrinter.getName());
                        } else {
                            System.out.println("No default printer set!");
                        }

                        JasperViewer.viewReport(jPrint, false);
                        /*JasperPrintManager.printReport(jPrint, false);*/

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Sucess Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Order Processed Sucessfully!");
                        alert.showAndWait();

                        clearbtn();
                        displayTodayTransactions();

                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public void clearbtn() {
        receiptList.clear();
        receipt_grid.getChildren().clear();
        resetTotal();
    }

    public void resetTotal() {
        sub_total.setText("0.00");
        total.setText("0.00");
        Discount.setText("0.00");
        payed_input.setText("");
        change_amount.setText("0.00");
    }

    public double getPayed() {
        if (payed_input.getText().isEmpty()) {
            return 0;
        } else {
            Double payed = Double.parseDouble(payed_input.getText());
            return payed;
        }

    }

    public double calculateChange() {

        double payed = getPayed();

        if (payed == 0.0) {
            return 0.0;
        } else {

            double total = calculateSubTotal();

            double change = payed - total;

            return change;
        }
    }

    public void displayChange() {

        try {
            Double change = calculateChange();
            System.out.println(change);
            change_amount.setText(String.format("%.2f", Math.max(change, 0)));
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., empty or non-numeric input)
            change_amount.setText("0.00");
        }
    }

    public void addStock() {
        if (stock_item_code_input.getText().isEmpty()
                || stock_input.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all Blank fields to update the stock");
            alert.showAndWait();

        } else {
            String checkItemId = "SELECT itemid FROM stock WHERE itemid = "
                    + stock_item_code_input.getText();

            connect = database.connectDb();

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkItemId);

                if (result.next()) {
                    String updateItemStock = "UPDATE stock SET stock = stock + ? WHERE itemid = "
                            + stock_item_code_input.getText();

                    try {
                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to update the stock of item with the code: " + stock_item_code_input.getText() + "?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {

                            prepare = connect.prepareStatement(updateItemStock);
                            prepare.setString(1, stock_input.getText());

                            prepare.executeUpdate();

                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Sucess Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Stock is Sucessfully Updated!");
                            alert.showAndWait();

                            displayStockCard();
                            stock_item_code_input.clear();
                            stock_input.clear();

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("There is no item with Item Code: stock_item_code_input!");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public ObservableList<ItemData> stockDataList() {
        ObservableList<ItemData> listData = FXCollections.observableArrayList();

        String sql = "SELECT stock.itemid,stock.stock,item.Name,item.Unit_type FROM stock "
                + "JOIN item ON stock.itemid = item.itemid";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ItemData stockdata;

            while (result.next()) {
                stockdata = new ItemData(result.getInt("stock.itemid"), result.getString("item.Name"), result.getString("item.Unit_type"), result.getInt("stock"));
                listData.add(stockdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(listData.get(0).getItem_name());
        return listData;
    }

    public void displayStockCard() {
        stockList.clear();
        stockList.addAll(stockDataList());
        int row = 0;
        int column = 0;

        stock_grid_pane.getChildren().clear();
        stock_grid_pane.getRowConstraints().clear();
        stock_grid_pane.getColumnConstraints().clear();

        for (int q = 0; q < stockList.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("stockCard.fxml"));
                AnchorPane pane = load.load();
                StockCardController stockC = load.getController();
                System.out.println(stockList.get(q));
                stockC.setData(stockList.get(q));

                if (column == 1) {
                    column = 0;
                    row += 1;
                }

                stock_grid_pane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(5));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<User_data> userDataList() {
        ObservableList<User_data> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM user";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            User_data userdata;

            while (result.next()) {
                userdata = new User_data(result.getInt("User_ID"), result.getString("Password"), result.getDate("created_date"));
                listData.add(userdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(listData.get(0).getUser_id());
        return listData;
    }

    public void addUser() {
        if (new_user_id.getText().isEmpty()
                || new_user_password.getText().isEmpty()
                || confirm_password.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all Blank fields to add a new Account");
            alert.showAndWait();

        } else {

            String checkUserId = "SELECT user_ID FROM user WHERE User_ID = "
                    + new_user_id.getText();

            connect = database.connectDb();

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkUserId);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("user id :" + new_user_id.getText() + " is already taken");
                    alert.showAndWait();
                } else {

                    if (new_user_password.getText().equals(confirm_password.getText())) {
                        String insertData = "INSERT INTO user"
                                + "(User_id,Password,created_date)"
                                + "VALUES(?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, new_user_id.getText());
                        prepare.setString(2, new_user_password.getText());
                        prepare.setString(3, String.valueOf(currentDate));
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Sucess Message");
                        alert.setHeaderText(null);
                        alert.setContentText("User Successfully Added!");
                        alert.showAndWait();
                        userDisplayCard();

                    } else {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Please Retype the matching password");
                        alert.showAndWait();

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(Integer userid) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete User with ID: " + userid + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            String deleteData = "DELETE FROM user WHERE user_id=" + userid;

            try {
                prepare = connect.prepareStatement(deleteData);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucess Message");
                alert.setHeaderText(null);
                alert.setContentText("User Sucessfully Deleted!");
                alert.showAndWait();

                userDisplayCard();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Cancelled");
            alert.showAndWait();

        }

    }

    public void userDisplayCard() {
        userList.clear();
        userList.addAll(userDataList());
        int row = 0;
        int column = 0;

        user_grid_pane.getChildren().clear();
        user_grid_pane.getRowConstraints().clear();
        user_grid_pane.getColumnConstraints().clear();

        for (int q = 0; q < userList.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("UserCard.fxml"));
                AnchorPane pane = load.load();
                UserCardController userC = load.getController();
                System.out.println(userList.get(q));
                userC.setData(userList.get(q));
                userC.setMainController(this);

                if (column == 1) {
                    column = 0;
                    row += 1;
                }

                user_grid_pane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(5));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

                    String insertStock = "INSERT INTO stock"
                            + "(itemid,stock)"
                            + "VALUES(?,1)";

                    prepare = connect.prepareStatement(insertStock);
                    prepare.setString(1, item_code_input.getText());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    itemDataShow();
                    itemInputClear();
                    displayStockCard();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void itemUpdateBtn() {
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
            alert.setContentText("Please Select an Item to Update");
            alert.showAndWait();

        } else {

            String updtId = item_code_input.getText();

            String updateData = "UPDATE item SET itemid = ?, Name = ?, Price = ?, Barcode = ?, Unit_type = ?, Alpha_search = ?, Discount = ? WHERE itemid = ?";

            connect = database.connectDb();

            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update item with the code: " + item_code_input.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, item_code_input.getText());
                    prepare.setString(2, item_name_input.getText());
                    prepare.setString(3, item_price_input.getText());
                    prepare.setString(4, barcode_input.getText());
                    prepare.setString(5, (String) item_unit_input.getSelectionModel().getSelectedItem());
                    prepare.setString(6, alpha_search_input.getText());
                    prepare.setString(7, item_discount_input.getText());
                    prepare.setString(8, updtId);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Sucess Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sucessfully Updated!");
                    alert.showAndWait();

                    itemDataShow();
                    itemInputClear();
                } else {
                    itemInputClear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void itemDeleteBtn() {

        if (item_code_input.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select An Item to Delete");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Item with ID: " + item_code_input.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM item WHERE itemid=" + item_code_input.getText();

                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Sucess Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sucessfully Deleted!");
                    alert.showAndWait();

                    itemDataShow();
                    itemInputClear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();

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

    public void itemSelectData() {

        ItemData itemdata = item_table.getSelectionModel().getSelectedItem();
        int n = item_table.getSelectionModel().getSelectedIndex();

        if ((n - 1) < -1) {
            return;
        }

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
                        result.getString("Barcode"), result.getString("Unit_type"), result.getString("Alpha_search"),
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

    /*
    public void processBarcode(String barcode) {
        ItemData item = fetchItemByBarcode(barcode);
        if (item != null) {
            int quantity = 1;
            addItemToReceipt(item, quantity);
        } else {
            System.out.println("Item not found for barcode: " + barcode);
        }
    }*/
    public ItemData fetchItemByBarcode(String barcode) {

        for (ItemData item : itemListData) {
            System.out.println(item.getItem_barcode());
            if (item.getItem_barcode().equals(barcode)) {
                return item;
            }
        }
        return null;

    }

    public void addItemToReceipt(ItemData item, int quantity) {

        receipt_Data receiptItem = new receipt_Data(item.getItemid(), item.getItem_name(), item.getItem_price(), quantity, item.getItem_unit_type());
        System.out.println("quantity is " + quantity);
        receiptList.add(receiptItem);
        System.out.println("Item added: " + receiptList.get(0).getPrice());
        displayReceiptItems();
        displaySubTotal();
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

    public void searchItems(String query) {
        cardListData.clear();

        // Filter cardListData based on the search query (by name, barcode, or ID)
        cardListData.addAll(filterItems(query));

        // Clear the grid and its constraints
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        int row = 0;
        int column = 0;

        // Add filtered items to the grid
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

    private List<ItemData> filterItems(String query) {
        List<ItemData> filteredItems = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            for (ItemData item : itemListData) {  // Assuming 'allItemsData' is your full item list
                String searchQueryLower = query.toLowerCase();
                if (item.getItem_name().toLowerCase().contains(searchQueryLower)
                        || item.getItem_barcode().contains(searchQueryLower)
                        || String.valueOf(item.getItemid()).contains(searchQueryLower)
                        || item.getItem_alpha_search().toLowerCase().contains(searchQueryLower)) {
                    filteredItems.add(item);
                }
            }
        }
        return filteredItems;
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

    public void displayReceiptItems() {
        receipt_grid.getChildren().clear();
        int row = 0;
        int column = 0;

        for (int q = 0; q < receiptList.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("receiptCard.fxml"));
                AnchorPane pane = load.load();
                ReceiptCardController cardC = load.getController();
                cardC.setData(receiptList.get(q), receiptList);
                cardC.setMainController(this);
                if (column == 1) {
                    column = 0;
                    row += 1;
                }

                receipt_grid.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Print message when Enter key is pressed
            System.out.println("Enter key was pressed!");
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

    public Double calculateSubTotal() {
        Double subTotal = 0.0;

        for (int q = 0; q < receiptList.size(); q++) {
            subTotal += receiptList.get(q).getTotalPrice();
        }

        return subTotal;
    }

    public void displaySubTotal() {
        sub_total.setText(String.valueOf(calculateSubTotal()));
        total.setText(String.valueOf(calculateSubTotal()));
        displayChange();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserSession session = UserSession.getInstance();
        if ("cashier".equals(session.getRole())) {
            items_mode.setDisable(true);
            stock_mode.setDisable(true);
            report_mode.setDisable(true);
            user_mode.setDisable(true);

            items_mode.setVisible(false);
            stock_mode.setVisible(false);
            report_mode.setVisible(false);
            user_mode.setVisible(false);
        }

        displayStockCard();
        userDisplayCard();
        itemUnitTypeList();
        menuDisplayCard();
        itemDataShow();
        displayChange();
        displayTodayTransactions();
        displayTodayIncome();
        displayMonthlyIncome();
        displayLowStockItems();
        displayIncomeChart();

        double change = calculateChange();
        System.out.println(change);

        toggle_btn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Toggle ON: Displaying ItemCard2");
                menuDisplayCard2(); // Load ItemCard2 layout
            } else {
                System.out.println("Toggle OFF: Displaying ItemCard1");
                menuDisplayCard(); // Load ItemCard1 layout
            }

        });

        payed_input.textProperty().addListener((observable, oldValue, newValue) -> {
            displayChange();
        });

        primary_stage.setOnMouseClicked(event -> {
            if (!search_field.isFocused()) {
                primary_stage.requestFocus();
            }
        });

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (search_field.getText().isEmpty()) {
                menuDisplayCard();
            } else {
                searchItems(newValue);
            }
        });

        setupBarcodeScannerListener();

    }

    private void setupBarcodeScannerListener() {
        Platform.runLater(() -> {
            Stage primaryStage = (Stage) primary_stage.getScene().getWindow(); // Obtain the primary stage
            Scene scene = primaryStage.getScene();

            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (search_field.isFocused()) {
                    return;
                }
                if (event.getCode() == KeyCode.ENTER) {

                    String scannedBarcode = barcodeBuffer.toString();
                    System.out.println("Scanned barcode is =" + scannedBarcode);

                    barcodeBuffer.delete(0, barcodeBuffer.length());
                    System.out.println("Empty barcode is =" + barcodeBuffer.toString());
                    processBarcode(scannedBarcode);

                } else {

                    barcodeBuffer.append(event.getText());
                }
            });
        });
    }

    private void processBarcode(String barcode) {
        System.out.println("Scanned Barcode: " + barcode);

        // Check if the item exists and add it to the receipt
        ItemData item = fetchItemByBarcode(barcode);
        if (item != null) {
            int quantity = 1; // Default quantity for barcode scan
            addItemToReceipt(item, quantity);
        } else {
            System.out.println("Item not found for barcode: " + barcode);
        }
    }

    private void navFocus(Button navButton, String mode) {
        navButton.setOnAction(event -> {
            // Check if the mode is changing
            if (!currentMode.equals(mode)) {
                currentMode = mode; // Update the current mode

                // Update styles for all buttons
                updateButtonStyles();
            }
        });
    }

    private void updateButtonStyles() {
        // Check and update styles for each button based on the current mode
        if (currentMode.equals("sales_mode")) {
            setPressedStyle(sales_mode);
            removePressedStyle(report_mode, stock_mode, items_mode);
        } else if (currentMode.equals("inventory_mode")) {
            setPressedStyle(items_mode);
            removePressedStyle(sales_mode, stock_mode, report_mode);
        } else if (currentMode.equals("report_mode")) {
            setPressedStyle(stock_mode);
            removePressedStyle(sales_mode, items_mode, report_mode);
        } else if (currentMode.equals("settings_mode")) {
            setPressedStyle(report_mode);
            removePressedStyle(sales_mode, items_mode, stock_mode);
        }
    }

    private void setPressedStyle(Button button) {
        if (!button.getStyleClass().contains("pressed-style")) {
            button.getStyleClass().add("pressed-style");
        }
    }

    private void removePressedStyle(Button... buttons) {
        for (Button button : buttons) {
            button.getStyleClass().remove("pressed-style");
        }
    }

}
