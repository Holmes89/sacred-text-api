package com.ferrumlabs.services.interfaces;

import java.util.List;

import com.ferrumlabs.dto.QuranVerseDTO;
import com.ferrumlabs.enums.QuranVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;

public interface IQuranService {
	
	List<QuranVerseDTO> getVersesInRange(QuranVersionEnum version, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException;

}
