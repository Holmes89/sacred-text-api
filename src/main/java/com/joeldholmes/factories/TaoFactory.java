package com.joeldholmes.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.utils.ErrorCodes;

@Component
@Scope("singleton")
public class TaoFactory {

	private final String ENG_FILE = "/json/sacredTexts/tao-te-ching/eng/tao-te-ching.json";

	Map<Integer, Map<Integer, String>> engTao;
	
	protected TaoFactory(){
		//No code, singleton, only one instance.
	}
	
//	@PostConstruct
//	private void init() throws FactoryException{
//		Resource resource = new ClassPathResource(ENG_FILE);
//		try{
//			InputStream resourceInputStream = resource.getInputStream();
//			TypeReference<LinkedHashMap<Integer, LinkedHashMap<Integer, String>>> typeRef = new TypeReference<LinkedHashMap<Integer, LinkedHashMap<Integer, String>>>() {};
//			ObjectMapper mapper = new ObjectMapper();
//			engTao = mapper.readValue(resourceInputStream, typeRef);
//		}catch(IOException e){
//			throw new FactoryException("Mapping failure", e);
//		}
//	}
	
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
	
	public List<Integer> getChapterList(){
		return new ArrayList<Integer>(engTao.keySet());
	}
}
