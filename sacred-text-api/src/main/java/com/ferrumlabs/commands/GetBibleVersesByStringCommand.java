package com.ferrumlabs.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.services.interfaces.IBibleService;
import com.ferrumlabs.utils.ErrorCodes;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetBibleVersesByStringCommand extends BaseCommand<List<BibleVerseDTO>> {
	
	@Autowired
	IBibleService bibleService;
	
	private BibleVersionEnum version;
	private String verses;
	
	protected GetBibleVersesByStringCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBibleVerseString")));
	}
	
	public GetBibleVersesByStringCommand setVersion(BibleVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetBibleVersesByStringCommand setVerses(String verses){
		this.verses=verses;
		return this;
	}
	
		
	@Override
	protected List<BibleVerseDTO> run() throws Exception {
		try{
			return bibleService.getVersesFromString(version, verses);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
	

}

