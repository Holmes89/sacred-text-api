package com.joeldholmes.commands;

import java.util.ArrayList;
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
public class GetTaoSingleVerseCommand extends BaseCommand<List<VerseDTO>> {
	
	@Autowired
	ITaoService taoService;
	
	private Integer chapter;
	private Integer verse;
	
	protected GetTaoSingleVerseCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetTaoSingleVerse")));
	}
	
	
	public GetTaoSingleVerseCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
		
	public GetTaoSingleVerseCommand setVerse(Integer verse){
		this.verse=verse;
		return this;
	}
	
	@Override
	protected List<VerseDTO> run() throws Exception {
		try{
			List<VerseDTO> dtos = new ArrayList<VerseDTO>();
			dtos.add(taoService.getSingleVerse(chapter, verse));
			return dtos;
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}