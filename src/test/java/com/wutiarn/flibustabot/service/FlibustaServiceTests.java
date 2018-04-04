package com.wutiarn.flibustabot.service;

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
	    flibustaService.search("1984");
	}

}
