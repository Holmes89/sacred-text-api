package com.joeldholmes.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.ISearchService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class SearchAllTextAndVerseCommand extends BaseCommand<List<VerseDTO>>{
	
	@Autowired
	ISearchService searchService;
	
	private String term;
	
	protected SearchAllTextAndVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("SearchAllTextAndVerse")));
	}
	
	
	public SearchAllTextAndVerseCommand setTerm(String term){
		this.term=term;
		return this;
	}
	
		
	@Override
	protected List<VerseDTO> run() throws Exception {
		try{
			return searchService.searchAllVerseAndText(term);
		}
		catch(ServiceException e){
			log.error("error creating searching "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}
