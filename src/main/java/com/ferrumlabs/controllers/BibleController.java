package com.ferrumlabs.controllers;

import java.util.List;

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
import com.ferrumlabs.dto.BibleVerseDTO;
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
	public HttpEntity<List<BibleVerseDTO>> getVerse(HttpServletRequest request, final @PathVariable BibleVersionEnum versionAbbr, @RequestParam(required=true, value="book") String book, @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=true, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		log.info("Request to get {} version of {} {}:{}", versionAbbr, book, chapter, verse);
		
		if(throughChapter!=null){
			getVerseProvider.get().setThroughChapter(throughChapter);
		}
		if(throughVerse!=null){
			getVerseProvider.get().setThroughVerse(throughVerse);
		}
		return new HttpEntity<List<BibleVerseDTO>>(getVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		
	}
}
