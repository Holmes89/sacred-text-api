package com.ferrumlabs.controllers;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ferrumlabs.commands.GetBibleVerseCommand;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.utils.StatisticCounter;
import com.ferrumlabs.utils.StatisticTimer;

@RequestMapping("/bible")
@Controller
public class BibleController extends BaseController {

	@Autowired
	Provider<GetBibleVerseCommand> getVerseProvider;
	
	public BibleController(){
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{versionAbbr}", produces=V1_MEDIA_STRING)
	@ResponseBody
	@StatisticTimer(name="getBibleVerseTimer")
	@StatisticCounter(name="getBibleVerseCounter")
	public HttpEntity<String> getVerse(HttpServletRequest request, final @PathVariable BibleVersionEnum versionAbbr, @RequestParam(required=true, value="book") String book, @RequestParam(required=true, value="chapter") int chapter, @RequestParam(required=true, value="verse") int verse) throws Throwable
	{
		log.info("Request to get {} version of {} {}:{}", versionAbbr, book, chapter, verse);
		
		return new HttpEntity<String>(getVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		
	}
}
