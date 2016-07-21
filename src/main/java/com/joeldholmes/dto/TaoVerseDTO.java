package com.joeldholmes.dto;

public class TaoVerseDTO {

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
}
