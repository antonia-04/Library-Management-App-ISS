package org.isslibrary.repository;

import org.isslibrary.domain.Book;

import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements Repository<Long, Book> {

    private final JdbcUtils dbUtils;

    public BookRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Iterable<Book> findAll() {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Book")) {
            try (ResultSet result = preStmt.executeQuery()) {
                List<Book> books = new ArrayList<>();
                while (result.next()) {
                    Long id = result.getLong("ID");
                    String ISBN = result.getString("ISBN");
                    String title = result.getString("title");
                    String author = result.getString("author");
                    String description = result.getString("description");
                    int year = result.getInt("year");
                    boolean available = result.getBoolean("available");

                    books.add(new Book(id, ISBN, title, author, description, year, available));
                }
                return books;
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book findOne(String title) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Book WHERE title = ?")) {
            preStmt.setString(1, title);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("ID");
                    String ISBN = result.getString("ISBN");
                    String author = result.getString("author");
                    String description = result.getString("description");
                    int year = result.getInt("year");
                    boolean available = result.getBoolean("available");

                    return new Book(id, ISBN, title, author, description, year, available);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book findOneID(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Book WHERE ID = ?")) {
            preStmt.setLong(1, ID);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String ISBN = result.getString("ISBN");
                    String title = result.getString("title");
                    String author = result.getString("author");
                    String description = result.getString("description");
                    int year = result.getInt("year");
                    boolean available = result.getBoolean("available");

                    return new Book(ID, ISBN, title, author, description, year, available);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book save(String ISBN, String title, String author, String description, int year) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "INSERT INTO Book (ISBN, title, author, description, year, available) VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            preStmt.setString(1, ISBN);
            preStmt.setString(2, title);
            preStmt.setString(3, author);
            preStmt.setString(4, description);
            preStmt.setInt(5, year);
            preStmt.setBoolean(6, true); // Default to available
            preStmt.executeUpdate();

            try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    return new Book(id, ISBN, title, author, description, year, true);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book update(Long ID, String ISBN, String title, String author, String description, int year) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE Book SET ISBN = ?, title = ?, author = ?, description = ?, year = ? WHERE ID = ?")) {
            preStmt.setString(1, ISBN);
            preStmt.setString(2, title);
            preStmt.setString(3, author);
            preStmt.setString(4, description);
            preStmt.setInt(5, year);
            preStmt.setLong(6, ID);
            preStmt.setBoolean(7, true); // Default to available

            int rowsUpdated = preStmt.executeUpdate();
            if (rowsUpdated > 0) {
                return new Book(ID, ISBN, title, author, description, year, true);
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book updateAvailable(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE Book SET available = ? WHERE ID = ?")) {
            preStmt.setBoolean(1, true);
            preStmt.setLong(2, ID);

            int rowsUpdated = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }


    public Book delete(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Book WHERE ID = ?")) {
            Book book = findOneById(ID);
            if (book != null) {
                preStmt.setLong(1, ID);
                preStmt.executeUpdate();
                return book;
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Book borrow(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE Book SET available = ? WHERE ID = ?")) {
            Book book = findOneById(ID);
            if (book != null && book.getAvailable()) {
                preStmt.setBoolean(1, false);
                preStmt.setLong(2, ID);
                preStmt.executeUpdate();
                book.setAvailable(false);
                return book;
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    private Book findOneById(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Book WHERE ID = ?")) {
            preStmt.setLong(1, ID);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String ISBN = result.getString("ISBN");
                    String title = result.getString("title");
                    String author = result.getString("author");
                    String description = result.getString("description");
                    int year = result.getInt("year");
                    boolean available = result.getBoolean("available");

                    return new Book(ID, ISBN, title, author, description, year, available);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

}
