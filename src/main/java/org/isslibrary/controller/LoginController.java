package org.isslibrary.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.isslibrary.service.Service;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button librarianButton;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Reader Username: " + username);
        System.out.println("Reader Password: " + password);

        try {
            service.login(username, password, false); // Assuming a method for reader login
            System.out.println("Reader login successful!");

            openReaderPage();

        } catch (IllegalArgumentException e) {
            System.out.println("Reader login failed: " + e.getMessage());

            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect credentials, please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLibrarianButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/librarian-login.fxml"));
            Parent root = loader.load();

            LibrarianLoginController librarianController = loader.getController();
            librarianController.setService(service);

            Stage stage = (Stage) librarianButton.getScene().getWindow();
            Scene scene = new Scene(root, 470, 394); // Updated dimensions
            stage.setScene(scene);

        } catch (IOException e) {
            System.out.println("Error loading librarian login page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openReaderPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/user-main-page.fxml"));
            Parent root = loader.load();

            ReaderMainPageController controller = loader.getController();
            controller.setService(service);  // SETEZI service AICI!
            controller.setCurrentUser(service.getReader());

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root, 812, 510);
            stage.setScene(scene);

        } catch (IOException e) {
            System.out.println("Error loading user main page: " + e.getMessage());
            e.printStackTrace();
        }
    }


}