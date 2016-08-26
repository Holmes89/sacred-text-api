package com.joeldholmes.resources;

import com.joeldholmes.entity.VerseEntity;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="taoVerse")
public class TaoVerseResource implements Comparable<TaoVerseResource>{

	
	public int chapter;
	
	public int verse;
	
	public String content;

	public TaoVerseResource(VerseEntity entity) {
		super();
		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
	}

	
	@Override
	public int compareTo(TaoVerseResource other) {
				
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
