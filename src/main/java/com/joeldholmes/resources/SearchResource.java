package com.joeldholmes.resources;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="searchResult")
public class SearchResource implements Comparable<SearchResource>{
	
	public int chapter; 
	
	public String version; 
	
	public String content;
	
	public int verse;
	
	public String book;
	
	public String chapterTitle;
	
	public String religiousText;
	
	public SearchResource(VerseEntity entity){
		super();
		
		String entityBook = entity.getBook();
		String entityChapterTitle= entity.getChapterTitle();
		String entityReligiousText= entity.getReligiousText();
		String entityVersion= entity.getVersion();

		if(entityBook!=null){
			this.book = WordUtils.capitalizeFully(entityBook);
		}
		if(entityChapterTitle!=null){
			this.chapterTitle = WordUtils.capitalizeFully(entityChapterTitle);
		}
		if(entityReligiousText!=null){
			this.religiousText = WordUtils.capitalizeFully(entityReligiousText);
		}
		if(entityVersion!=null){
			this.version = WordUtils.capitalizeFully(entityVersion);
		}

		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
		
	}
	
	@Override
	public int compareTo(SearchResource other) {
		if(this.book!=null){
			if(!this.book.equals(other.book)){
				return this.book.compareTo(other.book);
			}
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
