package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;

public interface IBibleService {

	@Deprecated
	List<SearchResource> getVersesInRange(BibleVersionEnum version, String book, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<SearchResource> getVersesInChapter(BibleVersionEnum version, String book, int chapter) throws ServiceException;

	List<SearchResource> getVersesFromString(String verses) throws ServiceException;

	List<SearchResource> getVersesFromString(BibleVersionEnum version, String verses) throws ServiceException;

	List<SearchResource> getVerses(BibleVersionEnum version, String book, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;

	SearchResource getSingleVerse(BibleVersionEnum version, String book, int chapter, int verse) throws ServiceException;

}
