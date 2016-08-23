package com.joeldholmes.dto;

import org.apache.commons.lang3.text.WordUtils;

import com.joeldholmes.entity.VerseEntity;

public class VerseDTO implements Comparable<VerseDTO>{
	
	private Integer chapter; 
	private String version; 
	private String content;
	private Integer verse;
	private String book;
	private String chapterTitle;
	private String religiousText;
	
	
	public VerseDTO(){
		super();
	}

	public VerseDTO(VerseEntity entity){
		this();
		
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
	
	public Integer getChapter() {
		return chapter;
	}
	public String getVersion() {
		return version;
	}
	public String getContent() {
		return content;
	}
	public Integer getVerse() {
		return verse;
	}
	public String getBook() {
		return book;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public String getReligiousText() {
		return religiousText;
	}

	@Override
	public int compareTo(VerseDTO other) {
		if(this.book!=null){
			if(!this.getBook().equals(other.getBook())){
				return this.getBook().compareTo(other.getBook());
			}
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
