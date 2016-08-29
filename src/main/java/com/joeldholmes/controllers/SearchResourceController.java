package com.joeldholmes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.repository.SearchRepository;
import com.joeldholmes.resources.SearchResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiFindAll;
import io.katharsis.repository.annotations.JsonApiResourceRepository;

@Component
@JsonApiResourceRepository(SearchResource.class)
public class SearchResourceController {

	
	@Autowired
	SearchRepository searchRepository;
	
	@HystrixCommand(commandKey="SearchFindAll", groupKey="Search", threadPoolKey="Search")
	@JsonApiFindAll
	public Iterable<SearchResource> findAll(QueryParams params) throws ServiceException{
		return searchRepository.findAll(params);
	}
}
