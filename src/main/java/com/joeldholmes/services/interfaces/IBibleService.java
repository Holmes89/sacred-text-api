package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;

public interface IBibleService {

	@Deprecated
	List<VerseDTO> getVersesInRange(BibleVersionEnum version, String book, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<VerseDTO> getVersesInChapter(BibleVersionEnum version, String book, int chapter) throws ServiceException;

	List<VerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<VerseDTO> getVersesFromString(BibleVersionEnum version, String verses) throws ServiceException;

	List<VerseDTO> getVerses(BibleVersionEnum version, String book, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;

	VerseDTO getSingleVerse(BibleVersionEnum version, String book, int chapter, int verse) throws ServiceException;

}
