package com.joeldholmes.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.IQuranService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetQuranSingleVerseCommand extends BaseCommand<List<VerseDTO>> {
	
	@Autowired
	IQuranService quranService;
	
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
	protected List<VerseDTO> run() throws Exception {
		try{
			List<VerseDTO> dtos = new ArrayList<VerseDTO>();
			dtos.add(quranService.getSingleVerse(version, chapter, verse));
			return dtos;
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}