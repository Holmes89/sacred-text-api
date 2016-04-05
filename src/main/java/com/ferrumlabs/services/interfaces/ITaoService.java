package com.ferrumlabs.services.interfaces;

import java.util.List;

import com.ferrumlabs.dto.TaoVerseDTO;
import com.ferrumlabs.exceptions.ServiceException;

public interface ITaoService {

	List<TaoVerseDTO> getVersesInRange(Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<TaoVerseDTO> getVersesInChapter(int chapter) throws ServiceException;

}
