package com.ferrumlabs.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ferrumlabs.SacredTextApiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SacredTextApiApplication.class)
@WebAppConfiguration
public class SacredTextAPIDocumentation {

	//This will change to different directory if using Gradle
	@Rule
	public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");
	
	private RestDocumentationResultHandler document; 
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.document = document("{method-name}",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));
	
		
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
	            .apply(documentationConfiguration(this.restDocumentation)) 
	            .build();
	
	}
	@Test
	public void bibleSingleVerseDoc() throws Exception{
		this.mockMvc.perform(get("/bible/?versionAbbr={versionAbbr}&book={book}&chapter={chapter}&verse={verse}", "NLT", "John", 1, 1).accept(MediaType.APPLICATION_JSON)) 
	    .andExpect(status().isOk()) 
	    .andDo(document("bible", pathParameters(
	    		parameterWithName("versionAbbr").description("Abbreviation of translation, default NKJV"),
	    		parameterWithName("book").description("Book of Bible").attributes(
                        key("constraints").value("Must not be null. Must not be empty")),
	    		parameterWithName("chapter").description("Chapter of book").attributes(
                        key("constraints").value("Must not be null. Must not be empty")),
	    		parameterWithName("verse").description("Verse in book, optional, if empty entire chapter is return")
	    		)));
	}
}
