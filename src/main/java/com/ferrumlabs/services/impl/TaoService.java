package com.ferrumlabs.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrumlabs.dto.TaoVerseDTO;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.factories.TaoFactory;
import com.ferrumlabs.services.interfaces.ITaoService;
import com.ferrumlabs.utils.ErrorCodes;

@Service("TaoService")
public class TaoService implements ITaoService {

	@Autowired
	TaoFactory taoFactory;
	
	@Override
	public List<TaoVerseDTO> getVersesInRange(Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException {
		if(chapter == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null");
		}
		if(verse == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Verse cannot be null");
		}
		if(chapter == throughChapter){
			throughChapter = null;
		}
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		try{
			
			List<Integer> chapterList = taoFactory.getChapterList();
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			
			if((throughChapter == null) && (throughVerse == null)){
				String singleVerseContent = taoFactory.getVerse(chapter, verse);
				TaoVerseDTO singleDTO = new TaoVerseDTO(chapter, verse, singleVerseContent);
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
				Map<Integer, String> versesInChapter = taoFactory.getChapter(chapter);
				if(!versesInChapter.keySet().contains(throughVerse)){
					throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
				}
				for(int index=verse; index<=throughVerse; index++){
					String content = versesInChapter.get(index);
					TaoVerseDTO dto = new TaoVerseDTO(chapter, index, content);
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
					Map<Integer, String> versesInChapter = taoFactory.getChapter(chapterIndex);
					if(chapterIndex==throughChapter){
						if(!versesInChapter.keySet().contains(throughVerse)){
							throw new ServiceException(ErrorCodes.INVALID_INPUT, "End verse is not within chapter");
						}
						for(int verseIndex=1; verseIndex<=throughVerse; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							TaoVerseDTO dto = new TaoVerseDTO(chapterIndex, verseIndex, content);
							dtos.add(dto);
						}
					}
					else{
						int numberOfVerses = versesInChapter.size();
						for(int verseIndex=verse; verseIndex<=numberOfVerses; verseIndex++){
							String content = versesInChapter.get(verseIndex);
							TaoVerseDTO dto = new TaoVerseDTO(chapterIndex, verseIndex, content);
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
	public List<TaoVerseDTO> getVersesInChapter(int chapter) throws ServiceException {
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		try{	
			List<Integer> chapterList = taoFactory.getChapterList();
			if(!chapterList.contains(chapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			Map<Integer, String> versesInChapter = taoFactory.getChapter(chapter);
			
			for(int verse: versesInChapter.keySet()){
				String content = versesInChapter.get(verse);
				dtos.add(new TaoVerseDTO(chapter, verse, content));
			}
			
		}catch(FactoryException e){
			throw new ServiceException("Factory error", e);
		}
		return dtos;
	}

}
