package com.joeldholmes.factories;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.utils.ErrorCodes;

@Component
@Scope("singleton")
public class QuranFactory {
	private final String ENG_BASE_PATH = "/json/sacredTexts/quran/eng/";	
	
	private Map<QuranVersionEnum, Map<Integer, Map<Integer, String>>> engQuranMap = new LinkedHashMap<QuranVersionEnum, Map<Integer, Map<Integer, String>>>() ;
	private Map<QuranVersionEnum, Map<Integer, String>> engChapterName = new LinkedHashMap<QuranVersionEnum, Map<Integer, String>>();
	
	protected QuranFactory(){
		//No code, singleton, only one instance.
	}
	
	@PostConstruct
	private void init() throws FactoryException{
		for(QuranVersionEnum version: QuranVersionEnum.values()){
			String name = version.getName();
			Resource resource = new ClassPathResource(ENG_BASE_PATH+name+".json");
			try{
				InputStream resourceInputStream = resource.getInputStream();
				TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, String>>> typeRef = new TypeReference<LinkedHashMap<Integer, LinkedHashMap<String, String>>>() {};
				ObjectMapper mapper = new ObjectMapper();
				Map<Integer, Map<String, String>> jsonMap = mapper.readValue(resourceInputStream, typeRef);
				Map<Integer, Map<Integer, String>> quranMap = new LinkedHashMap<Integer, Map<Integer, String>>();
				Map<Integer, String> chapterNameMap = new LinkedHashMap<Integer, String>();
				for(int chapter: jsonMap.keySet()){
					Map<Integer, String> verseMap = new LinkedHashMap<Integer, String>();
					Map<String, String> jsonLoadMap = jsonMap.get(chapter);
					chapterNameMap.put(chapter, jsonLoadMap.get("name"));
					jsonLoadMap.remove("name");
					for(String verseString: jsonLoadMap.keySet()){
						Integer verse = Integer.valueOf(verseString);
						verseMap.put(verse, jsonLoadMap.get(verseString));
					}
					quranMap.put(chapter, verseMap);
				}
				engChapterName.put(version, chapterNameMap);
				engQuranMap.put(version, quranMap);
			}catch(IOException e){
				throw new FactoryException("Mapping failure", e);
			}
		}
	}
	
	public String getVerse(QuranVersionEnum version, int chapter, int verse) throws FactoryException{
		if(version == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		Map<Integer, Map<Integer, String>> chapters = engQuranMap.get(version); 
		if(!chapters.keySet().contains(chapter)){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid Chapter");
		}
		String content = chapters.get(chapter).get(verse);
		if(content==null){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Verse not in chapter");
		}
		return content;
	}
	
	public Map<Integer, String> getVerses(QuranVersionEnum version, int chapter) throws FactoryException{
		if(version == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		Map<Integer, Map<Integer, String>> chapters = engQuranMap.get(version); 
		if(!chapters.keySet().contains(chapter)){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid Chapter");
		}
		return chapters.get(chapter);
	}
	
	public String getChapterName(QuranVersionEnum version, int chapter) throws FactoryException{
		if(version == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		Map<Integer, String> chapterNames = engChapterName.get(version); 
		if(!chapterNames.keySet().contains(chapter)){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid Chapter");
		}
		return chapterNames.get(chapter);
	}
	
	public List<Integer> getChapters(QuranVersionEnum version) throws FactoryException{
		if(version == null){
			throw new FactoryException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		return new ArrayList<Integer>(engQuranMap.get(version).keySet());
	}	
}
