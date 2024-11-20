/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pos.possystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hirushan Premarathna
 */
public class LoginController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Hyperlink forgot;

    @FXML
    private TextField user_id;

    @FXML
    private Button loginBtn;

    @FXML
    private BorderPane main_form;

    @FXML
    private TextField password;
    
   private StringBuilder barcodeBuffer = new StringBuilder();

    // DB TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void login() {

        String sql = "SELECT * FROM User WHERE user_id = ? and password = ?";

        connect = database.connectDb();

        try {
            Alert alert;
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, user_id.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            if (user_id.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    // GET USER ID 
                    Data.userID = user_id.getText();
                    //PROCEED TO HOME

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    // HIDE LOGIN PAGE
                    loginBtn.getScene().getWindow().hide();

                    // GET HOME PAGE
                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setFullScreen(true);
                    stage.setFullScreenExitHint("");
                    stage.setScene(scene);
                    stage.show();

                    Platform.runLater(() -> stage.requestFocus());
                    // Add a listener to handle when the user exits full-screen mode
                    stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
                        if (!isNowFullScreen) {
                            // Center the window after exiting full-screen mode
                            stage.centerOnScreen();
                        }
                    });

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong UserID/Password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("login");
    }
}
