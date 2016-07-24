package com.joeldholmes.commands;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.joeldholmes.dto.BibleVerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.factories.BibleFactory;
import com.joeldholmes.utils.ErrorCodes;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
@Ignore
public class GetBibleSingleVerseCommandTest {

	@Mock
	BibleFactory bibleFactory;
	
	@InjectMocks
	private GetBibleSingleVerseCommand cmd = new GetBibleSingleVerseCommand();
	
	@Mock
	List<BibleVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws FactoryException{

		Mockito.when(bibleFactory.getVerse(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MOCKED_RESPONSE);
		
		List<BibleVerseDTO> response = cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws FactoryException{
		
		Mockito.when(bibleFactory.getVerse(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new FactoryException(ErrorCodes.NULL_INPUT, "Chapter cannot be null"));
		
		cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		
	}
	
	
}

