package org.isslibrary.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.isslibrary.domain.Book;
import org.isslibrary.domain.Librarian;
import org.isslibrary.domain.Loan;
import org.isslibrary.domain.Reader;
import org.isslibrary.repository.BookRepository;
import org.isslibrary.repository.LibrarianRepository;
import org.isslibrary.repository.LoanRepository;
import org.isslibrary.repository.ReaderRepository;

import java.util.stream.StreamSupport;

public class Service {
    private BookRepository bookRepository;
    private LibrarianRepository librarianRepository;
    private ReaderRepository readerRepository;
    private LoanRepository loanRepository;
    private Librarian librarian;
    private Reader reader;

    public Service(BookRepository bookRepository, LibrarianRepository librarianRepository, ReaderRepository readerRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.librarianRepository = librarianRepository;
        this.readerRepository = readerRepository;
        this.loanRepository = loanRepository;
    }

    public void login(String username, String password, boolean isLibrarian) {
        if (isLibrarian) {
            librarian = librarianRepository.findOne(username, password);
            if (librarian == null) {
                throw new IllegalArgumentException("Invalid username or password");
            }
        } else {
            reader = readerRepository.findOne(username, password);
            System.out.println("Reader Username: " + username);
            if (reader == null) {
                throw new IllegalArgumentException("Invalid username or password");
            }
        }
    }

    public void logout() {
        librarian = null;
        reader = null;
    }

    public Librarian getLibrarian() {
        return librarian;
    }
    public Reader getReader() {
        return reader;
    }

    public Iterable<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }

    public Iterable<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Iterable<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(String ISBN, String title, String author, String description, int year) {
        return bookRepository.save(ISBN, title, author, description, year);
    }

    public Reader saveReader(String username, String password, String fullName, String cnp, String email, String phone, String address, String expirationDate) {
        return readerRepository.save(username, password, fullName, cnp, email, phone, address, expirationDate);
    }

    public Loan saveLoan(Reader reader, Book book, int durationDays) {
        return loanRepository.save(reader, book, durationDays);
    }
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
    public void deleteReader(Long id) {
        readerRepository.delete(id);
    }
//    public void deleteLoan(Long id) {
//        loanRepository.delete(id);
//    }
    public void updateBook(Long id, String ISBN, String title, String author, String description, int year) {
        bookRepository.update(id, ISBN, title, author, description, year);
    }
    public void updateReader(Long id, String username, String password, String fullName, String cnp, String email, String phone, String address, String expirationDate) {
        readerRepository.update(id, username, password, fullName, cnp, email, phone, address, expirationDate);
    }
    public void updateLoan(Loan loan) {
        loanRepository.update(loan);
    }
    public void borrowBook(Long id) {
        bookRepository.borrow(id);
    }

    // cărți disponibile (disponibile pentru împrumut)
    public ObservableList<Book> getAvailableBooks() {
        return FXCollections.observableArrayList(
                StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                        .filter(book -> book.getA() == 1)
                        .toList()
        );
    }


    // cărți împrumutate de utilizator și ne-returnate
    public ObservableList<Loan> getCurrentLoansForUser(int userId) {
        return FXCollections.observableArrayList(
                StreamSupport.stream(loanRepository.findAll().spliterator(), false)
                        .filter(loan -> !loan.isReturned() && loan.getReader().getId() == userId)
                        .toList()
        );
    }


    // istoric de împrumuturi (returned == true)
    public ObservableList<Loan> getLoanHistoryForUser(Reader currentUser) {
        return FXCollections.observableArrayList(
                StreamSupport.stream(loanRepository.findAll().spliterator(), false)
                        .filter(loan -> loan.isReturned() && loan.getReader().getId().equals(currentUser.getId()))
                        .toList()
        );
    }


    // creează împrumuturi pentru toate cărțile din coș
    public void borrowBooksForUser(ObservableList<Book> books, Reader reader) {
        for (Book book : books) {
            loanRepository.save(reader, book, 30);
            bookRepository.borrow(book.getId());
        }
    }

    public void updateLoanReturned(Loan loan) {
        loanRepository.update(loan);
    }

    public void updateBookAvailability(Book book) {
        bookRepository.updateAvailable(book.getId());
    }


}
