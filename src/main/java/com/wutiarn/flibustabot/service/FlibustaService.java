package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.exceptions.flibusta.BookBlockedException;
import com.wutiarn.flibustabot.model.opds.BookFile;
import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
public class FlibustaService {

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

    public BookFile getBookFile(String bookId, String format) throws IOException, BookBlockedException {
        Request request = new Request.Builder()
                .url(String.format("%s/b/%s/%s", BASE_URL, bookId, format))
                .build();
        try (Response response = okHttpClient.newCall(request).execute();) {
            assert response.body() != null;
            HttpUrl actualUrl = response.request().url();
            if (!actualUrl.host().equals("static.flibusta.is")) {
                throw new BookBlockedException();
            }

            List<String> pathSegments = actualUrl.pathSegments();

            String filename = pathSegments.get(pathSegments.size() - 1);
            if (!filename.contains(".")) {
                filename = String.format("%s.%s", bookId, format);
            }

            byte[] content = response.body().bytes();
            return new BookFile(filename, content);
        }
    }

    public BookSearchResult searchBooks(String query) {
        var url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/opds/search")
                .queryParam("searchType", SearchType.BOOKS.type)
                .queryParam("searchTerm", query)
                .build()
                .toUri();

        return restTemplate.getForObject(url, BookSearchResult.class);
    }
}
