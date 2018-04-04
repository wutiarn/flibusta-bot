package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.model.opds.Book;
import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class FlibustaService {

    enum FileFormat {
        FB2("fb2"),
        EPUB("epub"),
        MOBI("mobi");


        private final String format;
        FileFormat(String format) {
            this.format = format;
        }
    }

    private final String BASE_URL = "https://flibusta.is/";
    private final RestTemplate restTemplate;

    @Autowired
    public FlibustaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getBookById(int id, FileFormat format) {

    }

    public void search(String query) {
        var params = new HashMap<String, String>();
        params.put("searchType", "books");
        params.put("searchTerm", query);

        BookSearchResult page = restTemplate.getForObject(BASE_URL + "opds/search", BookSearchResult.class, params);
    }
}
