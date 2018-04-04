package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.model.opds.MainPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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

    public void getMainPage() {
        MainPage page = restTemplate.getForObject(BASE_URL + "opds", MainPage.class);
    }
}
