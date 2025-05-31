package org.isslibrary.repository;

import org.isslibrary.domain.Reader;

import java.sql.Connection;
import java.util.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReaderRepository implements Repository<Long, Reader> {

    private final JdbcUtils dbUtils;

    public ReaderRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Iterable<Reader> findAll() {
        Connection con = dbUtils.getConnection();
        List<Reader> readers = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Reader")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("ID");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String fullName = result.getString("fullName");
                    String cnp = result.getString("cnp");
                    String email = result.getString("email");
                    String phone = result.getString("phone");
                    String address = result.getString("address");
                    String expirationDate = result.getString("expirationDate");

                    Reader reader = new Reader(id, username, password, fullName, cnp, email, phone, address, expirationDate);
                    readers.add(reader);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return readers; // Return the list, even if it's empty
    }

    public Reader findOne(String username, String password) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Reader WHERE username = ? AND password = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("ID");
                    String fullName = result.getString("fullName");
                    String cnp = result.getString("cnp");
                    String email = result.getString("email");
                    String phone = result.getString("phone");
                    String address = result.getString("address");
                    String expirationDate = result.getString("expirationDate");

                    return new Reader(id, username, password, fullName, cnp, email, phone, address, expirationDate);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    /// librarians can find a reader by ID
    public Reader findOneID(Long ID) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Reader WHERE ID = ?")) {
            preStmt.setLong(1, ID);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String fullName = result.getString("fullName");
                    String cnp = result.getString("cnp");
                    String email = result.getString("email");
                    String phone = result.getString("phone");
                    String address = result.getString("address");
                    String expirationDate = result.getString("expirationDate");

                    return new Reader(ID, username, password, fullName, cnp, email, phone, address, expirationDate);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    /// librarians can save readers
    public Reader save(String username, String password, String fullName, String cnp, String email, String phone, String address, String expirationDate) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Reader (username, password, fullName, cnp, email, phone, address, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            preStmt.setString(3, fullName);
            preStmt.setString(4, cnp);
            preStmt.setString(5, email);
            preStmt.setString(6, phone);
            preStmt.setString(7, address);
            preStmt.setString(8, expirationDate);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    /// librarians can delete readers
    public Reader delete(Long id) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Reader WHERE ID = ?")) {
            preStmt.setLong(1, id);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }
    /// librarians can update readers
    public Reader update(Long id, String username, String password, String fullName, String cnp, String email, String phone, String address, String expirationDate) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE Reader SET username = ?, password = ?, fullName = ?, cnp = ?, email = ?, phone = ?, address = ?, expirationDate = ? WHERE ID = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            preStmt.setString(3, fullName);
            preStmt.setString(4, cnp);
            preStmt.setString(5, email);
            preStmt.setString(6, phone);
            preStmt.setString(7, address);
            preStmt.setString(8, expirationDate);
            preStmt.setLong(9, id);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

}
