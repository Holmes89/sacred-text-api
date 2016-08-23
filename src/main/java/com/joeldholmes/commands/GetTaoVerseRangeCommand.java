package com.joeldholmes.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.ITaoService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetTaoVerseRangeCommand extends BaseCommand<List<VerseDTO>> {
	
	@Autowired
	ITaoService taoService;
	
	private Integer chapter;
	private Integer verse;
	private Integer throughChapter;
	private Integer throughVerse;
	
	protected GetTaoVerseRangeCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetTaoVerseRange")));
	}
		
	public GetTaoVerseRangeCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
	
	public GetTaoVerseRangeCommand setThroughVerse(Integer throughVerse){
		this.throughVerse=throughVerse;
		return this;
	}
	public GetTaoVerseRangeCommand setThroughChapter(Integer throughChapter){
		this.throughChapter=throughChapter;
		return this;
	}
	
	public GetTaoVerseRangeCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	

	@Override
	protected List<VerseDTO> run() throws Exception {
		try{
			return taoService.getVerses(this.chapter, this.verse, this.throughChapter, this.throughVerse);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
}