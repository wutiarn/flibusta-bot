package com.wutiarn.flibustabot.model.opds;

public class Book {
    public String title;
    @SuppressWarnings("WeakerAccess")
    public String id;
    public Author author;

    public void setId(String id) {
        this.id = id.replace("tag:book:", "");
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
