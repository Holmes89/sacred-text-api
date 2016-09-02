package com.joeldholmes.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.BibleVerseResource;
import com.joeldholmes.services.interfaces.IBibleService;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.params.FilterParams;

@Repository
public class BibleVerseRepository {

	@Autowired
	IBibleService bibleService;
	
	public BibleVerseResource findOne(String id) throws ServiceException{
		return bibleService.getVerseById(id);
	}
	
	public List<BibleVerseResource> findAll(List<String> ids, QueryParams params) throws ServiceException{
		return bibleService.getVersesByIds(ids);
	}
	
	public List<BibleVerseResource> findAll(QueryParams params) throws ServiceException{
		Map<String, FilterParams> filterParams = params.getFilters().getParams();
		if(!filterParams.containsKey("displayVerse")){
			return null;
		}
		
		BibleVersionEnum version;
		if(!filterParams.containsKey("version")){
			version = BibleVersionEnum.NIV;
		}
		else{
			version = BibleVersionEnum.findByAbbreviation(filterParams.get("version").getParams().get("").iterator().next());
		}
		
		Set<String> verses = filterParams.get("displayVerse").getParams().get("");
		if(verses==null || verses.isEmpty()){
			return null;
		}
		
		List<BibleVerseResource> resources = new ArrayList<BibleVerseResource>();
		for(String verse: verses){
			resources.addAll(bibleService.getVersesFromString(version, verse));
		}
		return resources;
	}
}
