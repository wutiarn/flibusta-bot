package com.wutiarn.flibustabot.model.opds;

public class BookFile {
    public BookFile(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public String filename;
    public byte[] content;
}
