package org.isslibrary.repository;

import org.isslibrary.domain.Librarian;

import java.sql.Connection;
import java.util.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianRepository implements Repository<Long, Librarian> {

    private final JdbcUtils dbUtils;

    public LibrarianRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Iterable<Librarian> findAll() {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Librarian")) {
            try (ResultSet result = preStmt.executeQuery()) {
                List<Librarian> librarians = new ArrayList<>();
                while (result.next()) {
                    Long id = result.getLong("ID");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String fullName = result.getString("fullName");
                    String phone = result.getString("phone");

                    Librarian librarian = new Librarian(id, username, password, fullName, phone);
                    librarians.add(librarian);
                }
                return librarians;
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    public Librarian findOne(String username, String password) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Librarian WHERE username = ? AND password = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("ID");
                    String fullName = result.getString("fullName");
                    String phone = result.getString("phone");

                    return new Librarian(id, username, password, fullName, phone);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }


}
