package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;

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

    enum SearchType {
        BOOKS("books"),
        AUTHORS("authors");

        private final String type;
        SearchType(String type) {
            this.type = type;
        }
    }

    private final String BASE_URL = "https://flibusta.is";
    private final RestTemplate restTemplate;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Autowired
    public FlibustaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InputStream getBookFile(String bookId, String format) throws IOException {
        Request request = new Request.Builder()
                .url(String.format("%s/b/%s/%s", BASE_URL, bookId, format))
                .build();
        Response response = okHttpClient.newCall(request).execute();
        assert response.body() != null;
        return response.body().byteStream();
    }

    public BookSearchResult search(String query, SearchType searchType) {
        var url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/opds/search")
                .queryParam("searchType", searchType.type)
                .queryParam("searchTerm", query)
                .build()
                .toUri();

        return restTemplate.getForObject(url, BookSearchResult.class);
    }
}
