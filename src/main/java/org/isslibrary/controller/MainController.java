package org.isslibrary.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import org.isslibrary.service.Service;

public class MainController {

    @FXML
    private ImageView imageView;

    @FXML
    private Button loginButton;

    Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleLoginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setService(service);

            Scene scene = new Scene(root, 470, 394); // Updated dimensions
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
