package com.ferrumlabs.controllers;

import java.util.List;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferrumlabs.commands.GetBibleChapterCommand;
import com.ferrumlabs.commands.GetBibleSingleVerseCommand;
import com.ferrumlabs.commands.GetBibleVerseRangeCommand;
import com.ferrumlabs.commands.GetBibleVersesByStringCommand;
import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.APIException;
import com.ferrumlabs.utils.StatisticCounter;
import com.ferrumlabs.utils.StatisticTimer;

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
	public HttpEntity<List<BibleVerseDTO>> getVerse(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") BibleVersionEnum versionAbbr, @RequestParam(required=true, value="book") String book, @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=false, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = BibleVersionEnum.NKJV;
		}
		//Get Chapter Verses
		if((verse==null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Bible to get {} version of {} chapter {}", versionAbbr, book, chapter);
			return new HttpEntity<List<BibleVerseDTO>>(getChapterProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Bible to get {} version of {} {}:{}", versionAbbr, book, chapter, verse);
			return new HttpEntity<List<BibleVerseDTO>>(getSingleVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter!=null)&&(throughVerse!=null)){
			log.info("Request Bible to get {} version of {} {}:{} through {}:{}", versionAbbr, book, chapter, verse, throughChapter, throughVerse);
			return new HttpEntity<List<BibleVerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).setThroughChapter(throughChapter).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse!=null)){
			log.info("Request Bible to get {} version of {} {}:{} - {}", versionAbbr, book, chapter, verse, throughVerse);
			return new HttpEntity<List<BibleVerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setBook(book).setChapter(chapter).setVerse(verse).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else{
			throw new APIException("Invalid Parameters");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/search", produces = "application/json")
	@StatisticTimer(name="getBibleVerseTimer")
	@StatisticCounter(name="getBibleVerseCounter")
	public HttpEntity<List<BibleVerseDTO>> searchVerses(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") BibleVersionEnum versionAbbr, @RequestParam(required=true, value="verses") String search) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = BibleVersionEnum.NKJV;
		}
		log.info("Request Bible to search {} version of {}", versionAbbr, search);
		return new HttpEntity<List<BibleVerseDTO>>(getBibleVersesByStringProvider.get().setVersion(versionAbbr).setVerses(search).execute(), createEntityHeaders());
	}
}
