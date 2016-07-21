package com.joeldholmes.dto;

public class BibleVerseDTO {

	private String book;
	private int chapter;
	private int verse;
	private String content;
	
	public BibleVerseDTO(){
		super();
	}
	
	public BibleVerseDTO(String book, int chapter, int verse, String content){
		this();
		this.book=book;
		this.chapter=chapter;
		this.verse=verse;
		this.content=content;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
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
