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
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.impl.QuranService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetQuranSingleVerseCommandTest {

	@Mock
	QuranService quranService;
	
	@InjectMocks
	private GetQuranSingleVerseCommand cmd = new GetQuranSingleVerseCommand();
	
	@Mock
	List<VerseDTO> dtos;
	
	@Mock
	VerseDTO dto;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}

	@Test
	public void testCommand() throws ServiceException{

		Mockito.when(quranService.getSingleVerse(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dto);
		
		List<VerseDTO> response = cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(quranService.getSingleVerse(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		
	}
}
