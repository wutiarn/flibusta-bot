package com.wutiarn.flibustabot.service;

import org.springframework.stereotype.Service;

@Service
public class FlibustaService {
    private static final String BASE_URL = "https://flibusta.is/";

    enum FileFormat {
        FB2("fb2"),
        EPUB("epub"),
        MOBI("mobi");


        private final String format;
        FileFormat(String format) {
            this.format = format;
        }
    }

    public void getBookById(int id, FileFormat format) {

    }

    public void getMainPage() {

    }
}
