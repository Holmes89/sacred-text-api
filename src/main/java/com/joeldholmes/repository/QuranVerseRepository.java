package com.joeldholmes.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.QuranVerseResource;
import com.joeldholmes.services.interfaces.IQuranService;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.params.FilterParams;

@Repository
public class QuranVerseRepository {

	@Autowired
	IQuranService quranService;
	
	public QuranVerseResource findOne(String id) throws ServiceException{
		return quranService.getVerseById(id);
	}
	
	public List<QuranVerseResource> findAll(QueryParams params) throws ServiceException{
		Map<String, FilterParams> filterParams = params.getFilters().getParams();
		if(!filterParams.containsKey("displayVerse")){
			return null;
		}
		
		QuranVersionEnum version;
		if(!filterParams.containsKey("version")){
			version = QuranVersionEnum.SHAKIR;
		}
		else{
			version = QuranVersionEnum.findByName(filterParams.get("version").getParams().get("").iterator().next());
		}
		
		Set<String> verses = filterParams.get("displayVerse").getParams().get("");
		if(verses==null || verses.isEmpty()){
			return null;
		}
		
		List<QuranVerseResource> resources = new ArrayList<QuranVerseResource>();
		for(String verse: verses){
			resources.addAll(quranService.getVersesFromString(version, verse));
		}
		return resources;
	}

	public List<QuranVerseResource> findAll(List<String> ids, QueryParams params) {
		return quranService.getVersesByIds(ids);
	}
}
