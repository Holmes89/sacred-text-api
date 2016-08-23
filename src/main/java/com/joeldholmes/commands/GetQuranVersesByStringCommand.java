package com.joeldholmes.commands;

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
public class GetQuranVersesByStringCommand extends BaseCommand<List<VerseDTO>> {
	
	@Autowired
	IQuranService quranService;
	
	private QuranVersionEnum version;
	private String verses;
	
	protected GetQuranVersesByStringCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetQuranVerseString")));
	}
	
	public GetQuranVersesByStringCommand setVersion(QuranVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetQuranVersesByStringCommand setVerses(String verses){
		this.verses=verses;
		return this;
	}
	
		
	@Override
	protected List<VerseDTO> run() throws Exception {
		try{
			return quranService.getVersesFromString(version, verses);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
	
	

}

