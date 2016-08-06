package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;

public interface IQuranService {
	
	List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, String chapterName) throws ServiceException;
	
	List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException;

	
	List<QuranVerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<QuranVerseDTO> getVersesFromString(QuranVersionEnum version, String verses) throws ServiceException;

	List<QuranVerseDTO> getVerses(QuranVersionEnum version, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	
	List<QuranVerseDTO> getVerses(QuranVersionEnum version, String chapterName,  Integer verse,
			String throughChapterName, Integer throughVerse) throws ServiceException;

	QuranVerseDTO getSingleVerse(QuranVersionEnum version,  int chapter, int verse) throws ServiceException;
	
	QuranVerseDTO getSingleVerse(QuranVersionEnum version,  String chapterName, int verse) throws ServiceException;

	QuranVerseDTO getSingleVerse(int chapter, int verse) throws ServiceException;
	
	QuranVerseDTO getSingleVerse(String chapterName, int verse) throws ServiceException;

	List<QuranVerseDTO> getVersesInChapter(int chapter) throws ServiceException;
	
	List<QuranVerseDTO> getVersesInChapter(String chapterName) throws ServiceException;

}
