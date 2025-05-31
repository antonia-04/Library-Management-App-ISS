package org.isslibrary.repository;

import org.isslibrary.domain.Book;
import org.isslibrary.domain.Loan;
import org.isslibrary.domain.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LoanRepository implements Repository<Long, Loan> {
    private final JdbcUtils dbUtils;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;


    public LoanRepository(Properties props, ReaderRepository readerRepository, BookRepository bookRepository) {
        this.dbUtils = new JdbcUtils(props);
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Iterable<Loan> findAll() {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Loan")) {
            try (ResultSet result = preStmt.executeQuery()) {
                List<Loan> loans = new ArrayList<>();
                while (result.next()) {
                    Long id = result.getLong("ID");
                    Long readerID = result.getLong("readerID");
                    Long bookID = result.getLong("bookID");
                    int durationDays = result.getInt("durationDays");
                    boolean returned = result.getBoolean("returned");
                    // get the reader and book objects using their respective repositories
                    Reader reader = readerRepository.findOneID(readerID);
                    Book book = bookRepository.findOneID(bookID);

                    Loan loan = new Loan(id, reader, book, durationDays, returned);
                    loans.add(loan);
                }
                return loans;
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    // save a loan
    // this method should be called when a reader borrows a book
    // Book's available field should be set to false
    public Loan save(Reader reader, Book book, int durationDays) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Loan (readerID, bookID, durationDays, returned) VALUES (?, ?, ?, ?)")) {
            preStmt.setLong(1, reader.getId());
            preStmt.setLong(2, book.getId());
            preStmt.setInt(3, durationDays);
            preStmt.setBoolean(4, false);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    // update loan -> return the book
    // this method should be called when a reader returns a book
    // Book's available field should be set to true
    public Loan update(Loan loan) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE Loan SET returned = ? WHERE ID = ?")) {
            preStmt.setBoolean(1, true);
            preStmt.setLong(2, loan.getId());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }


}