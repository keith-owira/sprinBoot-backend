package com.kyoskInfraOps.getBook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class BooksModel {

    @Id
    private String id;
    private String title;
    private String author;
    public String getId() {
        return id;
    }


    public void setId(String id) {
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

    public BooksModel() {}
    public BooksModel(String title, String author) {
        this.title = title;
        this.author = author;
    }




}
