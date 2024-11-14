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

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class UserCardController implements Initializable {

    @FXML
    private Label User_date_created;

    @FXML
    private Button delete_btn;

    @FXML
    private Label user_id;
    
    private MainController mainController;

    private User_data user_data;

    public void setData(User_data user_data) {
        this.user_data = user_data;

        user_id.setText(String.valueOf(user_data.getUser_id()));
        User_date_created.setText("Created at : " + String.valueOf(user_data.getCreated_date()));

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void delete_btn() {
        if (mainController != null) {
            int userid = Integer.parseInt(user_id.getText());
            mainController.deleteUser(userid); // Add item to MainController's ObservableList
        } else {
            System.out.println("MainController reference not set.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
