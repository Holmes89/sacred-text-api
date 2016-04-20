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

import com.ferrumlabs.commands.GetQuranChapterCommand;
import com.ferrumlabs.commands.GetQuranSingleVerseCommand;
import com.ferrumlabs.commands.GetQuranVerseRangeCommand;
import com.ferrumlabs.dto.QuranVerseDTO;
import com.ferrumlabs.enums.QuranVersionEnum;
import com.ferrumlabs.exceptions.APIException;
import com.ferrumlabs.utils.StatisticCounter;
import com.ferrumlabs.utils.StatisticTimer;

@RequestMapping("/quran")
@RestController
public class QuranController extends BaseController {

	@Autowired
	Provider<GetQuranSingleVerseCommand> getSingleVerseProvider;
	
	@Autowired
	Provider<GetQuranVerseRangeCommand> getRangeVerseProvider;
	
	@Autowired
	Provider<GetQuranChapterCommand> getChapterProvider;
	
	public QuranController(){
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/", produces = "application/json")
	@StatisticTimer(name="getQuranVerseTimer")
	@StatisticCounter(name="getQuranVerseCounter")
	public HttpEntity<List<QuranVerseDTO>> getVerse(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") QuranVersionEnum versionAbbr, @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=false, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = QuranVersionEnum.PICKTHALL;
		}
		//Get Chapter Verses
		if((verse==null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Quran to get {} version of chapter {}", versionAbbr, chapter);
			return new HttpEntity<List<QuranVerseDTO>>(getChapterProvider.get().setVersion(versionAbbr).setChapter(chapter).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Quran to get {} version of {}:{}", versionAbbr, chapter, verse);
			return new HttpEntity<List<QuranVerseDTO>>(getSingleVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter!=null)&&(throughVerse!=null)){
			log.info("Request Quran to get {} version of {}:{} through {}:{}", versionAbbr, chapter, verse, throughChapter, throughVerse);
			return new HttpEntity<List<QuranVerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).setThroughChapter(throughChapter).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse!=null)){
			log.info("Request Quran to get {} version of {}:{} - {}", versionAbbr, chapter, verse, throughVerse);
			return new HttpEntity<List<QuranVerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else{
			throw new APIException("Invalid Parameters");
		}
	}
}