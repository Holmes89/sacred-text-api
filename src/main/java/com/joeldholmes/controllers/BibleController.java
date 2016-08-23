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

import com.joeldholmes.commands.GetBibleChapterCommand;
import com.joeldholmes.commands.GetBibleSingleVerseCommand;
import com.joeldholmes.commands.GetBibleVerseRangeCommand;
import com.joeldholmes.commands.GetBibleVersesByStringCommand;
import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.APIException;
import com.joeldholmes.utils.StatisticCounter;
import com.joeldholmes.utils.StatisticTimer;

@RequestMapping("/bible")
@RestController
public class BibleController extends BaseController {

	@Autowired
	Provider<GetBibleSingleVerseCommand> getSingleVerseProvider;
	
	@Autowired
	Provider<GetBibleVerseRangeCommand> getRangeVerseProvider;
	
	@Autowired
	Provider<GetBibleChapterCommand> getChapterProvider;
	
	@Autowired
	Provider<GetBibleVersesByStringCommand> getBibleVersesByStringProvider;
	
	public BibleController(){
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/", produces = "application/json")
	@StatisticTimer(name="getBibleVerseTimer")
	@StatisticCounter(name="getBibleVerseCounter")
	public HttpEntity<List<VerseDTO>> getVerse(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") BibleVersionEnum versionAbbr, @RequestParam(required=true, value="book") String book, @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=false, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = BibleVersionEnum.NKJV;
		}
		//Get Chapter Verses
		if((verse==null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Bible to get {} version of {} chapter {}", versionAbbr, book, chapter);
			return new HttpEntity<List<VerseDTO>>(getChapterProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Bible to get {} version of {} {}:{}", versionAbbr, book, chapter, verse);
			return new HttpEntity<List<VerseDTO>>(getSingleVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter!=null)&&(throughVerse!=null)){
			log.info("Request Bible to get {} version of {} {}:{} through {}:{}", versionAbbr, book, chapter, verse, throughChapter, throughVerse);
			return new HttpEntity<List<VerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).setThroughChapter(throughChapter).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse!=null)){
			log.info("Request Bible to get {} version of {} {}:{} - {}", versionAbbr, book, chapter, verse, throughVerse);
			return new HttpEntity<List<VerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else{
			throw new APIException("Invalid Parameters");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/search", produces = "application/json")
	@StatisticTimer(name="getBibleVerseTimer")
	@StatisticCounter(name="getBibleVerseCounter")
	public HttpEntity<List<VerseDTO>> searchVerses(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") BibleVersionEnum versionAbbr, @RequestParam(required=true, value="verses") String search) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = BibleVersionEnum.NKJV;
		}
		log.info("Request Bible to search {} version of {}", versionAbbr, search);
		return new HttpEntity<List<VerseDTO>>(getBibleVersesByStringProvider.get().setVersion(versionAbbr).setVerses(search).execute(), createEntityHeaders());
	}
}
