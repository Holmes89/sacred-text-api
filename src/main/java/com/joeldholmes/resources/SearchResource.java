package com.joeldholmes.resources;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="searchResult")
public class SearchResource{
	
	@JsonApiId
	public String id;
	
	public String displayVerse;
	
	public String religiousText;
	
	public String content;
	
	public SearchResource(VerseEntity entity){
		super();
		
		String entityBook = entity.getBook();
		String entityChapterTitle= entity.getChapterTitle();
		
		this.id = entity.getId();
		this.displayVerse = ""; 
		
		if(entityBook!=null){
			this.displayVerse += WordUtils.capitalizeFully(entityBook)+" ";
		}
		if(entityChapterTitle!=null){
			this.displayVerse += WordUtils.capitalizeFully(entityChapterTitle)+" ";
		}
		this.displayVerse += entity.getChapter()+":";
		this.displayVerse+=entity.getVerse();
		
		this.religiousText = entity.getReligiousText();
		this.content=entity.getContent();
	}

}
