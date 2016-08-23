package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.exceptions.ServiceException;

public interface ITaoService {

	List<VerseDTO> getVersesInChapter(int chapter) throws ServiceException;
	List<VerseDTO> getVersesFromString(String verses) throws ServiceException;

	List<VerseDTO> getVerses(Integer chapter, Integer verse,
			Integer throughChapter, Integer throughVerse) throws ServiceException;
	

	VerseDTO getSingleVerse(int chapter, int verse) throws ServiceException;


}
