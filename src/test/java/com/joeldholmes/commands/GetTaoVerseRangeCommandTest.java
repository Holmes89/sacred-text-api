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

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.impl.TaoService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
@Ignore
public class GetTaoVerseRangeCommandTest {
	
	@Mock
	TaoService taoService;
	
	@InjectMocks
	private GetTaoVerseRangeCommand cmd = new GetTaoVerseRangeCommand();
	
	@Mock
	List<TaoVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws ServiceException{

		Mockito.when(taoService.getVersesInRange(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dtos);
		
		List<TaoVerseDTO> response = cmd.setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(taoService.getVersesInRange(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setChapter(-1).setVerse(-1).execute();
		
	}
	
	
}
