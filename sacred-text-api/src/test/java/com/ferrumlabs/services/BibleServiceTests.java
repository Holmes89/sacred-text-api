package com.ferrumlabs.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ferrumlabs.SacredTextApiApplication;
import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.services.interfaces.IBibleService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class BibleServiceTests {

	@Autowired
	IBibleService bibleService;

	@Test
	public void testGetSingleVerse() throws Exception{
		List<BibleVerseDTO> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, null, null);
		Assert.assertEquals(1, dtos.size());
		BibleVerseDTO result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSingleChapter() throws Exception{
		List<BibleVerseDTO> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, null, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseDTO result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(6, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSameChapter() throws Exception{
		List<BibleVerseDTO> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, 2, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseDTO result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(6, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesMultipleChapter() throws Exception{
		List<BibleVerseDTO> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, 3, 3);
		Assert.assertEquals(33, dtos.size());
		BibleVerseDTO result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(3, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullType() throws Exception{
		bibleService.getVersesInRange(null, "Joel", 2, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullBook() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, null, 2, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidBook() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "asdfasdf", 2, 1, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullChapter() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", null, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullVerse() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, null, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidChapter() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", -1, 2, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_throughChapter_nullVerse() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, 3, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidVerse() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, null, 99);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidLowerVerse() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, null, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidLowerThroughChapter() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, 1, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidThroughChapter() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, 99, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_throughChapter_invalidVerse() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 2, 3, -1);
	}
	
	@Test
	public void testGetVersesInChapter() throws Exception{
		List<BibleVerseDTO> verses = bibleService.getVersesInChapter(BibleVersionEnum.KJV, "Joel", 2);
		Assert.assertEquals(32, verses.size());
		BibleVerseDTO result = verses.get(verses.size()-1);
		Assert.assertEquals("Joel", result.getBook());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(32, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_nullVersion() throws Exception{
		bibleService.getVersesInChapter(null, "Joel", 2);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_nullBook() throws Exception{
		bibleService.getVersesInChapter(BibleVersionEnum.KJV, null, 2);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_invalidBook() throws Exception{
		bibleService.getVersesInChapter(BibleVersionEnum.KJV, "asdfasdf", 2);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_invalidChapter() throws Exception{
		bibleService.getVersesInChapter(BibleVersionEnum.KJV, "asdfasdf", 200);
	}
}
