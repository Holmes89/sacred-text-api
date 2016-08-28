package com.joeldholmes.resources;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="bibleVerse")
public class BibleVerseResource implements Comparable<BibleVerseResource>{

	@JsonApiId
	public String id;
	
	public String book;
	
	public int chapter;
	
	public int verse;
	
	public String content;
	
	public String version;

	public BibleVerseResource(VerseEntity entity){
		super();
		String entityBook = entity.getBook();
		
		if(entityBook!=null){
			this.book = WordUtils.capitalizeFully(entityBook);
		}
		
		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
		this.id = entity.getId();
		this.version = entity.getVersion();
		
	}

	@Override
	public int compareTo(BibleVerseResource other) {
		
		if(!this.book.equals(other.book)){
			return this.book.compareTo(other.book);
		}
		
		if(this.chapter>other.chapter){
			return 1;
		}
		else if(this.chapter<other.chapter){
			return -1;
		}
		
		if(this.verse>other.verse){
			return 1;
		}
		else if(this.verse<other.verse){
			return -1;
		}
		
		return 0;
	}
}
