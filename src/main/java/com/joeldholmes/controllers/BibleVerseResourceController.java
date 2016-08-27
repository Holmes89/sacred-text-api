package com.joeldholmes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.BibleVerseResource;
import com.joeldholmes.services.interfaces.IBibleService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiFindOne;
import io.katharsis.repository.annotations.JsonApiResourceRepository;

@Component
@JsonApiResourceRepository(BibleVerseResource.class)
public class BibleVerseResourceController {

	@Autowired
	IBibleService bibleService;

	@HystrixCommand(commandKey="BibleVerseFindOne", groupKey="BibleVerse", threadPoolKey="BibleVerse")
	@JsonApiFindOne
	public BibleVerseResource findOne(String id) throws ServiceException{
		return bibleService.getVerseById(id);
	}
	
	@HystrixCommand(commandKey="BibleVerseFindAll", groupKey="BibleVerse", threadPoolKey="BibleVerse")
	@JsonApiFindOne
	public Iterable<BibleVerseResource> findAll(QueryParams params) throws ServiceException{
		return bibleService.getVersesFromString("John 3:16");
	}
}
