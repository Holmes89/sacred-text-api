package com.joeldholmes.dto;

import com.joeldholmes.entity.VerseEntity;

public class TaoVerseDTO implements Comparable<TaoVerseDTO>{

	private int chapter;
	private int verse;
	private String content;
	
	public TaoVerseDTO(){
		super();
	}
	
	public TaoVerseDTO(int chapter, int verse, String content){
		this();
		this.chapter=chapter;
		this.verse=verse;
		this.content=content;
	}

	public TaoVerseDTO(VerseEntity entity) {
		this();
		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getVerse() {
		return verse;
	}

	public void setVerse(int verse) {
		this.verse = verse;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public int compareTo(TaoVerseDTO other) {
				
		if(this.getChapter()>other.getChapter()){
			return 1;
		}
		else if(this.getChapter()<other.getChapter()){
			return -1;
		}
		
		if(this.getVerse()>other.getVerse()){
			return 1;
		}
		else if(this.getVerse()<other.getVerse()){
			return -1;
		}
		
		return 0;
	}
}
