package com.joeldholmes.dto;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

public class BibleVerseDTO implements Comparable<BibleVerseDTO> {

	private String book;
	private int chapter;
	private int verse;
	private String content;
	
	public BibleVerseDTO(){
		super();
	}
	
	public BibleVerseDTO(String book, int chapter, int verse, String content){
		this();
		
		if(book!=null){
			this.book = WordUtils.capitalizeFully(book);
		}
		
		this.chapter=chapter;
		this.verse=verse;
		this.content=content;
	}

	public BibleVerseDTO(VerseEntity entity){
		this();
		
		String entityBook = entity.getBook();
		
		if(entityBook!=null){
			this.book = WordUtils.capitalizeFully(entityBook);
		}
		
		this.chapter=entity.getChapter();
		this.verse=entity.getVerse();
		this.content=entity.getContent();
		
	}
	
	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		if(book!=null){
			this.book = WordUtils.capitalizeFully(book);
		}	
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
	public int compareTo(BibleVerseDTO other) {
		
		if(!this.getBook().equals(other.getBook())){
			return this.getBook().compareTo(other.getBook());
		}
		
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
