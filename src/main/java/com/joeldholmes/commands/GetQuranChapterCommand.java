package com.joeldholmes.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.services.interfaces.IQuranService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Component
@Scope("prototype")
public class GetQuranChapterCommand extends BaseCommand<List<QuranVerseDTO>> {
	
	@Autowired
	IQuranService quranService;
	
	private QuranVersionEnum version;
	private Integer chapter;
	
	protected GetQuranChapterCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SacredTextAPI"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetQuranChapter")));
	}
	
	public GetQuranChapterCommand setVersion(QuranVersionEnum version){
		this.version=version;
		return this;
	}
	
	public GetQuranChapterCommand setChapter(Integer chapter){
		this.chapter=chapter;
		return this;
	}
		
		@Override
	protected List<QuranVerseDTO> run() throws Exception {
		try{
			return quranService.getVersesInChapter(this.version, this.chapter);
		}
		catch(ServiceException e){
			log.error("error creating getting verse "+e);
			throw new HystrixBadRequestException("unable to process request", e);
		}
	}
}
	
	