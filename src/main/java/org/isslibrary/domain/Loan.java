package org.isslibrary.domain;

public class Loan extends Entity<Long> {
    private Reader reader;
    private Book book;
    private int durationDays;
    private boolean returned;

    public Loan(Long ID, Reader reader, Book book, int durationDays, boolean returned) {
        super(ID);
        this.reader = reader;
        this.book = book;
        this.durationDays = durationDays;
        this.returned = returned;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
