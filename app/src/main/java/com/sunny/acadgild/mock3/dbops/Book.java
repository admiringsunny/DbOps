package com.sunny.acadgild.mock3.dbops;

/** this is a pojo class with all the fields of a book in table:
 * id,
 * title and
 * author
 * */
public class Book {

    private int id;
    private String title;
    private String author;

    public Book() { }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
