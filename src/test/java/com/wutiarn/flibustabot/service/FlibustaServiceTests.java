package com.wutiarn.flibustabot.service;

import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlibustaServiceTests {

	@Autowired
    public FlibustaService flibustaService;

	@Test
	public void bookSearch() {
		BookSearchResult result = flibustaService.search("1984");
	}

}
