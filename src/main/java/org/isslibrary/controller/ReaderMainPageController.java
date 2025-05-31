package org.isslibrary.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.isslibrary.domain.Book;
import org.isslibrary.domain.Loan;
import org.isslibrary.domain.Reader;
import org.isslibrary.service.Service;

public class ReaderMainPageController {

    @FXML private TableView<Book> availableBooksTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;

    @FXML private TableView<Book> basketTable;
    @FXML private TableColumn<Book, String> basketTitleColumn;
    @FXML private TableColumn<Book, String> basketAuthorColumn;

    @FXML private TableView<Loan> currentLoansTable;
    @FXML private TableColumn<Loan, String> loanTitleColumn;
    @FXML private TableColumn<Loan, String> loanDateColumn;

    @FXML private TableView<Loan> historyTable;
    @FXML private TableColumn<Loan, String> historyTitleColumn;
    @FXML private TableColumn<Loan, String> returnDateColumn;

    private final ObservableList<Book> basketBooks = FXCollections.observableArrayList();
    private Long currentUserId;

    private Service service;
    private Reader currentUser;


    public void setService(Service service) {
        this.service = service;
    }


    public void initData() {
        // Init columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        basketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        basketAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        loanTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBook().getTitle()));

        loanDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDurationDays() + " zile"));

        historyTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBook().getTitle()));

        returnDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isReturned() ? "Returned" : "Not returned"));


        refreshTables();
    }

    public void setCurrentUser(Reader user) {
        this.currentUser = user;
        this.currentUserId = user.getId();
        initData();
        refreshTables();
    }


    public void initialize(){

    }



    public void refreshTables() {
        availableBooksTable.setItems(service.getAvailableBooks());
        basketTable.setItems(basketBooks);
        currentLoansTable.setItems(service.getCurrentLoansForUser(Math.toIntExact(currentUserId)));
        historyTable.setItems(service.getLoanHistoryForUser(currentUser));
    }


    @FXML
    public void handleBorrowBooks() {
        service.borrowBooksForUser(basketBooks, currentUser);
        basketBooks.clear();
        refreshTables();
    }


    @FXML
    public void handleAddToBasket() {
        Book selected = availableBooksTable.getSelectionModel().getSelectedItem();
        if (selected != null && !basketBooks.contains(selected)) {
            basketBooks.add(selected);
        }
    }

    @FXML
    private void handleReturnBook() {
        Loan selectedLoan = currentLoansTable.getSelectionModel().getSelectedItem();
        if (selectedLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Return Book");
            alert.setHeaderText(null);
            alert.setContentText("Please select a loan to mark as returned.");
            alert.showAndWait();
            return;
        }

        selectedLoan.setReturned(true);
        Book book = selectedLoan.getBook();

        try {
            service.updateLoanReturned(selectedLoan);
            service.updateBookAvailability(book);
            refreshTables();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Return Book");
            alert.setHeaderText(null);
            alert.setContentText("Book marked as returned successfully.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Return Book");
            alert.setHeaderText(null);
            alert.setContentText("Error while marking book as returned: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }


}
