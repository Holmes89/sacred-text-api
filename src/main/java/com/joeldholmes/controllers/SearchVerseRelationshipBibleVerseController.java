package com.joeldholmes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.repository.BibleVerseRepository;
import com.joeldholmes.resources.BibleVerseResource;
import com.joeldholmes.resources.SearchVerseResource;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiFindManyTargets;
import io.katharsis.repository.annotations.JsonApiFindOneTarget;
import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import io.katharsis.utils.PropertyUtils;

@Component
@JsonApiRelationshipRepository(source=SearchVerseResource.class, target=BibleVerseResource.class)
public class SearchVerseRelationshipBibleVerseController {
	
	@Autowired
	BibleVerseRepository bibleRepo;
	
	@JsonApiFindOneTarget
	public BibleVerseResource findOneTarget(String verseId, String fieldName, QueryParams params) throws ServiceException
	{
    	BibleVerseResource verse = bibleRepo.findOne(verseId);
		try {
            return (BibleVerseResource) PropertyUtils.getProperty(verse, fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
    @JsonApiFindManyTargets
	public Iterable<BibleVerseResource> findManyTargets(String verseId, String fieldName, QueryParams params) throws ServiceException
	{
    	BibleVerseResource verse = bibleRepo.findOne(verseId);
		try {
            return (Iterable<BibleVerseResource>) PropertyUtils.getProperty(verse, fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}
