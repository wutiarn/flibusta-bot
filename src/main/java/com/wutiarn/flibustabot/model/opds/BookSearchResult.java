package com.wutiarn.flibustabot.model.opds;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class BookSearchResult {
    public String title;
    public String id;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Book> entry;
}
