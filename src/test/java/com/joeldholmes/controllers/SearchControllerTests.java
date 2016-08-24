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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
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
import com.joeldholmes.commands.GetBibleChapterCommand;
import com.joeldholmes.commands.GetBibleSingleVerseCommand;
import com.joeldholmes.commands.GetBibleVerseRangeCommand;
import com.joeldholmes.commands.GetBibleVersesByStringCommand;
import com.joeldholmes.commands.GetQuranVersesByStringCommand;
import com.joeldholmes.commands.GetTaoVersesByStringCommand;
import com.joeldholmes.commands.SearchAllTextAndVerseCommand;
import com.joeldholmes.commands.SearchBibleTextAndVerseCommand;
import com.joeldholmes.commands.SearchQuranTextAndVerseCommand;
import com.joeldholmes.commands.SearchTaoTextAndVerseCommand;
import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
@WebAppConfiguration
public class SearchControllerTests {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private final String MOCKED_RESPONSE = "blah";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Mock
	Provider<SearchAllTextAndVerseCommand> searchAllTextAndVerseProvider;
	
	@Mock
	Provider<SearchBibleTextAndVerseCommand> searchBibleTextAndVerseProvider;
	
	@Mock
	Provider<SearchQuranTextAndVerseCommand> searchQuranTextAndVerseProvider;
	
	@Mock
	Provider<SearchTaoTextAndVerseCommand> searchTaoTextAndVerseProvider;
	
	@Mock
	SearchAllTextAndVerseCommand searchAllTextAndVerseCommand;
	
	@Mock
	SearchBibleTextAndVerseCommand searchBibleTextAndVerseCommand;
	
	@Mock
	SearchQuranTextAndVerseCommand searchQuranTextAndVerseCommand;
	
	@Mock
	SearchTaoTextAndVerseCommand searchTaoTextAndVerseCommand;
	
	@InjectMocks
	@Autowired
	SearchController underTest = new SearchController();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();
		when(searchAllTextAndVerseProvider.get()).thenReturn(searchAllTextAndVerseCommand);
		when(searchBibleTextAndVerseProvider.get()).thenReturn(searchBibleTextAndVerseCommand);
		when(searchQuranTextAndVerseProvider.get()).thenReturn(searchQuranTextAndVerseCommand);
		when(searchTaoTextAndVerseProvider.get()).thenReturn(searchTaoTextAndVerseCommand);
	}
	
	@Test
	public void testGetAll() throws Exception{
		when(searchAllTextAndVerseCommand.setTerm(Mockito.anyString())).thenReturn(searchAllTextAndVerseCommand);
		
		when(searchAllTextAndVerseCommand.execute()).thenReturn(makeList());
		
		MvcResult mvcResult = this.mockMvc.perform(get("/search/?term=blah")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<VerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<VerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		VerseDTO verse = response.iterator().next();
		Assert.assertEquals("Blah", verse.getBook());
	}
	
	@Test
	public void testGetBible() throws Exception{
		when(searchBibleTextAndVerseCommand.setTerm(Mockito.anyString())).thenReturn(searchBibleTextAndVerseCommand);
		
		when(searchBibleTextAndVerseCommand.execute()).thenReturn(makeList());
		
		MvcResult mvcResult = this.mockMvc.perform(get("/search/bible/?term=blah")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<VerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<VerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		VerseDTO verse = response.iterator().next();
		Assert.assertEquals("Blah", verse.getBook());
	}
	
	@Test
	public void testGetQuran() throws Exception{
		when(searchQuranTextAndVerseCommand.setTerm(Mockito.anyString())).thenReturn(searchQuranTextAndVerseCommand);
		
		when(searchQuranTextAndVerseCommand.execute()).thenReturn(makeList());
		
		MvcResult mvcResult = this.mockMvc.perform(get("/search/quran/?term=blah")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<VerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<VerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		VerseDTO verse = response.iterator().next();
		Assert.assertEquals("Blah", verse.getBook());
	}
	
	@Test
	public void testGetTao() throws Exception{
		when(searchTaoTextAndVerseCommand.setTerm(Mockito.anyString())).thenReturn(searchTaoTextAndVerseCommand);
		
		when(searchTaoTextAndVerseCommand.execute()).thenReturn(makeList());
		
		MvcResult mvcResult = this.mockMvc.perform(get("/search/tao-te-ching/?term=blah")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		List<VerseDTO> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<VerseDTO>>() { });
		
		Assert.assertEquals(1, response.size());
		VerseDTO verse = response.iterator().next();
		Assert.assertEquals("Blah", verse.getBook());
	}
	
	private List<VerseDTO> makeList(){
		VerseDTO dto = new VerseDTO();
		dto.setBook("blah");
		dto.setChapter(1);
		dto.setVerse(1);
		dto.setContent("asdfsad");
		
		List<VerseDTO> dtos = new ArrayList<VerseDTO>();
		dtos.add(dto);
		return dtos;
	}
	
}
