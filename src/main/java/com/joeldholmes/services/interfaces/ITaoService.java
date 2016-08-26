package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;

public interface ITaoService {

	List<SearchResource> getVersesInChapter(int chapter) throws ServiceException;
	List<SearchResource> getVersesFromString(String verses) throws ServiceException;

	List<SearchResource> getVerses(Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	

	SearchResource getSingleVerse(int chapter, int verse) throws ServiceException;


}
