package com.joeldholmes.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.factories.TaoFactory;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetTaoSingleVerseCommand extends BaseCommand<List<TaoVerseDTO>> {
	
	@Autowired
	TaoFactory taoFactory;
	
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
	protected List<TaoVerseDTO> run() throws Exception {
		try{
			String content = taoFactory.getVerse(this.chapter, this.verse);
			List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
			dtos.add(new TaoVerseDTO(this.chapter, this.verse, content));
			return dtos;
		}
		catch(FactoryException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}