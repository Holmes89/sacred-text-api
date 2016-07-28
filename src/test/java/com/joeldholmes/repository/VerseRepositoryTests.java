package com.joeldholmes.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.entity.VerseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class VerseRepositoryTests {

	@Autowired
	VerseRepository verseRepo;
	
	@Test
	public void testGetVerse() throws Exception{
		String expectedVerseContent = "Before them fire devours, behind them a flame blazes. Before them the land is like the garden of Eden, behind them, a desert waste- nothing escapes them.";
		VerseEntity verse = verseRepo.getSingleBibleVerse("joel", 2, 3);
		Assert.assertNotNull(verse);
		Assert.assertEquals(expectedVerseContent, verse.getContent());
	}
}
