package com.joeldholmes.factories;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.exceptions.FactoryException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class TaoFactoryTests {
	
	@Autowired
	TaoFactory taoResource;
	
	@Test
	public void testGetVerse() throws FactoryException{
		String content = taoResource.getVerse(1, 1);
		Assert.assertEquals("The Tao that can be trodden is not the enduring and unchanging Tao. The name that can be named is not the enduring and unchanging name.", content);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVerse_invalidChapter() throws FactoryException{
		taoResource.getVerse(-1, 1);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVerse_invalidVerse() throws FactoryException{
		taoResource.getVerse(1, -1);
	}
	
	@Test
	public void testGetChapter() throws FactoryException{
		Map<Integer, String> verses = taoResource.getChapter(1);
		Assert.assertEquals(4, verses.size());
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapter_invalidChapter() throws FactoryException{
		taoResource.getChapter(-1);
	}
	
	@Test
	public void testGetChapterList(){
		List<Integer> chapterList = taoResource.getChapterList();
		Assert.assertEquals(81, chapterList.size());
	}
}
