package org.isslibrary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.isslibrary.domain.Book;
import org.isslibrary.domain.Reader;
import org.isslibrary.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianMainPageController {

    // Readers Table
    @FXML
    private TableView<Reader> readersTable;

    @FXML
    private TableColumn<Reader, String> usernameColumn;

    @FXML
    private TableColumn<Reader, String> fullNameColumn;

    @FXML
    private TableColumn<Reader, String> cnpColumn;

    @FXML
    private TableColumn<Reader, String> emailColumn;

    @FXML
    private TableColumn<Reader, String> phoneColumn;

    @FXML
    private TableColumn<Reader, String> addressColumn;

    @FXML
    private TableColumn<Reader, String> expirationDateColumn;

    // Books Table
    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> bookISBNColumn;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableColumn<Book, String> bookAuthorColumn;

    @FXML
    private TableColumn<Book, String> bookDescriptionColumn;

    @FXML
    private TableColumn<Book, Integer> bookYearColumn;

    @FXML
    private TableColumn<Book, Boolean> bookAvailableColumn;

    private Service service;
    private ObservableList<Reader> readersList;
    private ObservableList<Book> booksList;

    private boolean isEditMode = false;
    private Book selectedBook;

    public void setService(Service service) {
        this.service = service;
        loadReaders();
        loadBooks();
    }

    @FXML
    public void initialize() {
        // Bind columns to Reader properties
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));

        // Bind columns to Book properties
        bookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        bookYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    }

    private void loadReaders() {
        Iterable<Reader> readersIterable = service.getAllReaders();
        List<Reader> readers = new ArrayList<>();
        readersIterable.forEach(readers::add); // Convert Iterable to List
        readersList = FXCollections.observableArrayList(readers);
        readersTable.setItems(readersList);
    }

    void loadBooks() {
        Iterable<Book> booksIterable = service.getAllBooks();
        List<Book> books = new ArrayList<>();
        booksIterable.forEach(books::add); // Convert Iterable to List
        booksList = FXCollections.observableArrayList(books);
        booksTable.setItems(booksList);
    }


    @FXML
    private void handleDeleteReader() {
        Reader selectedReader = readersTable.getSelectionModel().getSelectedItem();
        if (selectedReader != null) {
            service.deleteReader(selectedReader.getId());
            readersList.remove(selectedReader);
        } else {
            showAlert("No Reader Selected", "Please select a reader to delete.");
        }
    }


    @FXML
    private void handleAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/book-details.fxml"));
            AnchorPane root = loader.load();

            BookDetailsController controller = loader.getController();
            controller.setService(service);
            controller.setLibrarianMainPageController(this);

            Stage stage = new Stage();
            stage.setTitle("Add Book");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleDeleteBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            service.deleteBook(selectedBook.getId());
            booksList.remove(selectedBook);
        } else {
            showAlert("No Book Selected", "Please select a book to delete.");
        }
    }

    @FXML
    private void handleUpdateBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/book-details.fxml"));
                AnchorPane root = loader.load();

                BookDetailsController controller = loader.getController();
                controller.setService(service);
                controller.setLibrarianMainPageController(this);
                controller.setBookToUpdate(selectedBook); // trimitem cartea selectată pentru update

                Stage stage = new Stage();
                stage.setTitle("Update Book");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Book Selected", "Please select a book to update.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // TAB READER

    @FXML
    private void handleAddReader() {
        openReaderDetailsWindow(null);
    }

    @FXML
    private void handleUpdateReader() {
        Reader selectedReader = readersTable.getSelectionModel().getSelectedItem();
        if (selectedReader != null) {
            openReaderDetailsWindow(selectedReader);
        } else {
            showAlert("No Reader Selected", "Please select a reader to update.");
        }
    }

    private void openReaderDetailsWindow(Reader reader) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/reader-details.fxml"));
            AnchorPane root = loader.load();

            ReaderDetailsController controller = loader.getController();
            controller.setService(service);
            controller.setMainController(this);
            if (reader != null) {
                controller.setReaderToUpdate(reader);
            }

            Stage stage = new Stage();
            stage.setTitle(reader == null ? "Add Reader" : "Update Reader");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshReaders() {
        loadReaders(); // reîncarcă lista după Add/Update
    }

}