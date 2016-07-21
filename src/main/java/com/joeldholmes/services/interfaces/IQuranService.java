package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;

public interface IQuranService {
	
	List<QuranVerseDTO> getVersesInRange(QuranVersionEnum version, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException;

}
