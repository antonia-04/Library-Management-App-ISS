package org.isslibrary.domain;

public class Librarian extends Entity<Long> {
    private String username;
    private String password;
    private String fullName;
    private String phone;

    public Librarian(Long ID, String username, String password, String fullName, String phone) {
        super(ID);
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Long getId() {
        return super.getId();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
