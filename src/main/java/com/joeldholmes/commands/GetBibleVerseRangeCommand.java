package com.joeldholmes.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.BibleVerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.IBibleService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetBibleVerseRangeCommand extends BaseCommand<List<BibleVerseDTO>> {
	
	@Autowired
	IBibleService bibleService;
	
	private BibleVersionEnum version;
	private String book;
	private Integer chapter;
	private Integer verse;
	private Integer throughChapter;
	private Integer throughVerse;
	
	protected GetBibleVerseRangeCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleVerseRange")));
	}
	
	public GetBibleVerseRangeCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleVerseRangeCommand setBook(String book){
		this.book=book;
		return this;
	}
	
	public GetBibleVerseRangeCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
	
	public GetBibleVerseRangeCommand setThroughVerse(Integer throughVerse){
		this.throughVerse=throughVerse;
		return this;
	}
	public GetBibleVerseRangeCommand setThroughChapter(Integer throughChapter){
		this.throughChapter=throughChapter;
		return this;
	}
	
	public GetBibleVerseRangeCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	

	@Override
	protected List<BibleVerseDTO> run() throws Exception {
		try{
			return bibleService.getVerses(this.version, this.book, this.chapter, this.verse, this.throughChapter, this.throughVerse);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
}
