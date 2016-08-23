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
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.ITaoService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetTaoSingleVerseCommandTest {
	
	@Mock
	ITaoService taoService;
	
	@InjectMocks
	private GetTaoSingleVerseCommand cmd = new GetTaoSingleVerseCommand();
	
	@Mock
	VerseDTO dto;
	
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

		Mockito.when(taoService.getSingleVerse(Mockito.anyInt(), Mockito.anyInt())).thenReturn(dto);
		
		List<VerseDTO> response = cmd.setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(taoService.getSingleVerse(Mockito.anyInt(), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setChapter(-1).setVerse(-1).execute();
		
	}
}
