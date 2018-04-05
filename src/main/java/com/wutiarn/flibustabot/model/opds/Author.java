package com.wutiarn.flibustabot.model.opds;

public class Author {
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String id;

    public void setUri(String uri) {
        this.id = uri.replace("/a/", "");
    }
}
