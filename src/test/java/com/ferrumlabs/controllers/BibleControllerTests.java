package com.ferrumlabs.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.ferrumlabs.SacredTextApiApplication;
import com.ferrumlabs.commands.GetBibleVerseCommand;
import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
@WebAppConfiguration
public class BibleControllerTests {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private final String MOCKED_RESPONSE = "blah";
		
	@Mock
	Provider<GetBibleVerseCommand> getVerseProvider;
	
	@Mock
	GetBibleVerseCommand getBibleVerseCommand;
	
	@InjectMocks
	@Autowired
	BibleController underTest = new BibleController();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();
		when(getVerseProvider.get()).thenReturn(getBibleVerseCommand);
	}
	
	@Test
	public void testGetVerse() throws Exception{
		BibleVerseDTO dto = new BibleVerseDTO();
		dto.setBook("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<BibleVerseDTO> dtos = new ArrayList<BibleVerseDTO>();
		dtos.add(dto);
		
		when(getBibleVerseCommand.setVersion(Mockito.any(BibleVersionEnum.class))).thenReturn(getBibleVerseCommand);
		when(getBibleVerseCommand.setBook(Mockito.anyString())).thenReturn(getBibleVerseCommand);
		when(getBibleVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getBibleVerseCommand);
		when(getBibleVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getBibleVerseCommand);
		
		when(getBibleVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/bible/NIV?book=John&chapter=1&verse=1")
				.accept(BibleController.V1_MEDIA_TYPE)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(BibleController.V1_MEDIA_TYPE))
				.andReturn();

	}
}
