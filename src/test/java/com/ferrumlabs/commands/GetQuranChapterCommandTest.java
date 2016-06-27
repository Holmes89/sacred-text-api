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
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.services.impl.QuranService;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RunWith(PowerMockRunner.class)
public class GetQuranChapterCommandTest {

	@Mock
	QuranService quranService;
	
	@InjectMocks
	private GetQuranChapterCommand cmd = new GetQuranChapterCommand();
	
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

		Mockito.when(quranService.getVersesInChapter(Mockito.any(QuranVersionEnum.class),  Mockito.anyInt())).thenReturn(dtos);
		
		List<QuranVerseDTO> response = cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).execute();
		Assert.assertTrue(!response.isEmpty());
	}
	
	@Test(expected=HystrixBadRequestException.class)
	public void testException() throws ServiceException{
		
		Mockito.when(quranService.getVersesInChapter(Mockito.any(QuranVersionEnum.class), Mockito.anyInt())).thenThrow(ServiceException.class);
		
		cmd.setVersion(QuranVersionEnum.PICKTHALL).setChapter(-1).execute();
		
	}

}
