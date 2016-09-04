package com.joeldholmes.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.QuranVerseResource;
import com.joeldholmes.resources.TaoVerseResource;
import com.joeldholmes.services.interfaces.ITaoService;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.params.FilterParams;

@Repository
public class TaoVerseRepository {

	@Autowired
	ITaoService taoService;
	
	public TaoVerseResource findOne(String id) throws ServiceException{
		return taoService.getVerseById(id);
	}
	
	public List<TaoVerseResource> findAll(List<String> ids, QueryParams params) {
		return taoService.getVersesByIds(ids);
	}
	
	public List<TaoVerseResource> findAll(QueryParams params) throws ServiceException{
		Map<String, FilterParams> filterParams = params.getFilters().getParams();
		if(!filterParams.containsKey("displayVerse")){
			return null;
		}
		Set<String> verses = filterParams.get("displayVerse").getParams().get("");
		if(verses==null || verses.isEmpty()){
			return null;
		}
		
		List<TaoVerseResource> resources = new ArrayList<TaoVerseResource>();
		for(String verse: verses){
			resources.addAll(taoService.getVersesFromString(verse));
		}
		return resources;
	}
}
