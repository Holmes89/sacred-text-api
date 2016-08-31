package com.joeldholmes.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.SearchResource;

public interface ISearchService {
	
	Iterable<SearchResource> searchAllText(String term, Pageable page) throws ServiceException;
	List<SearchResource> searchBibleText(String term) throws ServiceException;
	List<SearchResource> searchQuranText(String term) throws ServiceException;
	List<SearchResource> searchTaoText(String term) throws ServiceException;
}
