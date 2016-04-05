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

import com.ferrumlabs.commands.GetTaoChapterCommand;
import com.ferrumlabs.commands.GetTaoSingleVerseCommand;
import com.ferrumlabs.commands.GetTaoVerseRangeCommand;
import com.ferrumlabs.dto.TaoVerseDTO;
import com.ferrumlabs.exceptions.APIException;
import com.ferrumlabs.utils.StatisticCounter;
import com.ferrumlabs.utils.StatisticTimer;

@RequestMapping("/tao")
@RestController
public class TaoController extends BaseController {

	@Autowired
	Provider<GetTaoSingleVerseCommand> getSingleVerseProvider;
	
	@Autowired
	Provider<GetTaoVerseRangeCommand> getRangeVerseProvider;
	
	@Autowired
	Provider<GetTaoChapterCommand> getChapterProvider;
	
	public TaoController(){
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/", produces=V1_MEDIA_STRING)
	@StatisticTimer(name="getTaoVerseTimer")
	@StatisticCounter(name="getTaoVerseCounter")
	public HttpEntity<List<TaoVerseDTO>> getVerse(HttpServletRequest request,  @RequestParam(required=true, value="chapter") Integer chapter, @RequestParam(required=false, value="verse") Integer verse, @RequestParam(required=false, value="throughChapter") Integer throughChapter, @RequestParam(required=false, value="throughVerse") Integer throughVerse) throws Throwable
	{
		//Get Chapter Verses
		if((verse==null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Tao to get chapter {}", chapter);
			return new HttpEntity<List<TaoVerseDTO>>(getChapterProvider.get().setChapter(chapter).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse==null)){
			log.info("Request Tao to get {}:{}", chapter, verse);
			return new HttpEntity<List<TaoVerseDTO>>(getSingleVerseProvider.get().setChapter(chapter).setVerse(verse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter!=null)&&(throughVerse!=null)){
			log.info("Request Tao to get {}:{} through {}:{}", chapter, verse, throughChapter, throughVerse);
			return new HttpEntity<List<TaoVerseDTO>>(getRangeVerseProvider.get().setChapter(chapter).setVerse(verse).setThroughChapter(throughChapter).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else if((verse!=null)&&(throughChapter==null)&&(throughVerse!=null)){
			log.info("Request Tao to get {}:{} - {}", chapter, verse, throughVerse);
			return new HttpEntity<List<TaoVerseDTO>>(getRangeVerseProvider.get().setChapter(chapter).setVerse(verse).setThroughVerse(throughVerse).execute(), createEntityHeaders());
		}
		else{
			throw new APIException("Invalid Parameters");
		}
	}
}
