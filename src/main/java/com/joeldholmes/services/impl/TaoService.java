package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.dto.VerseDTO;
import com.joeldholmes.dto.VerseDTO;
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
	public List<VerseDTO> getVerses(Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException {
		if(chapter == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter cannot be null");
		}
		if(chapter == throughChapter){
			throughChapter = null;
		}
		if((throughChapter!=null) && (chapter > throughChapter)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start Chapter cannot be less than end chapter");
		}


		List<VerseDTO> dtos = new ArrayList<VerseDTO>();



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

			VerseDTO singleDTO = getSingleVerse(chapter, verse);
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
	public List<VerseDTO> getVersesInChapter(int chapter) throws ServiceException {
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}
		List<VerseEntity> verses = verseRepository.getTaoVersesInChapter(chapter);
		return convertEntitiesToDTOs(verses);
	}

	@Override
	public VerseDTO getSingleVerse(int chapter, int verse) throws ServiceException {
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}

		int maxVerseSize = indexService.maxTaoChapterVerses(chapter);

		if((verse < 1) || (verse > maxVerseSize)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
		}

		VerseEntity entity = verseRepository.getSingleTaoVerse( chapter, verse);

		return new VerseDTO(entity);
	}

	@Override
	public List<VerseDTO> getVersesFromString(String verses) throws ServiceException {
		if(verses == null || verses.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Verse cannot be null or empty");
		}
		
		verses = verses.replaceAll("\\s+", " ");
		List<VerseDTO> verseList = new ArrayList<VerseDTO>();
		
		String chapterVerseRegex = "([\\d:\\s]+)-?([\\d:\\s]+)?";
		Pattern chapterVersePattern =Pattern.compile(chapterVerseRegex);

		Integer startChapter = null;
		Integer startVerse = null;
		Integer endChapter = null;
		Integer endVerse = null;
		
		String[] verseArray = verses.trim().split(",");
		for(String verse: verseArray){
			verse = verse.trim();
			if(verse.matches(chapterVerseRegex)){
				Matcher m = chapterVersePattern.matcher(verse);
				if(m.matches()){
					String chapterVerse = m.group(1);
					String throughChapterVerse = m.group(2);
					String[] cvSplit = chapterVerse.split(":");
					//Chapter declaration
					if(chapterVerse.contains(":")){
						startChapter=Integer.parseInt(cvSplit[0].trim());
						if(cvSplit.length==2){
							startVerse = Integer.parseInt(cvSplit[1].trim());
						}
						else{
							startVerse = null;
						}
						endVerse = null;
						endChapter = null;
					}
					else{
						if(startChapter==null){
							startChapter = Integer.parseInt(cvSplit[0].trim());
						}
						else{
							startVerse = Integer.parseInt(cvSplit[0].trim());
						}
						
					}
					if(throughChapterVerse!=null){
						cvSplit = throughChapterVerse.split(":");
						if(throughChapterVerse.contains(":")){
							endChapter=Integer.parseInt(cvSplit[0].trim());
							if(cvSplit.length==2){
								endVerse = Integer.parseInt(cvSplit[1].trim());
							}
							else{
								endVerse = null;
							}
						}
						else{
							endVerse = Integer.parseInt(cvSplit[0].trim());
						}
					}
				}
			}
			else{
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
			}
			//get verses here.
			verseList.addAll(getVerses(startChapter, startVerse, endChapter, endVerse));
			if(endChapter!=null)
				startChapter = endChapter;
			if(endVerse!=null)
				startVerse = endVerse;
			endChapter = null;
			endVerse = null;
			
		}
		
		return verseList;
	}

	private List<VerseDTO> convertEntitiesToDTOs(List<VerseEntity> entities){
		List<VerseDTO> dtos = new ArrayList<VerseDTO>();
		for(VerseEntity verseEntity: entities){
			dtos.add(new VerseDTO(verseEntity));
		}
		Collections.sort(dtos);
		return dtos;
	}

}
