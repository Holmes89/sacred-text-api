package com.joeldholmes.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;
import com.joeldholmes.services.interfaces.ISearchService;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.params.FilterParams;

@Repository
public class SearchRepository {

	@Autowired
	ISearchService searchService;
	
	public List<SearchResource> findAll(QueryParams params) throws ServiceException{
		Map<String, FilterParams> filterParams = params.getFilters().getParams();
		if(!filterParams.containsKey("search")){
			return null;
		}
		Map<String, Set<String>> searchParams =filterParams.get("search").getParams();
		Set<String> searchTerms = searchParams.get("searchTerm");
		if(searchTerms==null || searchTerms.isEmpty()){
			return null;
		}
		List<SearchResource> resources = new ArrayList<SearchResource>();
		for(String searchTerm: searchTerms){
			resources.addAll(searchService.searchAllText(searchTerm));
		}
		return resources;
	}
}
