package org.isslibrary.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.isslibrary.domain.Book;
import org.isslibrary.service.Service;

public class BookDetailsController {
    private Service service;

    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField yearField;

    private LibrarianMainPageController librarianMainPageController;

    public void setService(Service service) {
        this.service = service;
    }

    public void setLibrarianMainPageController(LibrarianMainPageController controller) {
        this.librarianMainPageController = controller;
    }

    private Book bookToUpdate = null;

    public void setBookToUpdate(Book book) {
        this.bookToUpdate = book;
        if (book != null) {
            isbnField.setText(book.getISBN());
            isbnField.setDisable(true);
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            descriptionField.setText(book.getDescription());
            yearField.setText(String.valueOf(book.getYear()));
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String description = descriptionField.getText();
            String yearText = yearField.getText();

            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || description.isEmpty() || yearText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Incomplete Fields", "All fields must be filled!");
                return;
            }

            int year = Integer.parseInt(yearText);
            service.updateBook(bookToUpdate.getId(), bookToUpdate.getISBN(), title, author, description, year);
            showAlert(Alert.AlertType.INFORMATION, "Book Updated", "The book has been successfully updated!");


            if (librarianMainPageController != null) {
                librarianMainPageController.loadBooks();
            }

            Stage stage = (Stage) isbnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Operation failed");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleSave() {
        if (bookToUpdate != null) {
            handleUpdate();
            return;
        }
        try {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String description = descriptionField.getText();
            String yearText = yearField.getText();

            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || description.isEmpty() || yearText.isEmpty()) {
                Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
                emptyFieldsAlert.setTitle("Incomplete Fields");
                emptyFieldsAlert.setHeaderText(null);
                emptyFieldsAlert.setContentText("All fields must be filled!");
                emptyFieldsAlert.showAndWait();
                return;
            }

            int year = Integer.parseInt(yearText);

            service.saveBook(isbn, title, author, description, year);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Save Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("The book has been successfully saved!");
            successAlert.showAndWait();

            if (librarianMainPageController != null) {
                librarianMainPageController.loadBooks();
            }

            Stage stage = (Stage) isbnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Operation failed");
            errorAlert.showAndWait();
        }
    }
}