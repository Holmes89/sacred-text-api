package com.joeldholmes.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.IQuranService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetQuranVerseRangeCommand extends BaseCommand<List<QuranVerseDTO>> {
	
	@Autowired
	IQuranService quranService;
	
	private QuranVersionEnum version;
	private Integer chapter;
	private Integer verse;
	private Integer throughChapter;
	private Integer throughVerse;
	
	protected GetQuranVerseRangeCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetQuranVerseRange")));
	}
	
	public GetQuranVerseRangeCommand setVersion(QuranVersionEnum version){
		this.version=version;
		return this;
	}
		
	public GetQuranVerseRangeCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
	
	public GetQuranVerseRangeCommand setThroughVerse(Integer throughVerse){
		this.throughVerse=throughVerse;
		return this;
	}
	public GetQuranVerseRangeCommand setThroughChapter(Integer throughChapter){
		this.throughChapter=throughChapter;
		return this;
	}
	
	public GetQuranVerseRangeCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	

	@Override
	protected List<QuranVerseDTO> run() throws Exception {
		try{
			return quranService.getVerses(this.version, this.chapter, this.verse, this.throughChapter, this.throughVerse);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
}