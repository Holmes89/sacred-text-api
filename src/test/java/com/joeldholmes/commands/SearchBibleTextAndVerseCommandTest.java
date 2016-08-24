package com.joeldholmes.commands;

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

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.impl.SearchService;
import com.joeldholmes.utils.ErrorCodes;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class SearchBibleTextAndVerseCommandTest {

	@Mock
	SearchService searchService;
	
	@InjectMocks
	private SearchBibleTextAndVerseCommand cmd = new SearchBibleTextAndVerseCommand();
	
	@Mock
	List<VerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws ServiceException{

		Mockito.when(searchService.searchBibleVerseAndText(Mockito.anyString())).thenReturn(dtos);
		
		List<VerseDTO> response = cmd.setTerm("AGAS").execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(searchService.searchBibleVerseAndText(Mockito.anyString())).thenThrow(new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null"));
		
		cmd.setTerm("AGAS").execute();
		
	}
	
	
}
