package com.ferrumlabs.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
}
