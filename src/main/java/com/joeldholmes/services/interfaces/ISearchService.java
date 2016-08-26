package com.joeldholmes.services.interfaces;

import java.util.List;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;

public interface ISearchService {
	
	List<SearchResource> searchAllText(String term) throws ServiceException;
	List<SearchResource> searchBibleText(String term) throws ServiceException;
	List<SearchResource> searchQuranText(String term) throws ServiceException;
	List<SearchResource> searchTaoText(String term) throws ServiceException;
	List<SearchResource> searchAllVerseAndText(String term) throws ServiceException;
	List<SearchResource> searchBibleVerseAndText(String term) throws ServiceException;
	List<SearchResource> searchQuranVerseAndText(String term) throws ServiceException;
	List<SearchResource> searchTaoVerseAndText(String term) throws ServiceException;
}
