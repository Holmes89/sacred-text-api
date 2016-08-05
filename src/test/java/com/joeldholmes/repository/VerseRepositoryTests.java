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
	IVerseRepository verseRepo;
	
	@Test
	public void testGetSingleBibleVerse() throws Exception{
		String expectedVerseContent = "Before them fire devours, behind them a flame blazes. Before them the land is like the garden of Eden, behind them, a desert waste- nothing escapes them.";
		VerseEntity verse = verseRepo.getSingleBibleVerse("niv", "joel", 2, 3);
		Assert.assertNotNull(verse);
		Assert.assertEquals(expectedVerseContent, verse.getContent());
	}
	
	@Test
	public void testGetBibleVersesInChapter() throws Exception{
		List<VerseEntity> verses = verseRepo.getBibleVersesInChapter("niv","joel", 2);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(32, verses.size());
	}
	
	@Test
	public void testGetBibleVersesInChapterWithVerses() throws Exception{
		List<VerseEntity> verses = verseRepo.getBibleVersesInChapter("niv","joel", 2, 3, 7);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(5, verses.size());
	}
	
	@Test
	public void testGetBibleVersesInChapterRange() throws Exception{
		List<VerseEntity> verses = verseRepo.getBibleVersesInChapterRange("niv","joel", 2, 3);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(53, verses.size());
	}
	
	@Test
	public void testGetSingleQuranVerse() throws Exception{
		String expectedVerseContent = "Those who believe in the unseen and keep up prayer and spend out of what We have given them.";
		VerseEntity verse = verseRepo.getSingleQuranVerse("shakir", 2, 3);
		Assert.assertNotNull(verse);
		Assert.assertEquals(expectedVerseContent, verse.getContent());
	}
	
	@Test
	public void testGetQuranVersesInChapter() throws Exception{
		List<VerseEntity> verses = verseRepo.getQuranVersesInChapter("shakir", 13);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(43, verses.size());
	}
	
	@Test
	public void testGetQuranVersesInChapterWithVerses() throws Exception{
		List<VerseEntity> verses = verseRepo.getQuranVersesInChapter("shakir", 2, 3, 7);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(5, verses.size());
	}
	
	@Test
	public void testGetQuranVersesInChapterRange() throws Exception{
		List<VerseEntity> verses = verseRepo.getQuranVersesInChapterRange("shakir", 13, 14);
		Assert.assertNotNull(verses);
		Assert.assertTrue(!verses.isEmpty());
		Assert.assertEquals(95, verses.size());
	}
}
