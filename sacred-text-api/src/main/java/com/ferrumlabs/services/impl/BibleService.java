package com.ferrumlabs.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.factories.BibleFactory;
import com.ferrumlabs.services.interfaces.IBibleService;
import com.ferrumlabs.utils.ErrorCodes;

@Service("BibleService")
public class BibleService implements IBibleService{
	
	@Autowired
	BibleFactory bibleFactory;
	
	@Override
	public List<BibleVerseDTO> getVersesInChapter(BibleVersionEnum version, String book, int chapter) throws ServiceException{
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(book == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Book cannot be null");
		}
		List<BibleVerseDTO> dtos = new ArrayList<BibleVerseDTO>();
		try{	
			List<Integer> chapterList = bibleFactory.getChapterList(version, book);
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			Map<Integer, String> versesInChapter = bibleFactory.getVerses(version, book, chapter);
			
			for(int verse: versesInChapter.keySet()){
				String content = versesInChapter.get(verse);
				dtos.add(new BibleVerseDTO(book, chapter, verse, content));
			}
			
		}catch(FactoryException e){
			throw new ServiceException("Factory error", e);
		}
		return dtos;
	}
	
	@Override
	@Deprecated
	public List<BibleVerseDTO> getVersesInRange(BibleVersionEnum version, String book, Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException{
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(book == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Book cannot be null");
		}
		if(chapter == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null");
		}
		if(verse == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Verse cannot be null");
		}
		if(chapter == throughChapter){
			throughChapter = null;
		}
		List<BibleVerseDTO> dtos = new ArrayList<BibleVerseDTO>();
		try{
			
			List<Integer> chapterList = bibleFactory.getChapterList(version, book);
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			
			if((throughChapter == null) && (throughVerse == null)){
				String singleVerseContent = bibleFactory.getVerse(version, book, chapter, verse);
				BibleVerseDTO singleDTO = new BibleVerseDTO(book, chapter, verse, singleVerseContent);
				dtos.add(singleDTO);
				return dtos;
			}
			else if((throughChapter != null) && (throughVerse == null)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Range can only end in chapter and verse or verse only");
			}
			else if((throughChapter == null) && (throughVerse != null)){
				if(throughVerse<verse){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse cannot be earlier than beginning verse");
				}
				Map<Integer, String> versesInChapter = bibleFactory.getVerses(version, book, chapter);
				if(!versesInChapter.keySet().contains(throughVerse)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
				}
				for(int index=verse; index<=throughVerse; index++){
					String content = versesInChapter.get(index);
					BibleVerseDTO dto = new BibleVerseDTO(book, chapter, index, content);
					dtos.add(dto);
				}
				return dtos;
			}
			else{
				if(throughChapter<chapter){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End chapter cannot be earlier than beginning chapter");
				}
				if(!chapterList.contains(throughChapter)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End chapter not in book");
				}
				for(int chapterIndex=chapter; chapterIndex<=throughChapter; chapterIndex++){
					Map<Integer, String> versesInChapter = bibleFactory.getVerses(version, book, chapterIndex);
					if(chapterIndex==throughChapter){
						if(!versesInChapter.keySet().contains(throughVerse)){
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
						}
						for(int verseIndex=1; verseIndex<=throughVerse; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							BibleVerseDTO dto = new BibleVerseDTO(book, chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
					}
					else{
						int numberOfVerses = versesInChapter.size();
						for(int verseIndex=verse; verseIndex<=numberOfVerses; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							BibleVerseDTO dto = new BibleVerseDTO(book, chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
						verse=1;
					}
				}
				return dtos;
			}
		}catch(FactoryException e){
			throw new ServiceException("Factory error", e);
		}
	}
	
	@Override
	public List<BibleVerseDTO> getVerses(BibleVersionEnum version, String book, Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException{
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(book == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Book cannot be null");
		}
		if(chapter == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null");
		}
		if(chapter == throughChapter){
			throughChapter = null;
		}
		if((throughChapter!=null) && (chapter > throughChapter)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start Chapter cannot be less than end chapter");
		}
			
		List<BibleVerseDTO> dtos = new ArrayList<BibleVerseDTO>();
		try{
			
			List<Integer> chapterList = bibleFactory.getChapterList(version, book);
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter not in book");
			}
			
			if((chapter != null) && (verse == null) && (throughChapter == null) && (throughVerse == null)){
				return getVersesInChapter(version, book, chapter);
			}
			else if((chapter != null) && (verse == null) && (throughChapter != null) && (throughVerse == null)){
				for(int x = chapter; x<=throughChapter; x++){
					dtos.addAll(this.getVersesInChapter(version, book, x));
				}
				return dtos;
			}
			else if((throughChapter == null) && (throughVerse == null)){
				String singleVerseContent = bibleFactory.getVerse(version, book, chapter, verse);
				BibleVerseDTO singleDTO = new BibleVerseDTO(book, chapter, verse, singleVerseContent);
				dtos.add(singleDTO);
				return dtos;
			}
			else if((throughChapter == null) && (throughVerse != null)){
				if(throughVerse<verse){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start verse cannot be less than end verse");
				}
				Map<Integer, String> versesInChapter = bibleFactory.getVerses(version, book, chapter);
				if(!versesInChapter.keySet().contains(throughVerse)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
				}
				for(int index=verse; index<=throughVerse; index++){
					String content = versesInChapter.get(index);
					BibleVerseDTO dto = new BibleVerseDTO(book, chapter, index, content);
					dtos.add(dto);
				}
				return dtos;
			}
			else if((throughChapter != null) && (throughVerse != null)){
				if(verse==null){
					verse=1;
				}
				if(!chapterList.contains(throughChapter)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "Book does not contain chapter");
				}
				for(int chapterIndex=chapter; chapterIndex<=throughChapter; chapterIndex++){
					Map<Integer, String> versesInChapter = bibleFactory.getVerses(version, book, chapterIndex);
					if(chapterIndex==throughChapter){
						if(!versesInChapter.keySet().contains(throughVerse)){
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
						}
						for(int verseIndex=1; verseIndex<=throughVerse; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							BibleVerseDTO dto = new BibleVerseDTO(book, chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
					}
					else{
						int numberOfVerses = versesInChapter.size();
						for(int verseIndex=verse; verseIndex<=numberOfVerses; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							BibleVerseDTO dto = new BibleVerseDTO(book, chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
						verse=1;
					}
				}
				
			}
		}catch(FactoryException e){
			throw new ServiceException("Factory error", e);
		}
		return dtos;
	}
	
	@Override
	public List<BibleVerseDTO> getVersesFromString(BibleVersionEnum version, String verses) throws ServiceException{
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(verses == null || verses.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Verse cannot be null or empty");
		}
		
		List<BibleVerseDTO> verseList = new ArrayList<BibleVerseDTO>();
		
		String fullRegex = "(\\d?\\s?\\w+)\\s([\\d:]+)-?([\\d:]+)?";
		String chapterVerseRegex = "([\\d:]+)-?([\\d:]+)?";
		
		Pattern fullRegexPattern = Pattern.compile(fullRegex);
		Pattern chapterVersePattern =Pattern.compile(chapterVerseRegex);
		
		String book = null;
		int startChapter = 0;
		int startVerse = 0;
		int endChapter = 0;
		int endVerse = 0;
		
		String[] verseArray = verses.trim().split(",");
		for(String verse: verseArray){
			verse = verse.trim();
			Matcher m = fullRegexPattern.matcher(verse);	
			if(m.matches()){
				book = m.group(1);
				String[] cv = m.group(2).split(":");
				startChapter = Integer.parseInt(cv[0]);
				if(cv.length==2){
					startVerse = Integer.parseInt(cv[1]);
				}
				else if(cv.length>2){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
				}
				if(m.group(3)!=null){
					cv = m.group(3).split(":");
					if(cv.length==1){
						endVerse = Integer.parseInt(cv[0]);
					}
					else if(cv.length==2){
						endChapter = Integer.parseInt(cv[0]);
						endVerse = Integer.parseInt(cv[1]);
					}
					else{
						throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
					}
				}
			}
			else if(verse.matches(chapterVerseRegex)){
				m = chapterVersePattern.matcher(verse);
				if(m.matches()){
					String[] nextCv = m.group(1).split(":");
					if(startVerse==0){
						startChapter = Integer.parseInt(nextCv[0]);
						if(nextCv.length==2){
							startVerse = Integer.parseInt(nextCv[1]);
						}
						else{
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
						}
					}
					else{
						if(nextCv.length==1){
							startVerse = Integer.parseInt(nextCv[0]);
						}
						else if(nextCv.length==2){
							startChapter = Integer.parseInt(nextCv[0]);
							startVerse = Integer.parseInt(nextCv[1]);
						}
						else{
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
						}
					}
					if(m.group(2)!=null){
						nextCv = m.group(2).split(":");
						if(startChapter!=0 && startVerse!=0){
							if(nextCv.length==1){
								endVerse = Integer.parseInt(nextCv[0]);
							}
							else if(nextCv.length==2){
								endChapter = Integer.parseInt(nextCv[0]);
								endVerse = Integer.parseInt(nextCv[1]);
							}
							else{
								throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
							}
						}
						else if(startChapter!=0){
							if(nextCv.length==1){
								endChapter = Integer.parseInt(nextCv[0]);
							}
							else if(nextCv.length==2){
								endChapter = Integer.parseInt(nextCv[0]);
								endVerse = Integer.parseInt(nextCv[1]);
							}
							else{
								return null;
							}
						}
						else if(startVerse!=0){
							if(nextCv.length==1){
								endVerse = Integer.parseInt(nextCv[0]);
							}
							else if(nextCv.length==2){
								endChapter = Integer.parseInt(nextCv[0]);
								endVerse = Integer.parseInt(nextCv[1]);
							}
							else{
								throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
							}
						}
					}
				}
			}
			else{
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
			}
			//get verses here.
			verseList.addAll(getVerses(version, book, startChapter, startVerse, endChapter, endVerse));
			
		}
		
		return verseList;
	}
	
	@Override
	public List<BibleVerseDTO> getVersesFromString(String verses) throws ServiceException{
		return getVersesFromString(BibleVersionEnum.NIV, verses);
	}
}
