package com.ferrumlabs.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.services.interfaces.IBibleService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetBibleVerseCommand extends BaseCommand<List<BibleVerseDTO>> {
	
	@Autowired
	IBibleService bibleService;
	
	private BibleVersionEnum version;
	private String book;
	private Integer chapter;
	private Integer verse;
	private Integer throughChapter;
	private Integer throughVerse;
	
	
	public GetBibleVerseCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleVerseCommand setBook(String book){
		this.book=book;
		return this;
	}
	
	public GetBibleVerseCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
	
	public GetBibleVerseCommand setThroughVerse(Integer throughVerse){
		this.throughVerse=throughVerse;
		return this;
	}
	public GetBibleVerseCommand setThroughChapter(Integer throughChapter){
		this.throughChapter=throughChapter;
		return this;
	}
	
	public GetBibleVerseCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	
	protected GetBibleVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleVerse")));
	}

	@Override
	protected List<BibleVerseDTO> run() throws Exception {
		try{
			return bibleService.getVersesInRange(this.version, this.book, this.chapter, this.verse, this.throughChapter, this.throughVerse);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
	

}
