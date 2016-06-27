package com.ferrumlabs.controllers;

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
import com.ferrumlabs.SacredTextApiApplication;
import com.ferrumlabs.commands.GetTaoChapterCommand;
import com.ferrumlabs.commands.GetTaoSingleVerseCommand;
import com.ferrumlabs.commands.GetTaoVerseRangeCommand;
import com.ferrumlabs.dto.TaoVerseDTO;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
@WebAppConfiguration
public class TaoControllerTests {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private final String MOCKED_RESPONSE = "blah";
	
	private ObjectMapper mapper = new ObjectMapper();
		
	@Mock
	Provider<GetTaoSingleVerseCommand> getSingleVerseProvider;
	
	@Mock
	Provider<GetTaoVerseRangeCommand> getRangeVerseProvider;
	
	@Mock
	Provider<GetTaoChapterCommand> getChapterProvider;
	
	@Mock
	GetTaoSingleVerseCommand getSingleVerseCommand;
	
	@Mock
	GetTaoVerseRangeCommand getRangeVerseCommand;
	
	@Mock
	GetTaoChapterCommand getChapterCommand;
	
	@InjectMocks
	@Autowired
	TaoController underTest = new TaoController();
	
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
		TaoVerseDTO dto = new TaoVerseDTO();
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		dtos.add(dto);
		
		when(getSingleVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getSingleVerseCommand);
		when(getSingleVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getSingleVerseCommand);
		
		when(getSingleVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/tao/?chapter=1&verse=1")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<TaoVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TaoVerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		TaoVerseDTO verse = response.iterator().next();
	}
	
	@Test
	public void testGetRangeVerses() throws Exception{
		TaoVerseDTO dto = new TaoVerseDTO();
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getRangeVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		
		when(getRangeVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/tao/?&chapter=1&verse=1&throughChapter=3&throughVerse=2")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<TaoVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TaoVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
	@Test
	public void testGetRangeVerses_verseOnly() throws Exception{
		TaoVerseDTO dto = new TaoVerseDTO();
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getRangeVerseCommand.setChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughVerse(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		when(getRangeVerseCommand.setThroughChapter(Mockito.anyInt())).thenReturn(getRangeVerseCommand);
		
		when(getRangeVerseCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/tao/?chapter=1&verse=1&throughVerse=2")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<TaoVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TaoVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
	
	@Test
	public void testGetChapterVerse() throws Exception{
		TaoVerseDTO dto = new TaoVerseDTO();
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		dtos.add(dto);
		dtos.add(dto);
		
		when(getChapterCommand.setChapter(Mockito.anyInt())).thenReturn(getChapterCommand);
		
		when(getChapterCommand.execute()).thenReturn(dtos);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/tao/?chapter=1")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<TaoVerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TaoVerseDTO>>() { });
		
		Assert.assertEquals(2, response.size());
	}
}
