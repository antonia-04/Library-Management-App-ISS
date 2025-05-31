package org.isslibrary.domain;

public class Reader extends Entity<Long> {
    private String username;
    private String password;
    private String fullName;
    private String cnp;
    private String email;
    private String phone;
    private String address;
    private String expirationDate;

    public Reader(Long ID, String username, String password, String fullName, String cnp, String email, String phone, String address, String expirationDate) {
        super(ID);
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.cnp = cnp;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.expirationDate = expirationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
