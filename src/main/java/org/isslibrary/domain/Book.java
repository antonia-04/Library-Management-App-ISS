package org.isslibrary.domain;

public class Book extends Entity<Long> {
    private String ISBN;
    private String title;
    private String author;
    private String description;
    private int year;
    private boolean available; // if the book is available for loan

    public Book(Long ID, String ISBN, String title, String author, String description, int year, boolean available) {
        super(ID);
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.description = description;
        this.year = year;
        this.available = available;
    }


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean getAvailable() {
        return available;
    }
    public int getA() {
        int i = !this.available ? 0 : 1;
        return i; // Convert boolean to int for database storage
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
