package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.entity.VerseEntity;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.repository.IVerseRepository;
import com.joeldholmes.services.interfaces.ISearchService;
import com.joeldholmes.utils.ErrorCodes;

@Service("SearchService")
public class SearchService implements ISearchService {

	@Autowired
	IVerseRepository verseRepository;

	@Override
	public List<VerseDTO> searchAllText(String term) throws ServiceException {
		if(term==null||term.isEmpty()){
			throw new ServiceException("Search term cannot be null or empty", ErrorCodes.NULL_INPUT);
		}
		List<VerseEntity> entities = verseRepository.searchAllText(term);
		if(entities==null||entities.isEmpty()){
			return null;
		}
		return entitiesToDTOs(entities);
	}

	@Override
	public List<VerseDTO> searchBibleText(String term) throws ServiceException {
		if(term==null||term.isEmpty()){
			throw new ServiceException("Search term cannot be null or empty", ErrorCodes.NULL_INPUT);
		}
		List<VerseEntity> entities = verseRepository.searchAllBibleText(term);
		if(entities==null||entities.isEmpty()){
			return null;
		}
		return entitiesToDTOs(entities);
	}

	@Override
	public List<VerseDTO> searchQuranText(String term) throws ServiceException {
		if(term==null||term.isEmpty()){
			throw new ServiceException("Search term cannot be null or empty", ErrorCodes.NULL_INPUT);
		}
		List<VerseEntity> entities = verseRepository.searchAllQuranText(term);
		if(entities==null||entities.isEmpty()){
			return null;
		}
		return entitiesToDTOs(entities);
		
	}

	@Override
	public List<VerseDTO> searchTaoText(String term) throws ServiceException {
		if(term==null||term.isEmpty()){
			throw new ServiceException("Search term cannot be null or empty", ErrorCodes.NULL_INPUT);
		}
		List<VerseEntity> entities = verseRepository.searchAllTaoText(term);
		if(entities==null||entities.isEmpty()){
			return null;
		}
		return entitiesToDTOs(entities);
		
	}
	
	private List<VerseDTO> entitiesToDTOs(List<VerseEntity> entities){
		List<VerseDTO> list = new ArrayList<VerseDTO>();
		for(VerseEntity entity: entities){
			list.add(new VerseDTO(entity));
		}
		
		return list;
	}

}
