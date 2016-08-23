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

import com.joeldholmes.commands.GetBibleVersesByStringCommand;
import com.joeldholmes.commands.GetQuranVersesByStringCommand;
import com.joeldholmes.commands.GetTaoVersesByStringCommand;
import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.utils.StatisticCounter;
import com.joeldholmes.utils.StatisticTimer;

@RequestMapping("/search")
@RestController
public class SearchController extends BaseController {

	@Autowired
	Provider<GetBibleVersesByStringCommand> getBibleVersesByStringProvider;
	
	@Autowired
	Provider<GetQuranVersesByStringCommand> getQuranVersesByStringProvider;
	
	@Autowired
	Provider<GetTaoVersesByStringCommand> getTaoVersesByStringProvider;
	
	public SearchController() {
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/bible", produces = "application/json")
	@StatisticTimer(name="getBibleVerseTimer")
	@StatisticCounter(name="getBibleVerseCounter")
	public HttpEntity<List<VerseDTO>> searchVerses(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") BibleVersionEnum versionAbbr, @RequestParam(required=true, value="verses") String search) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = BibleVersionEnum.NIV;
		}
		log.info("Request Bible to search {} version of {}", versionAbbr, search);
		return new HttpEntity<List<VerseDTO>>(getBibleVersesByStringProvider.get().setVersion(versionAbbr).setVerses(search).execute(), createEntityHeaders());
	}

}
