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
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.services.impl.BibleService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetBibleVerseCommandTest {

	@Mock
	BibleService bibleService;
	
	@InjectMocks
	private GetBibleVerseCommand cmd = new GetBibleVerseCommand();
	
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

		Mockito.when(bibleService.getVersesInRange(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dtos);
		
		List<BibleVerseDTO> response = cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(bibleService.getVersesInRange(Mockito.any(BibleVersionEnum.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setVersion(BibleVersionEnum.KJV).setBook("AGAS").setChapter(-1).setVerse(-1).execute();
		
	}
	
	
}
