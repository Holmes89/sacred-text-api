package com.joeldholmes.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.BibleVerseDTO;
import com.joeldholmes.enums.BibleVersionEnum;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.factories.BibleFactory;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetBibleSingleVerseCommand extends BaseCommand<List<BibleVerseDTO>> {
	
	@Autowired
	BibleFactory bibleFactory;
	
	private BibleVersionEnum version;
	private String book;
	private Integer chapter;
	private Integer verse;
	
	protected GetBibleSingleVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleSingleVerse")));
	}
	
	public GetBibleSingleVerseCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleSingleVerseCommand setBook(String book){
		this.book=book;
		return this;
	}
	
	public GetBibleSingleVerseCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
		
	public GetBibleSingleVerseCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	
	@Override
	protected List<BibleVerseDTO> run() throws Exception {
		try{
			String content = bibleFactory.getVerse(this.version, this.book, this.chapter, this.verse);
			List<BibleVerseDTO> dtos = new ArrayList<BibleVerseDTO>();
			dtos.add(new BibleVerseDTO(this.book, this.chapter, this.verse, content));
			return dtos;
		}
		catch(FactoryException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}

