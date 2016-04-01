package com.ferrumlabs.factories;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.utils.ErrorCodes;

@Component
@Scope("singleton")
public class BibleFactory {
	
	private final String ENG_BASE_PATH = "/json/sacredTexts/bible/eng/";	
		
	private Map<BibleVersionEnum, Map<String, Map<Integer, Map<Integer, String>>>> engBibleMap = new LinkedHashMap<BibleVersionEnum, Map<String, Map<Integer, Map<Integer, String>>>>() ;
	
	protected BibleFactory(){
		//No code, singleton, only one instance.
	}
	
	@PostConstruct
	private void init() throws FactoryException{
		for(BibleVersionEnum version: BibleVersionEnum.values()){
			String abbr = version.getAbbr();
			Resource resource = new ClassPathResource(ENG_BASE_PATH+abbr+".json");
			try{
				InputStream resourceInputStream = resource.getInputStream();
				TypeReference<LinkedHashMap<String, LinkedHashMap<Integer, LinkedHashMap<Integer, String>>>> typeRef = new TypeReference<LinkedHashMap<String, LinkedHashMap<Integer, LinkedHashMap<Integer, String>>>>() {};
				ObjectMapper mapper = new ObjectMapper();
				engBibleMap.put(version, mapper.readValue(resourceInputStream, typeRef));
			}catch(IOException e){
				throw new FactoryException("Mapping failure", e);
			}
		}
	}
	
	public String getVerse(BibleVersionEnum version, String book, int chapter, int verse) throws FactoryException{
		if(version == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(book == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Book cannot be null");
		}
		book = book.toLowerCase();
		Map<Integer, Map<Integer, String>> chapters = engBibleMap.get(version).get(book);
		if(chapters == null){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid book name");
		}
		Map<Integer, String> verses = chapters.get(chapter);
		if(verses == null){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Chapter not in book");
		}
		String verseContent = verses.get(verse);
		if(verseContent == null){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Verse not in chapter");
		}
		return verseContent;
	}
	
}
