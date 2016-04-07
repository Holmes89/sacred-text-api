package com.ferrumlabs.factories;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ferrumlabs.SacredTextApiApplication;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class BibleFactoryTests {

	@Autowired
	BibleFactory bibleResource;
	
	@Test
	public void testGetVerse() throws FactoryException{
		String verse = bibleResource.getVerse(BibleVersionEnum.NIV, "John", 11, 35);
		Assert.assertNotNull(verse);
		Assert.assertEquals(verse, "Jesus wept.");
		verse = bibleResource.getVerse(BibleVersionEnum.NIV, "JOHN", 11, 35);
		Assert.assertNotNull(verse);
		Assert.assertEquals(verse, "Jesus wept.");
		verse = bibleResource.getVerse(BibleVersionEnum.NIV, "john", 11, 35);
		Assert.assertNotNull(verse);
		Assert.assertEquals(verse, "Jesus wept.");
		verse = bibleResource.getVerse(BibleVersionEnum.NIV, "jOHn", 11, 35);
		Assert.assertNotNull(verse);
		Assert.assertEquals(verse, "Jesus wept.");
	}
	
	@Test(expected=FactoryException.class)
	public void testNullEnum() throws FactoryException{
		bibleResource.getVerse(null, "John", 11, 35);
	}
	
	@Test(expected=FactoryException.class)
	public void testNullBook() throws FactoryException{
		bibleResource.getVerse(BibleVersionEnum.NIV, null, 11, 35);
	}
	
	@Test(expected=FactoryException.class)
	public void testEmptyBook() throws FactoryException{
		bibleResource.getVerse(BibleVersionEnum.NIV, "", 11, 35);
	}
	
	@Test(expected=FactoryException.class)
	public void testInvalidBook() throws FactoryException{
		bibleResource.getVerse(BibleVersionEnum.NIV, "asdfasd", 11, 35);
	}
	
	@Test(expected=FactoryException.class)
	public void testInvalidChapter() throws FactoryException{
		bibleResource.getVerse(BibleVersionEnum.NIV, "John", 99, 35);
	}
	
	@Test(expected=FactoryException.class)
	public void testInvalidVerse() throws FactoryException{
		bibleResource.getVerse(BibleVersionEnum.NIV, "asdfasd", 11, 21321);
	}
	
	@Test
	public void testGetVerses() throws FactoryException{
		Map<Integer, String> versus = bibleResource.getVerses(BibleVersionEnum.NIV, "John", 1);
		Assert.assertEquals(51, versus.size());
	}
	@Test(expected=FactoryException.class)
	public void testGetVersesNullEnum() throws FactoryException{
		bibleResource.getVerses(null, "John", 11);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVersesNullBook() throws FactoryException{
		bibleResource.getVerses(BibleVersionEnum.NIV, null, 11);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVersesEmptyBook() throws FactoryException{
		bibleResource.getVerses(BibleVersionEnum.NIV, "", 11);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVersesInvalidBook() throws FactoryException{
		bibleResource.getVerses(BibleVersionEnum.NIV, "asdfasd", 11);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVersesInvalidChapter() throws FactoryException{
		bibleResource.getVerses(BibleVersionEnum.NIV, "John", 99);
	}
	
	public void testGetChapterList() throws FactoryException{
		List<Integer> chapters = bibleResource.getChapterList(BibleVersionEnum.NIV, "John");
		Assert.assertEquals(21, chapters.size());
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterListNullEnum() throws FactoryException{
		bibleResource.getChapterList(null, "John");
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterListNullBook() throws FactoryException{
		bibleResource.getChapterList(BibleVersionEnum.NIV, null);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterListEmptyBook() throws FactoryException{
		bibleResource.getChapterList(BibleVersionEnum.NIV, "");
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterListInvalidBook() throws FactoryException{
		bibleResource.getChapterList(BibleVersionEnum.NIV, "asdfasd");
	}
}
