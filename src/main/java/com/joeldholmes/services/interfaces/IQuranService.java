package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;

public interface IQuranService {
	
	List<SearchResource> getVersesInChapter(QuranVersionEnum version, String chapterName) throws ServiceException;
	
	List<SearchResource> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException;

	
	List<SearchResource> getVersesFromString(String verses) throws ServiceException;

	List<SearchResource> getVersesFromString(QuranVersionEnum version, String verses) throws ServiceException;

	List<SearchResource> getVerses(QuranVersionEnum version, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	
	List<SearchResource> getVerses(QuranVersionEnum version, String chapterName,  Integer verse,
			String throughChapterName, Integer throughVerse) throws ServiceException;

	SearchResource getSingleVerse(QuranVersionEnum version,  int chapter, int verse) throws ServiceException;
	
	SearchResource getSingleVerse(QuranVersionEnum version,  String chapterName, int verse) throws ServiceException;

	SearchResource getSingleVerse(int chapter, int verse) throws ServiceException;
	
	SearchResource getSingleVerse(String chapterName, int verse) throws ServiceException;

	List<SearchResource> getVersesInChapter(int chapter) throws ServiceException;
	
	List<SearchResource> getVersesInChapter(String chapterName) throws ServiceException;

}
