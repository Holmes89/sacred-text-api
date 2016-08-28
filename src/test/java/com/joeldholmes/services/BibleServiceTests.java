package com.joeldholmes.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.BibleVerseResource;
import com.joeldholmes.services.interfaces.IBibleService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BibleServiceTests {

	@Autowired
	IBibleService bibleService;

	@Test
	public void testGetSingleVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, null, null);
		Assert.assertEquals(1, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetMultipleVersesSingleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, null, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetMultipleVersesSameChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, 2, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetMultipleVersesMultipleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", 2, 3, 3, 3);
		Assert.assertEquals(33, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
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
	public void testGetVerses_invalidChapter() throws Exception{
		bibleService.getVersesInRange(BibleVersionEnum.KJV, "Joel", -1, 2, null, null);
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
		List<BibleVerseResource> verses = bibleService.getVersesInChapter(BibleVersionEnum.KJV, "Joel", 2);
		Assert.assertEquals(32, verses.size());
		BibleVerseResource result = verses.get(verses.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(32, result.verse);
		Assert.assertNotNull(result.content);
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
	
	@Test
	public void testGetVerse_SingleVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 3, null, null);
		Assert.assertEquals(1, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerse_MultipleVersesSingleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 3, null, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerses_MultipleVersesSameChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 3, 2, 6);
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerse_MultipleVersesMultipleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 3, 3, 3);
		Assert.assertEquals(33, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
	}	
	
	@Test
	public void testGetVerse_MultipleChapters() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, null, 3, null);
		Assert.assertEquals(53, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(21, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerse_SingleChapters() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, null, null, null);
		Assert.assertEquals(32, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(32, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerse_MultipleChapterEndVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, null, 3, 2);
		Assert.assertEquals(34, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(2, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_nullType() throws Exception{
		bibleService.getVerses(null, "Joel", 2, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_nullBook() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, null, 2, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_invalidBook() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "asdfasdf", 2, 1, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_nullChapter() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", null, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_invalidChapter() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", -1, 2, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_nullThroughChapter_invalidVerse() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 2, null, 99);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_nullThroughChapter_invalidLowerVerse() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 2, null, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_invalidLowerThroughChapter() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 2, 1, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_invalidThroughChapter() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 2, 99, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerse_throughChapter_invalidVerse() throws Exception{
		bibleService.getVerses(BibleVersionEnum.KJV, "Joel", 2, 2, 3, -1);
	}
	
	@Test
	public void testGetVerseFromString_SingleVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3");
		Assert.assertEquals(1, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6");
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6,9");
		Assert.assertEquals(5, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(9, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusVerseRange() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6,9-11");
		Assert.assertEquals(7, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(11, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusVerseRange_book_test() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "1 kings 2:3-6,9-11");
		Assert.assertEquals(7, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("1 Kings", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("1 Kings", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(11, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusChapterVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6, 3:9");
		Assert.assertEquals(5, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(9, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusChapterVerseRange() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6,3:9-11");
		Assert.assertEquals(7, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(11, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusBookChapterVerseRange() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6, Exodus 3:9-11");
		Assert.assertEquals(7, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Exodus", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(11, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusBookChapterVerseRange_2() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6, Exodus 3:9-11, 14");
		Assert.assertEquals(8, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Exodus", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(14, result.verse);
		Assert.assertNotNull(result.content);
	}
	@Test
	public void testGetVerseFromString_MultipleVersesSingleChapterPlusBookChapterVerseRange_3() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-6, Exodus 3:9-11, 4:14");
		Assert.assertEquals(8, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Exodus", result.book);
		Assert.assertEquals(4, result.chapter);
		Assert.assertEquals(14, result.verse);
		Assert.assertNotNull(result.content);
	}
	@Test
	public void testGetVerseFromStrings_MultipleVersesSameChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-2:6");
		Assert.assertEquals(4, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(6, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleVersesMultipleChapter() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2:3-3:3");
		Assert.assertEquals(33, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(3, result.verse);
		Assert.assertNotNull(result.content);
	}	
	
	@Test
	public void testGetVerseFromString_MultipleChapters() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2-3");
		Assert.assertEquals(53, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(21, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_SingleChapters() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2");
		Assert.assertEquals(32, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(32, result.verse);
		Assert.assertNotNull(result.content);
	}
	
	@Test
	public void testGetVerseFromString_MultipleChapterEndVerse() throws Exception{
		List<BibleVerseResource> dtos = bibleService.getVersesFromString(BibleVersionEnum.KJV, "Joel 2-3:2");
		Assert.assertEquals(34, dtos.size());
		BibleVerseResource result = dtos.iterator().next();
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(2, result.chapter);
		Assert.assertEquals(1, result.verse);
		Assert.assertNotNull(result.content);
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("Joel", result.book);
		Assert.assertEquals(3, result.chapter);
		Assert.assertEquals(2, result.verse);
		Assert.assertNotNull(result.content);
	}
	
}