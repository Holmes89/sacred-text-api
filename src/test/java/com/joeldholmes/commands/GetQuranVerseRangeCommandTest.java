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

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.impl.QuranService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
@Ignore
public class GetQuranVerseRangeCommandTest {
	
	@Mock
	QuranService quranService;
	
	@InjectMocks
	private GetQuranVerseRangeCommand cmd = new GetQuranVerseRangeCommand();
	
	@Mock
	List<QuranVerseDTO> dtos;
	
	private final String MOCKED_RESPONSE = "blah";
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);	
	}
	

	@Test
	public void testCommand() throws ServiceException{

		Mockito.when(quranService.getVersesInRange(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dtos);
		
		List<QuranVerseDTO> response = cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(quranService.getVersesInRange(Mockito.any(QuranVersionEnum.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).setVerse(-1).execute();
		
	}
}
