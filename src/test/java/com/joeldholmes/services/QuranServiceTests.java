package com.joeldholmes.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;
import com.joeldholmes.services.interfaces.IQuranService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class QuranServiceTests {

	@Autowired
	IQuranService quranService;

	@Test
	public void testGetSingleVerse() throws Exception{
		List<SearchResource> dtos = quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 3, null, null);
		Assert.assertEquals(1, dtos.size());
		SearchResource result = dtos.iterator().next();
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSingleChapter() throws Exception{
		List<SearchResource> dtos = quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 3, null, 6);
		Assert.assertEquals(4, dtos.size());
		SearchResource result = dtos.iterator().next();
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(6, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSameChapter() throws Exception{
		List<SearchResource> dtos = quranService.getVerses(QuranVersionEnum.PICKTHALL,  2, 3, 2, 6);
		Assert.assertEquals(4, dtos.size());
		SearchResource result = dtos.iterator().next();
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(6, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesMultipleChapter() throws Exception{
		List<SearchResource> dtos = quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 3, 3, 3);
		Assert.assertEquals(287, dtos.size());
		SearchResource result = dtos.iterator().next();
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals("The Family Of Imran", result.getChapterTitle());
		Assert.assertEquals(3, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullType() throws Exception{
		quranService.getVerses(null, 2, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullChapter() throws Exception{
		Integer chapter =null;
		quranService.getVerses(QuranVersionEnum.PICKTHALL, chapter, 3, null, null);
	}
	
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidChapter() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL, -1, 2, null, null);
	}
	
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidVerse() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 2, null, -99);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidLowerVerse() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 2, null, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidLowerThroughChapter() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 2, 1, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidThroughChapter() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL, 2, 2, 99999, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_throughChapter_invalidVerse() throws Exception{
		quranService.getVerses(QuranVersionEnum.PICKTHALL,  2, 2, 3, -1);
	}
	
	@Test
	public void testGetVersesInChapter() throws Exception{
		List<SearchResource> verses = quranService.getVersesInChapter(QuranVersionEnum.PICKTHALL, 2);
		Assert.assertEquals(286, verses.size());
		SearchResource result = verses.get(verses.size()-1);
		Assert.assertEquals("The Cow", result.getChapterTitle());
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(286, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_nullVersion() throws Exception{
		quranService.getVersesInChapter(null, 2);
	}
	
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_invalidChapter() throws Exception{
		quranService.getVersesInChapter(QuranVersionEnum.PICKTHALL, 20000);
	}
	
	@Test
	public void testGetVersesFromString() throws Exception{
		List<SearchResource> verses = quranService.getVersesFromString("The Opening, The Thunder, 3:32-33");
		Assert.assertEquals(52, verses.size());
		verses = quranService.getVersesFromString("The Opening 1, The Thunder, 3:32-33");
		Assert.assertEquals(46, verses.size());
		verses = quranService.getVersesFromString("The Opening 1, 3, The Thunder, 3:32-33");
		Assert.assertEquals(47, verses.size());
		verses = quranService.getVersesFromString("The Opening 1-3, 6-7, The Thunder, 3:32-33");
		Assert.assertEquals(50, verses.size());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesFromString_invalid() throws Exception{
		quranService.getVersesFromString("The Opening, 32-33");
		
	}
	
}

