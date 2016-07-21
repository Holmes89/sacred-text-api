package com.joeldholmes.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import org.junit.Assert;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeldholmes.SacredTextApiApplication;
import com.joeldholmes.commands.GetQuranChapterCommand;
import com.joeldholmes.commands.GetQuranSingleVerseCommand;
import com.joeldholmes.commands.GetQuranVerseRangeCommand;
import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
@WebAppConfiguration
public class QuranControllerTests {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private final String MOCKED_RESPONSE = "blah";
	
	private ObjectMapper mapper = new ObjectMapper();
		
	@Mock
	Provider<GetQuranSingleVerseCommand> getSingleVerseProvider;
	
	@Mock
	Provider<GetQuranVerseRangeCommand> getRangeVerseProvider;
	
	@Mock
	Provider<GetQuranChapterCommand> getChapterProvider;
	
	@Mock
	GetQuranSingleVerseCommand getSingleVerseCommand;
	
	@Mock
	GetQuranVerseRangeCommand getRangeVerseCommand;
	
	@Mock
	GetQuranChapterCommand getChapterCommand;
	
	@InjectMocks
	@Autowired
	QuranController underTest = new QuranController();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();
		when(getSingleVerseProvider.get()).thenReturn(getSingleVerseCommand);
		when(getRangeVerseProvider.get()).thenReturn(getRangeVerseCommand);
		when(getChapterProvider.get()).thenReturn(getChapterCommand);
	}
	
	@Test
	public void testGetSingleVerse() throws Exception{
		QuranVerseDTO dto = new QuranVerseDTO();
		dto.setChapterName("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		dtos.add(dto);
		
		when(getSingleVerseCommand.setVersion(Mockito.any(QuranVersionEnum.class))).thenReturn(getSingleVerseCommand);
		when(getSingleVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getSingleVerseCommand);
		when(getSingleVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getSingleVerseCommand);
		
		when(getSingleVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/quran/?chapter=1&verse=1")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<QuranVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuranVerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		QuranVerseDTO verse = response.iterator().next();
		Assert.assertEquals("blah", verse.getChapterName());
	}
	
	@Test
	public void testGetRangeVerses() throws Exception{
		QuranVerseDTO dto = new QuranVerseDTO();
		dto.setChapterName("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getRangeVerseCommand.setVersion(Mockito.any(QuranVersionEnum.class))).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		
		when(getRangeVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/quran/?chapter=1&verse=1&throughChapter=3&throughVerse=2")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<QuranVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuranVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
	@Test
	public void testGetRangeVerses_verseOnly() throws Exception{
		QuranVerseDTO dto = new QuranVerseDTO();
		dto.setChapterName("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getRangeVerseCommand.setVersion(Mockito.any(QuranVersionEnum.class))).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		
		when(getRangeVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/quran/?chapter=1&verse=1&throughVerse=2")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<QuranVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuranVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
	
	@Test
	public void testGetChapterVerse() throws Exception{
		QuranVerseDTO dto = new QuranVerseDTO();
		dto.setChapterName("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getChapterCommand.setVersion(Mockito.any(QuranVersionEnum.class))).thenReturn(getChapterCommand);
		when(getChapterCommand.setChapter(Mockito.anyInt())).thenReturn(getChapterCommand);
		
		when(getChapterCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/quran/?chapter=1")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<QuranVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuranVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
}