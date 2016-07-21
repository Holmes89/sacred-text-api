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

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.factories.TaoFactory;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetTaoSingleVerseCommandTest {
	
	@Mock
	TaoFactory taoFactory;
	
	@InjectMocks
	private GetTaoSingleVerseCommand cmd = new GetTaoSingleVerseCommand();
	
	@Mock
	List<TaoVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws FactoryException{

		Mockito.when(taoFactory.getVerse(Mockito.anyInt(), Mockito.anyInt())).thenReturn(MOCKED_RESPONSE);
		
		List<TaoVerseDTO> response = cmd.setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws FactoryException{
		
		Mockito.when(taoFactory.getVerse(Mockito.anyInt(), Mockito.anyInt())).thenThrow(FactoryException.class);
		
		cmd.setChapter(-1).setVerse(-1).execute();
		
	}
}
