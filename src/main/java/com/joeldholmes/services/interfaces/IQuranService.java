package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;

public interface IQuranService {
	
	List<VerseDTO> getVersesInChapter(QuranVersionEnum version, String chapterName) throws ServiceException;
	
	List<VerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException;

	
	List<VerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<VerseDTO> getVersesFromString(QuranVersionEnum version, String verses) throws ServiceException;

	List<VerseDTO> getVerses(QuranVersionEnum version, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	
	List<VerseDTO> getVerses(QuranVersionEnum version, String chapterName,  Integer verse,
			String throughChapterName, Integer throughVerse) throws ServiceException;

	VerseDTO getSingleVerse(QuranVersionEnum version,  int chapter, int verse) throws ServiceException;
	
	VerseDTO getSingleVerse(QuranVersionEnum version,  String chapterName, int verse) throws ServiceException;

	VerseDTO getSingleVerse(int chapter, int verse) throws ServiceException;
	
	VerseDTO getSingleVerse(String chapterName, int verse) throws ServiceException;

	List<VerseDTO> getVersesInChapter(int chapter) throws ServiceException;
	
	List<VerseDTO> getVersesInChapter(String chapterName) throws ServiceException;

}
