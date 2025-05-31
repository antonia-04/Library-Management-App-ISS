package org.isslibrary.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.isslibrary.domain.Reader;
import org.isslibrary.service.Service;

public class ReaderDetailsController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField cnpField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField expirationDateField;

    private Service service;
    private LibrarianMainPageController mainController;
    private Reader readerToUpdate;

    public void setService(Service service) {
        this.service = service;
    }

    public void setMainController(LibrarianMainPageController mainController) {
        this.mainController = mainController;
    }

    public void setReaderToUpdate(Reader reader) {
        this.readerToUpdate = reader;
        populateFields();
    }

    private void populateFields() {
        if (readerToUpdate != null) {
            usernameField.setText(readerToUpdate.getUsername());
            passwordField.setText(readerToUpdate.getPassword());
            fullNameField.setText(readerToUpdate.getFullName());
            cnpField.setText(readerToUpdate.getCnp());
            emailField.setText(readerToUpdate.getEmail());
            phoneField.setText(readerToUpdate.getPhone());
            addressField.setText(readerToUpdate.getAddress());
            expirationDateField.setText(readerToUpdate.getExpirationDate());
        }
    }

    @FXML
    private void handleSave() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String cnp = cnpField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String expirationDate = expirationDateField.getText();

        if (readerToUpdate == null) {
            service.saveReader(username, password, fullName, cnp, email, phone, address, expirationDate);
        } else {
            service.updateReader(readerToUpdate.getId(), username, password, fullName, cnp, email, phone, address, expirationDate);
        }

        mainController.refreshReaders();
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
