package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.FactoryException;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.factories.QuranFactory;
import com.joeldholmes.services.interfaces.IQuranService;
import com.joeldholmes.utils.ErrorCodes;

@Service("QuranService")
public class QuranService implements IQuranService {

	@Autowired
	QuranFactory quranFactory;
	
	@Override
	public List<QuranVerseDTO> getVersesInRange(QuranVersionEnum version, Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
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
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		try{
			
			List<Integer> chapterList = quranFactory.getChapters(version);
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			
			if((throughChapter == null) && (throughVerse == null)){
				String singleVerseContent = quranFactory.getVerse(version, chapter, verse);
				String chapterName = quranFactory.getChapterName(version, chapter);
				QuranVerseDTO singleDTO = new QuranVerseDTO(chapterName, chapter, verse, singleVerseContent);
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
				Map<Integer, String> versesInChapter = quranFactory.getVerses(version, chapter);
				if(!versesInChapter.keySet().contains(throughVerse)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
				}
				for(int index=verse; index<=throughVerse; index++){
					String content = versesInChapter.get(index);
					String chapterName = quranFactory.getChapterName(version, chapter);
					QuranVerseDTO dto = new QuranVerseDTO(chapterName, chapter, index, content);
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
					String chapterName = quranFactory.getChapterName(version, chapterIndex);
					Map<Integer, String> versesInChapter = quranFactory.getVerses(version, chapterIndex);
					if(chapterIndex==throughChapter){
						if(!versesInChapter.keySet().contains(throughVerse)){
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
						}
						for(int verseIndex=1; verseIndex<=throughVerse; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							QuranVerseDTO dto = new QuranVerseDTO(chapterName, chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
					}
					else{
						int numberOfVerses = versesInChapter.size();
						for(int verseIndex=verse; verseIndex<=numberOfVerses; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							QuranVerseDTO dto = new QuranVerseDTO(chapterName, chapterIndex, verseIndex, content);
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
	public List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		try{
			String chapterName = quranFactory.getChapterName(version, chapter);
			List<Integer> chapterList = quranFactory.getChapters(version);
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			Map<Integer, String> versesInChapter = quranFactory.getVerses(version, chapter);
			
			for(int verse: versesInChapter.keySet()){
				String content = versesInChapter.get(verse);
				dtos.add(new QuranVerseDTO(chapterName, chapter, verse, content));
			}
			
		}catch(FactoryException e){
			throw new ServiceException("Factory error", e);
		}
		return dtos;
	}

}
