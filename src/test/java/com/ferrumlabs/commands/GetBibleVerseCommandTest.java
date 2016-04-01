package com.ferrumlabs.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.factories.BibleFactory;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import org.junit.Assert;

@RunWith(PowerMockRunner.class)
public class GetBibleVerseCommandTest {

	@Mock
	BibleFactory bibleFactory;
	
	@InjectMocks
	private GetBibleVerseCommand cmd = new GetBibleVerseCommand();
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws FactoryException{
		
		Mockito.when(bibleFactory.getVerse(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MOCKED_RESPONSE);
		
		String response = cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		Assert.assertEquals("\""+MOCKED_RESPONSE+"\"", response);
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws FactoryException{
		
		Mockito.when(bibleFactory.getVerse(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(FactoryException.class);
		
		cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		
	}
	
	
}
