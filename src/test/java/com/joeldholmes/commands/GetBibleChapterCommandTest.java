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
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.impl.BibleService;
import com.joeldholmes.utils.ErrorCodes;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
@Ignore
public class GetBibleChapterCommandTest {

	@Mock
	BibleService bibleService;
	
	@InjectMocks
	private GetBibleChapterCommand cmd = new GetBibleChapterCommand();
	
	@Mock
	List<BibleVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws ServiceException{

		Mockito.when(bibleService.getVersesInChapter(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt())).thenReturn(dtos);
		
		List<BibleVerseDTO> response = cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(bibleService.getVersesInChapter(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt())).thenThrow(new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null"));
		
		cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).execute();
		
	}
	
	
}
