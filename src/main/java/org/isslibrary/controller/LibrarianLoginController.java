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

public class LibrarianLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Librarian Username: " + username);
        System.out.println("Librarian Password: " + password);

        try {
            service.login(username, password, true); // Assuming the same login method works for librarians
            System.out.println("Librarian login successful!");

            openLibrarianPage();

        } catch (IllegalArgumentException e) {
            System.out.println("Librarian login failed: " + e.getMessage());

            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect credentials, please try again.");
            alert.showAndWait();
        }
    }

    private void openLibrarianPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/librarian-main-page.fxml"));
            Parent root = loader.load();

            LibrarianMainPageController controller = loader.getController();
            controller.setService(service); // Pass the service if needed

            Stage stage = (Stage) loginButton.getScene().getWindow();
            //Scene scene = new Scene(root, 470, 394); // Updated dimensions
            Scene scene = new Scene(root); // Use default size
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error loading librarian main page: " + e.getMessage());
            e.printStackTrace();
        }
    }
}