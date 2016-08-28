package com.joeldholmes.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;
import com.joeldholmes.services.interfaces.ISearchService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class SearchServiceTests {

	@Autowired
	ISearchService searchService;
	
	@Test
	public void testSearchAllText() throws Exception{
		List<SearchResource> results = searchService.searchAllText("hatred");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		
		int bibleCount = 0;
		int quranCount = 0;
		for(SearchResource verse: results){
			String text = verse.religiousText;
			if(text.equalsIgnoreCase("bible"))
				bibleCount++;
			else
				quranCount++;
		}
		
		Assert.assertEquals(29, bibleCount);
		Assert.assertEquals(16, quranCount);
	}
	
	@Test
	public void testSearchAllText_no_results() throws Exception{
		List<SearchResource> results = searchService.searchAllText("asdflkja;sldkfapoisdfja;sldjf;aosidjf;lasjdf;");
		Assert.assertNull(results);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllText_null() throws Exception{
		searchService.searchAllText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllText_empty() throws Exception{
		searchService.searchAllText("");
	}
	
	@Test
	public void testSearchBibleText() throws Exception{
		List<SearchResource> results = searchService.searchBibleText("hatred");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		
		Assert.assertEquals(29, results.size());
	}
	
	@Test
	public void testSearchAllBibleText_no_results() throws Exception{
		List<SearchResource> results = searchService.searchBibleText("asdflkja;sldkfapoisdfja;sldjf;aosidjf;lasjdf;");
		Assert.assertNull(results);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllBibleText_null() throws Exception{
		searchService.searchBibleText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllBibleText_empty() throws Exception{
		searchService.searchBibleText("");
	}
	
	@Test
	public void testSearchQuranText() throws Exception{
		List<SearchResource> results = searchService.searchQuranText("Jesus");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		
		Assert.assertEquals(25, results.size());
	}
	
	@Test
	public void testSearchAllQuranText_no_results() throws Exception{
		List<SearchResource> results = searchService.searchQuranText("asdflkja;sldkfapoisdfja;sldjf;aosidjf;lasjdf;");
		Assert.assertNull(results);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllQuranText_null() throws Exception{
		searchService.searchQuranText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllQuranText_empty() throws Exception{
		searchService.searchQuranText("");
	}
	
	@Test
	public void testSearchTaoText() throws Exception{
		List<SearchResource> results = searchService.searchTaoText("Love");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		
		Assert.assertEquals(8, results.size());
	}
	
	@Test
	public void testSearchAllTaoText_no_results() throws Exception{
		List<SearchResource> results = searchService.searchTaoText("asdflkja;sldkfapoisdfja;sldjf;aosidjf;lasjdf;");
		Assert.assertNull(results);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllTaoText_null() throws Exception{
		searchService.searchTaoText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllTaoText_empty() throws Exception{
		searchService.searchTaoText("");
	}
	
	@Test
	public void testSearchAllVerseAndText() throws Exception{
		List<SearchResource> results = searchService.searchAllVerseAndText("hatred");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(45, results.size());
		
		results = searchService.searchAllVerseAndText("Joel 2:3-6, Exodus 3:9-11, 4:14");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(8, results.size());
		
		results = searchService.searchAllVerseAndText("The Opening 1-3, 6-7, The Thunder, 3:32-33");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(50, results.size());
		
		results = searchService.searchAllVerseAndText("2:1, 1:3-4");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(6, results.size());
		
		results = searchService.searchAllVerseAndText("sadfasdfj;aslkdfj;alskdjf;asdf");
		Assert.assertTrue(results.isEmpty());
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllVerseAndText_null() throws Exception{
		searchService.searchAllVerseAndText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchAllVerseAndText_empty() throws Exception{
		searchService.searchAllVerseAndText("");
	}
	
	@Test
	public void testSearchBibleVerseAndText() throws Exception{
		List<SearchResource> results = searchService.searchBibleVerseAndText("hatred");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(29, results.size());
		
		results = searchService.searchBibleVerseAndText("Joel 2:3-6, Exodus 3:9-11, 4:14");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(8, results.size());
		
		results = searchService.searchBibleVerseAndText("sadfasdfj;aslkdfj;alskdjf;asdf");
		Assert.assertTrue(results.isEmpty());
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchBibleVerseAndText_null() throws Exception{
		searchService.searchBibleVerseAndText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchBibleVerseAndText_empty() throws Exception{
		searchService.searchBibleVerseAndText("");
	}
	
	@Test
	public void testSearchQuranVerseAndText() throws Exception{
		List<SearchResource> results = searchService.searchQuranVerseAndText("Jesus");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(25, results.size());
		
		results = searchService.searchQuranVerseAndText("The Opening 1-3, 6-7, The Thunder, 3:32-33");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(50, results.size());
		
		results = searchService.searchQuranVerseAndText("sadfasdfj;aslkdfj;alskdjf;asdf");
		Assert.assertTrue(results.isEmpty());
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchQuranVerseAndText_null() throws Exception{
		searchService.searchQuranVerseAndText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchQuranVerseAndText_empty() throws Exception{
		searchService.searchQuranVerseAndText("");
	}
	
	@Test
	public void testSearchTaoVerseAndText() throws Exception{
		List<SearchResource> results = searchService.searchTaoVerseAndText("Love");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(8, results.size());
		
		results = searchService.searchTaoVerseAndText("2:1, 1:3-4");
		Assert.assertNotNull(results);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertEquals(3, results.size());
		
		results = searchService.searchTaoVerseAndText("sadfasdfj;aslkdfj;alskdjf;asdf");
		Assert.assertTrue(results.isEmpty());
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchTaoVerseAndText_null() throws Exception{
		searchService.searchTaoVerseAndText(null);
	}
	
	@Test(expected=ServiceException.class)
	public void testSearchTaoVerseAndText_empty() throws Exception{
		searchService.searchTaoVerseAndText("");
	}
}
