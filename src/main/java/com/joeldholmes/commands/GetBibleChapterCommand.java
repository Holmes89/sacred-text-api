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
public class GetBibleChapterCommand extends BaseCommand<List<BibleVerseDTO>> {
	
	@Autowired
	IBibleService bibleService;
	
	private BibleVersionEnum version;
	private String book;
	private Integer chapter;
	
	protected GetBibleChapterCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleChapter")));
	}
	
	public GetBibleChapterCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleChapterCommand setBook(String book){
		this.book=book;
		return this;
	}
	
	public GetBibleChapterCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
		
		@Override
	protected List<BibleVerseDTO> run() throws Exception {
		try{
			return bibleService.getVersesInChapter(this.version, this.book, this.chapter);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
	

}

