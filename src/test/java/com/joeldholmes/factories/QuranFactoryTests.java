package com.joeldholmes.factories;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.FactoryException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
public class QuranFactoryTests {

	@Autowired
	QuranFactory quranResource;
	
	@Test
	public void testGetVerse() throws FactoryException{
		String content = quranResource.getVerse(QuranVersionEnum.PICKTHALL, 1, 1);
		Assert.assertEquals("In the name of Allah, the Beneficent, the Merciful.", content);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVerse_nullVersion() throws FactoryException{
		quranResource.getVerse(null, 1, 1);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVerse_invalidChapter() throws FactoryException{
		quranResource.getVerse(QuranVersionEnum.PICKTHALL, -1, 1);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetVerse_invalidVerse() throws FactoryException{
		quranResource.getVerse(QuranVersionEnum.PICKTHALL, 1, -1);
	}
	
	@Test
	public void testGetChapterVerses() throws FactoryException{
		Map<Integer, String> verses = quranResource.getVerses(QuranVersionEnum.PICKTHALL, 1);
		Assert.assertEquals(7, verses.size());
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterVerses_nullVersion() throws FactoryException{
		quranResource.getVerses(null, 1);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterVerses_invalidChapter() throws FactoryException{
		quranResource.getVerses(QuranVersionEnum.PICKTHALL, -1);
	}
	
	@Test
	public void testGetChapterName() throws FactoryException{
		String name = quranResource.getChapterName(QuranVersionEnum.PICKTHALL, 2);
		Assert.assertEquals("The Cow", name);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterName_nullVersion() throws FactoryException{
		quranResource.getChapterName(null, 1);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterName_invalidChapter() throws FactoryException{
		quranResource.getChapterName(QuranVersionEnum.PICKTHALL, -1);
	}
	
	@Test
	public void testGetChapterList() throws FactoryException{
		int size = quranResource.getChapters(QuranVersionEnum.PICKTHALL).size();
		Assert.assertEquals(114, size);
	}
	
	@Test(expected=FactoryException.class)
	public void testGetChapterList_nullVersion() throws FactoryException{
		quranResource.getChapters(null);
	}
	
}
