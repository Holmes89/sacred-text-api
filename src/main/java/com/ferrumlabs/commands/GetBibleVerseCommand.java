package com.ferrumlabs.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.factories.BibleFactory;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetBibleVerseCommand extends BaseCommand<String> {
	
	@Autowired
	BibleFactory bibleFactory;
	
	private BibleVersionEnum version;
	private String book;
	private int chapter;
	private int verse;
	
	
	public GetBibleVerseCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleVerseCommand setBook(String book){
		this.book=book;
		return this;
	}
	
	public GetBibleVerseCommand setChapter(int chapter){
		this.chapter=chapter;
		return this;
	}
	
	public GetBibleVerseCommand setVerse(int verse){
		this.verse=verse;
		return this;
	}
	
	protected GetBibleVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleVerse")));
	}

	@Override
	protected String run() throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(bibleFactory.getVerse(this.version, this.book, this.chapter, this.verse));
		}
		catch(FactoryException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
	

}
