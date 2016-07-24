package com.joeldholmes.repository;

import java.util.List;

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
	public void testRead() throws Exception{
		List<VerseEntity> verses = verseRepo.getVerse("bible", "Judges", 11, 29);
		Assert.assertEquals(1, verses.size());
	}
}
