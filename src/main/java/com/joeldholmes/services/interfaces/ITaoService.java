package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.ServiceException;

public interface ITaoService {

	List<TaoVerseDTO> getVersesInRange(Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

	List<TaoVerseDTO> getVersesInChapter(int chapter) throws ServiceException;

}
