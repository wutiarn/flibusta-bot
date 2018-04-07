package com.wutiarn.flibustabot.model.opds;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookSearchResult {
    public String type;
    public String query;

    private static Pattern idPattern = Pattern.compile("tag:search:(?<type>.+):(?<query>.+):");

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Book> entry;

    public void setId(String id) {
        Matcher matcher = idPattern.matcher(id);

        if (!matcher.matches()) {
            throw new RuntimeException(String.format("Failed to parse search id %s", id));
        }

        this.type = matcher.group("type");
        this.query = matcher.group("query");
    }

    public String getQuery() {
        return query;
    }

    public List<Book> getBooks() {
        return entry;
    }
}
