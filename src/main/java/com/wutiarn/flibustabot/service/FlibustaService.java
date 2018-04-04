package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "opds/search")
                .queryParam("searchType", "books")
                .queryParam("searchTerm", query)
                .toUriString();

        BookSearchResult page = restTemplate.getForObject(url, BookSearchResult.class);
    }
}
