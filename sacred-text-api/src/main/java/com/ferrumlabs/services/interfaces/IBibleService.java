package com.ferrumlabs.services.interfaces;

import java.util.List;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;

public interface IBibleService {

	@Deprecated
	List<BibleVerseDTO> getVersesInRange(BibleVersionEnum version, String book, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<BibleVerseDTO> getVersesInChapter(BibleVersionEnum version, String book, int chapter) throws ServiceException;

	List<BibleVerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<BibleVerseDTO> getVersesFromString(BibleVersionEnum version, String verses) throws ServiceException;

	List<BibleVerseDTO> getVerses(BibleVersionEnum version, String book, Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;

}
