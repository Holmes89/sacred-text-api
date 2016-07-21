package com.joeldholmes.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.ITaoService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class TaoServiceTests {

	@Autowired
	ITaoService taoService;

	@Test
	public void testGetSingleVerse() throws Exception{
		List<TaoVerseDTO> dtos = taoService.getVersesInRange(2, 3, null, null);
		Assert.assertEquals(1, dtos.size());
		TaoVerseDTO result = dtos.iterator().next();
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSingleChapter() throws Exception{
		List<TaoVerseDTO> dtos = taoService.getVersesInRange(2, 3, null, 4);
		Assert.assertEquals(2, dtos.size());
		TaoVerseDTO result = dtos.iterator().next();
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(4, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesSameChapter() throws Exception{
		List<TaoVerseDTO> dtos = taoService.getVersesInRange(2, 3, 2, 4);
		Assert.assertEquals(2, dtos.size());
		TaoVerseDTO result = dtos.iterator().next();
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(4, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test
	public void testGetMultipleVersesMultipleChapter() throws Exception{
		List<TaoVerseDTO> dtos = taoService.getVersesInRange(2, 3, 3, 3);
		Assert.assertEquals(5, dtos.size());
		TaoVerseDTO result = dtos.iterator().next();
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
		result = dtos.get(dtos.size()-1);
		Assert.assertEquals(3, result.getChapter());
		Assert.assertEquals(3, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullChapter() throws Exception{
		taoService.getVersesInRange(null, 3, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullVerse() throws Exception{
		taoService.getVersesInRange(2, null, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidChapter() throws Exception{
		taoService.getVersesInRange(-1, 2, null, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_throughChapter_nullVerse() throws Exception{
		taoService.getVersesInRange(2, 2, 3, null);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidVerse() throws Exception{
		taoService.getVersesInRange(2, 2, null, 99);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_nullThroughChapter_invalidLowerVerse() throws Exception{
		taoService.getVersesInRange(2, 2, null, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidLowerThroughChapter() throws Exception{
		taoService.getVersesInRange(2, 2, 1, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_invalidThroughChapter() throws Exception{
		taoService.getVersesInRange(2, 2, 99, 1);
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVerses_throughChapter_invalidVerse() throws Exception{
		taoService.getVersesInRange(2, 2, 3, -1);
	}
	
	@Test
	public void testGetVersesInChapter() throws Exception{
		List<TaoVerseDTO> verses = taoService.getVersesInChapter(2);
		Assert.assertEquals(4, verses.size());
		TaoVerseDTO result = verses.get(verses.size()-1);
		Assert.assertEquals(2, result.getChapter());
		Assert.assertEquals(4, result.getVerse());
		Assert.assertNotNull(result.getContent());
	}
	
	@Test(expected=ServiceException.class)
	public void testGetVersesInChapter_invalidChapter() throws Exception{
		taoService.getVersesInChapter(200);
	}
}
