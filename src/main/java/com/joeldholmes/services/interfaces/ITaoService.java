package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.ServiceException;

public interface ITaoService {

	List<TaoVerseDTO> getVersesInChapter(int chapter) throws ServiceException;
	List<TaoVerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<TaoVerseDTO> getVerses(Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	

	TaoVerseDTO getSingleVerse(int chapter, int verse) throws ServiceException;


}
