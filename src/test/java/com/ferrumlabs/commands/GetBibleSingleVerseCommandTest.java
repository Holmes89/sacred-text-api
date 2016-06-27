package com.ferrumlabs.commands;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.factories.BibleFactory;
import com.ferrumlabs.utils.ErrorCodes;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
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

