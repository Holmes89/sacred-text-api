package com.joeldholmes.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;
import com.joeldholmes.services.interfaces.ISearchService;
import com.joeldholmes.utils.QueryParamUtils;

import io.katharsis.queryParams.QueryParams;

@Repository
public class SearchRepository {

	@Autowired
	ISearchService searchService;
	
	public Iterable<SearchResource> findAll(QueryParams params) throws ServiceException{
		Sort sort = new Sort(Direction.DESC, Arrays.asList("score"));
		Pageable page = QueryParamUtils.getPageable(params, sort);
		
		Map<String, Set<String>> filters = QueryParamUtils.getFilters(SearchResource.class, params);
		if(!filters.containsKey("searchTerm")){
			return null;
		}
		Set<String> searchTerms = filters.get("searchTerm");
		if(searchTerms==null || searchTerms.isEmpty()){
			return null;
		}
		String searchTerm ="";
		for(String terms: searchTerms){
			searchTerm +=terms+" ";
		}
		searchTerm = searchTerm.trim();
		return searchService.searchAllText(searchTerm, page);
	}
}
