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

import com.ferrumlabs.dto.QuranVerseDTO;
import com.ferrumlabs.enums.QuranVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.factories.QuranFactory;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetQuranSingleVerseCommandTest {

	@Mock
	QuranFactory quranFactory;
	
	@InjectMocks
	private GetQuranSingleVerseCommand cmd = new GetQuranSingleVerseCommand();
	
	@Mock
	List<QuranVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}

	@Test
	public void testCommand() throws FactoryException{

		Mockito.when(quranFactory.getVerse(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MOCKED_RESPONSE);
		
		List<QuranVerseDTO> response = cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws FactoryException{
		
		Mockito.when(quranFactory.getVerse(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(FactoryException.class);
		
		cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		
	}
}
