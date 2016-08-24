package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.exceptions.ServiceException;

public interface ISearchService {
	
	List<VerseDTO> searchAllText(String term) throws ServiceException;
	List<VerseDTO> searchBibleText(String term) throws ServiceException;
	List<VerseDTO> searchQuranText(String term) throws ServiceException;
	List<VerseDTO> searchTaoText(String term) throws ServiceException;
	List<VerseDTO> searchAllVerseAndText(String term) throws ServiceException;
	List<VerseDTO> searchBibleVerseAndText(String term) throws ServiceException;
	List<VerseDTO> searchQuranVerseAndText(String term) throws ServiceException;
	List<VerseDTO> searchTaoVerseAndText(String term) throws ServiceException;
}
