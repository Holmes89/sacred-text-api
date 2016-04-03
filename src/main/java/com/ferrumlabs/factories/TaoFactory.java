package com.ferrumlabs.factories;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.utils.ErrorCodes;

@Component
@Scope("singleton")
public class TaoFactory {

	private final String ENG_FILE = "/json/sacredTexts/tao-te-ching/eng/tao-te-ching.json";

	Map<Integer, Map<Integer, String>> engTao;
	
	protected TaoFactory(){
		//No code, singleton, only one instance.
	}
	
	@PostConstruct
	private void init() throws FactoryException{
		Resource resource = new ClassPathResource(ENG_FILE);
		try{
			InputStream resourceInputStream = resource.getInputStream();
			TypeReference<LinkedHashMap<Integer, LinkedHashMap<Integer, String>>> typeRef = new TypeReference<LinkedHashMap<Integer, LinkedHashMap<Integer, String>>>() {};
			ObjectMapper mapper = new ObjectMapper();
			engTao = mapper.readValue(resourceInputStream, typeRef);
		}catch(IOException e){
			throw new FactoryException("Mapping failure", e);
		}
	}
	
	public String getVerse(int chapter, int verse) throws FactoryException{
		if(!engTao.keySet().contains(chapter)){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid Chapter");
		}
		String content = engTao.get(chapter).get(verse);
		if(content==null){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Verse not in chapter");
		}
		return content;
	}
	
	public Map<Integer, String> getChapter(int chapter) throws FactoryException{
		if(!engTao.keySet().contains(chapter)){
			throw new FactoryException(ErrorCodes.INVALID_INPUT, "Invalid Chapter");
		}
		return engTao.get(chapter);
	}
}
