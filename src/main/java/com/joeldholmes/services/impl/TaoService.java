package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.dto.TaoVerseDTO;
import com.joeldholmes.entity.VerseEntity;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.repository.IVerseRepository;
import com.joeldholmes.services.interfaces.IReligiousTextIndexService;
import com.joeldholmes.services.interfaces.ITaoService;
import com.joeldholmes.utils.ErrorCodes;

@Service("TaoService")
public class TaoService implements ITaoService {

	@Autowired
	IVerseRepository verseRepository;

	@Autowired
	IReligiousTextIndexService indexService;

	private final int MAX_CHAPTER_SIZE=81;

	@Override
	public List<TaoVerseDTO> getVerses(Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException {
		if(chapter == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null");
		}
		if(chapter == throughChapter){
			throughChapter = null;
		}
		if((throughChapter!=null) && (chapter > throughChapter)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start Chapter cannot be less than end chapter");
		}


		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();



		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist");
		}

		if((chapter != null) && (verse == null) && (throughChapter == null) && (throughVerse == null)){
			return getVersesInChapter(chapter);
		}
		else if((chapter != null) && (verse == null) && (throughChapter != null) && (throughVerse == null)){

			List<VerseEntity> versesInChapter = verseRepository.getTaoVersesInChapterRange(chapter, throughChapter);
			dtos.addAll(convertEntitiesToDTOs(versesInChapter));
			return dtos;
		}
		else if((throughChapter == null) && (throughVerse == null)){

			TaoVerseDTO singleDTO = getSingleVerse(chapter, verse);
			dtos.add(singleDTO);
			return dtos;
		}
		else if((throughChapter == null) && (throughVerse != null)){
			if(throughVerse<verse){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start verse cannot be less than end verse");
			}
			int maxVerseSize = indexService.maxTaoChapterVerses(chapter);

			if((verse < 1) || (verse > maxVerseSize)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}

			if((throughVerse < 1) || (throughVerse > maxVerseSize)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}

			List<VerseEntity> versesInChapter = verseRepository.getTaoVersesInChapter(chapter, verse, throughVerse);

			dtos.addAll(convertEntitiesToDTOs(versesInChapter));
			return dtos;
		}
		else if((throughChapter != null) && (throughVerse != null)){
			if(verse==null){
				verse=1;
			}

			if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}
			if((throughChapter < 1) || (throughChapter > MAX_CHAPTER_SIZE)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
			}

			int versesInChapter = indexService.maxTaoChapterVerses(chapter);
			if((verse < 1) || (verse > versesInChapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}

			int versesInThroughChapter = indexService.maxTaoChapterVerses(chapter);
			if((throughVerse < 1) || (throughVerse > versesInThroughChapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}
			//First Verse Set
			List<VerseEntity> entities = verseRepository.getTaoVersesInChapter(chapter, verse, versesInChapter);

			//End Verse Set
			entities.addAll(verseRepository.getTaoVersesInChapter(throughChapter, 1, throughVerse));

			if(throughChapter-chapter>1){
				entities.addAll(verseRepository.getTaoVersesInChapterRange(chapter+1, throughChapter-1));
			}
			dtos.addAll(convertEntitiesToDTOs(entities));
		}
		return dtos;
	}

	@Override
	public List<TaoVerseDTO> getVersesInChapter(int chapter) throws ServiceException {
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}
		List<VerseEntity> verses = verseRepository.getTaoVersesInChapter(chapter);
		return convertEntitiesToDTOs(verses);
	}

	@Override
	public TaoVerseDTO getSingleVerse(int chapter, int verse) throws ServiceException {
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}

		int maxVerseSize = indexService.maxTaoChapterVerses(chapter);

		if((verse < 1) || (verse > maxVerseSize)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
		}

		VerseEntity entity = verseRepository.getSingleTaoVerse( chapter, verse);

		return new TaoVerseDTO(entity);
	}

	@Override
	public List<TaoVerseDTO> getVersesFromString(String verses) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	private List<TaoVerseDTO> convertEntitiesToDTOs(List<VerseEntity> entities){
		List<TaoVerseDTO> dtos = new ArrayList<TaoVerseDTO>();
		for(VerseEntity verseEntity: entities){
			dtos.add(new TaoVerseDTO(verseEntity));
		}
		Collections.sort(dtos);
		return dtos;
	}

}
