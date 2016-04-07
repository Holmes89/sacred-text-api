package com.ferrumlabs.dto;

public class QuranVerseDTO {

	private String chapterName;
	private int chapter;
	private int verse;
	private String content;
	
	public QuranVerseDTO(){
		super();
	}
	
	public QuranVerseDTO(String chapterName, int chapter, int verse, String content){
		this();
		this.chapterName=chapterName;
		this.chapter=chapter;
		this.verse=verse;
		this.content=content;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
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
