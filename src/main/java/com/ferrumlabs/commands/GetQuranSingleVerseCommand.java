package com.ferrumlabs.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ferrumlabs.dto.QuranVerseDTO;
import com.ferrumlabs.enums.QuranVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.factories.QuranFactory;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetQuranSingleVerseCommand extends BaseCommand<List<QuranVerseDTO>> {
	
	@Autowired
	QuranFactory quranFactory;
	
	private QuranVersionEnum version;
	private Integer chapter;
	private Integer verse;
	
	protected GetQuranSingleVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetQuranSingleVerse")));
	}
	
	public GetQuranSingleVerseCommand setVersion(QuranVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetQuranSingleVerseCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
		
	public GetQuranSingleVerseCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	
	@Override
	protected List<QuranVerseDTO> run() throws Exception {
		try{
			String content = quranFactory.getVerse(this.version, this.chapter, this.verse);
			String chapterName = quranFactory.getChapterName(this.version, this.chapter);
			List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
			dtos.add(new QuranVerseDTO(chapterName, this.chapter, this.verse, content));
			return dtos;
		}
		catch(FactoryException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}