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

import com.joeldholmes.commands.GetQuranChapterCommand;
import com.joeldholmes.commands.GetQuranSingleVerseCommand;
import com.joeldholmes.commands.GetQuranVerseRangeCommand;
import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.APIException;
import com.joeldholmes.utils.StatisticCounter;
import com.joeldholmes.utils.StatisticTimer;

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
	public HttpEntity<List<VerseDTO>> getVerse(HttpServletRequest request, @RequestParam(required=false, value="versionAbbr") QuranVersionEnum versionAbbr, @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=false, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		if(versionAbbr==null){
			versionAbbr = QuranVersionEnum.PICKTHALL;
		}
		//Get Chapter Verses
		if((verse==null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Quran to get {} version of chapter {}", versionAbbr, chapter);
			return new HttpEntity<List<VerseDTO>>(getChapterProvider.get().setVersion(versionAbbr).setChapter(chapter).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Quran to get {} version of {}:{}", versionAbbr, chapter, verse);
			return new HttpEntity<List<VerseDTO>>(getSingleVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter!=null)&&(throughVerse!=null)){
			log.info("Request Quran to get {} version of {}:{} through {}:{}", versionAbbr, chapter, verse, throughChapter, throughVerse);
			return new HttpEntity<List<VerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).setThroughChapter(throughChapter).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse!=null)){
			log.info("Request Quran to get {} version of {}:{} - {}", versionAbbr, chapter, verse, throughVerse);
			return new HttpEntity<List<VerseDTO>>(getRangeVerseProvider.get().setVersion(versionAbbr).setChapter(chapter).setVerse(verse).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else{
			throw new APIException("Invalid Parameters");
		}
	}
}