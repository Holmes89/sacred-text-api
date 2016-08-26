package com.joeldholmes.resources;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="quranVerse")
public class QuranVerseResource implements Comparable<QuranVerseResource>{

	public String chapterTitle;
	
	public int chapter;
	
	public int verse;
	
	public String content;
	
	public QuranVerseResource(VerseEntity entity){
		super();
		String chapterTitle = entity.getChapterTitle();
		if(chapterTitle!=null){
			this.chapterTitle=  WordUtils.capitalizeFully(chapterTitle);
		}
		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
	}

	@Override
	public int compareTo(QuranVerseResource other) {
				
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
