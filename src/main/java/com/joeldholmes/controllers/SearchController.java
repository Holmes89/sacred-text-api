package com.joeldholmes.controllers;

import java.util.List;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joeldholmes.commands.SearchAllTextAndVerseCommand;
import com.joeldholmes.commands.SearchBibleTextAndVerseCommand;
import com.joeldholmes.commands.SearchQuranTextAndVerseCommand;
import com.joeldholmes.commands.SearchTaoTextAndVerseCommand;
import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.utils.StatisticCounter;
import com.joeldholmes.utils.StatisticTimer;

@RequestMapping("/search")
@RestController
public class SearchController extends BaseController {
	
	@Autowired
	Provider<SearchAllTextAndVerseCommand> searchAllTextAndVerseProvider;
	
	@Autowired
	Provider<SearchBibleTextAndVerseCommand> searchBibleTextAndVerseProvider;
	
	@Autowired
	Provider<SearchQuranTextAndVerseCommand> searchQuranTextAndVerseProvider;
	
	@Autowired
	Provider<SearchTaoTextAndVerseCommand> searchTaoTextAndVerseProvider;
	
	public SearchController() {
		super();
	}
	

	@RequestMapping(method = RequestMethod.GET, value="/", produces = "application/json")
	@StatisticTimer(name="searchAllTime")
	@StatisticCounter(name="searchAllCounter")
	public HttpEntity<List<VerseDTO>> searchAll(HttpServletRequest request, @RequestParam(required=true, value="term") String search) throws Throwable
	{
		log.info("Request to search {}", search);
		return new HttpEntity<List<VerseDTO>>(searchAllTextAndVerseProvider.get().setTerm(search).execute(), createEntityHeaders());
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/bible", produces = "application/json")
	@StatisticTimer(name="searchBibleTime")
	@StatisticCounter(name="searchBibleCounter")
	public HttpEntity<List<VerseDTO>> searchBible(HttpServletRequest request, @RequestParam(required=true, value="term") String search) throws Throwable
	{
		log.info("Request Bible to search {}", search);
		return new HttpEntity<List<VerseDTO>>(searchBibleTextAndVerseProvider.get().setTerm(search).execute(), createEntityHeaders());
	}

	@RequestMapping(method = RequestMethod.GET, value="/tao-te-ching", produces = "application/json")
	@StatisticTimer(name="searchQuranTime")
	@StatisticCounter(name="searchQuranCounter")
	public HttpEntity<List<VerseDTO>> searchTao(HttpServletRequest request, @RequestParam(required=true, value="term") String search) throws Throwable
	{
		log.info("Request Quran to search {} version of {}", search);
		return new HttpEntity<List<VerseDTO>>(searchTaoTextAndVerseProvider.get().setTerm(search).execute(), createEntityHeaders());
	}

	@RequestMapping(method = RequestMethod.GET, value="/quran", produces = "application/json")
	@StatisticTimer(name="searchTaoTime")
	@StatisticCounter(name="searchTaoCounter")
	public HttpEntity<List<VerseDTO>> searchQuran(HttpServletRequest request, @RequestParam(required=true, value="term") String search) throws Throwable
	{
		log.info("Request Tao to search {} version of {}", search);
		return new HttpEntity<List<VerseDTO>>(searchQuranTextAndVerseProvider.get().setTerm(search).execute(), createEntityHeaders());
	}

}
