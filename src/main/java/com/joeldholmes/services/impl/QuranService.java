package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.dto.BibleVerseDTO;
import com.joeldholmes.dto.QuranVerseDTO;
import com.joeldholmes.entity.VerseEntity;
import com.joeldholmes.enums.QuranVersionEnum;
import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.repository.IVerseRepository;
import com.joeldholmes.services.interfaces.IQuranService;
import com.joeldholmes.services.interfaces.IReligiousTextIndexService;
import com.joeldholmes.utils.ErrorCodes;

@Service("QuranService")
public class QuranService implements IQuranService {

	@Autowired
	IVerseRepository verseRepository;
	
	@Autowired
	IReligiousTextIndexService indexService;
	
	private final int MAX_CHAPTER_SIZE = 114;
		
	@Override
	public List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, int chapter) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}
		List<VerseEntity> verses = verseRepository.getQuranVersesInChapter(version.getName(), chapter);
		return convertEntitiesToDTOs(verses);
	}
	
	@Override
	public List<QuranVerseDTO> getVersesInChapter(QuranVersionEnum version, String chapterName) throws ServiceException {
		if(chapterName==null || chapterName.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter Name cannot be null");
		}
		int chapter = indexService.quranChapterNameLookup(chapterName);
		return getVersesInChapter(version, chapter);
	}

	@Override
	public List<QuranVerseDTO> getVersesInChapter(int chapter) throws ServiceException {
		return getVersesInChapter(QuranVersionEnum.SHAKIR, chapter);
	}

	@Override
	public List<QuranVerseDTO> getVersesInChapter(String chapterName) throws ServiceException {
		return getVersesInChapter(QuranVersionEnum.SHAKIR, chapterName);
	}

	@Override
	public List<QuranVerseDTO> getVersesFromString(String verses) throws ServiceException {
		return getVersesFromString(QuranVersionEnum.SHAKIR, verses);
	}

	@Override
	public List<QuranVerseDTO> getVersesFromString(QuranVersionEnum version, String verses) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		if(verses == null || verses.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Verse cannot be null or empty");
		}
		
		verses = sanitizeVerseString(verses);
		
		List<QuranVerseDTO> verseList = new ArrayList<QuranVerseDTO>();
		
		String chapterVerseRegex = "([\\d:]+)-?([\\d:]+)?";
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
					String[] nextCv = m.group(1).split(":");
				
					if(nextCv.length==1){
						startChapter = Integer.parseInt(nextCv[0]);
					}
					else if(nextCv.length==2){
						startChapter = Integer.parseInt(nextCv[0]);
						startVerse = Integer.parseInt(nextCv[1]);
					}
					else{
						throw new ServiceException(ErrorCodes.INVALID_INPUT, "Improperly formatted verse request");
					}
				
					if(m.group(2)!=null){
						nextCv = m.group(2).split(":");
						if(startChapter!=null && startVerse!=null){
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
						else if(startChapter!=null){
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
						else if(startVerse!=null){
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
			verseList.addAll(getVerses(version, startChapter, startVerse, endChapter, endVerse));
			if(endChapter!=null)
				startChapter = endChapter;
			if(endVerse!=null)
				startVerse = endVerse;
			endChapter = null;
			endVerse = null;
			
		}
		
		return verseList;
	}

	@Override
	public List<QuranVerseDTO> getVerses(QuranVersionEnum version, Integer chapter, Integer verse, Integer throughChapter, Integer throughVerse) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
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
		
		
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
	
		
		
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist");
		}
		
		if((chapter != null) && (verse == null) && (throughChapter == null) && (throughVerse == null)){
			return getVersesInChapter(version, chapter);
		}
		else if((chapter != null) && (verse == null) && (throughChapter != null) && (throughVerse == null)){
			
			List<VerseEntity> versesInChapter = verseRepository.getQuranVersesInChapterRange(version.getName(), chapter, throughChapter);
			dtos.addAll(convertEntitiesToDTOs(versesInChapter));
			return dtos;
		}
		else if((throughChapter == null) && (throughVerse == null)){
			
			QuranVerseDTO singleDTO = getSingleVerse(version, chapter, verse);
			dtos.add(singleDTO);
			return dtos;
		}
		else if((throughChapter == null) && (throughVerse != null)){
			if(throughVerse<verse){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Start verse cannot be less than end verse");
			}
			int maxVerseSize = indexService.maxQuranChapterVerses(chapter);
			
			if((verse < 1) || (verse > maxVerseSize)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}
			
			if((throughVerse < 1) || (throughVerse > maxVerseSize)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}
			
			List<VerseEntity> versesInChapter = verseRepository.getQuranVersesInChapter(version.getName(), chapter, verse, throughVerse);
			
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
			
			int versesInChapter = indexService.maxQuranChapterVerses(chapter);
			if((verse < 1) || (verse > versesInChapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}
			
			int versesInThroughChapter = indexService.maxQuranChapterVerses(chapter);
			if((throughVerse < 1) || (throughVerse > versesInThroughChapter)){
				throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
			}
			//First Verse Set
			List<VerseEntity> entities = verseRepository.getQuranVersesInChapter(version.getName(), chapter, verse, versesInChapter);
			
			//End Verse Set
			 entities.addAll(verseRepository.getQuranVersesInChapter(version.getName(), throughChapter, 1, throughVerse));
			 
			 if(throughChapter-chapter>1){
				 entities.addAll(verseRepository.getQuranVersesInChapterRange(version.getName(), chapter+1, throughChapter-1));
			 }
			 dtos.addAll(convertEntitiesToDTOs(entities));
		}
		return dtos;
	}
	
	@Override
	public List<QuranVerseDTO> getVerses(QuranVersionEnum version, String chapterName, Integer verse, String throughChapterName, Integer throughVerse) throws ServiceException {
		int chapter = indexService.quranChapterNameLookup(chapterName);
		int throughChapter = indexService.quranChapterNameLookup(throughChapterName);
		return getVerses(version, chapter, verse, throughChapter, throughVerse);
	}

	@Override
	public QuranVerseDTO getSingleVerse(QuranVersionEnum version, int chapter, int verse) throws ServiceException {
		if(version == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Version cannot be null");
		}
		
		
		if((chapter < 1) || (chapter > MAX_CHAPTER_SIZE)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not exist in book");
		}
		
		int maxVerseSize = indexService.maxQuranChapterVerses(chapter);
		
		if((verse < 1) || (verse > maxVerseSize)){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Chapter does not contain verse");
		}
		
		VerseEntity entity = verseRepository.getSingleQuranVerse(version.getName(),  chapter, verse);
		
		return new QuranVerseDTO(entity);
		
	}

	@Override
	public QuranVerseDTO getSingleVerse(QuranVersionEnum version, String chapterName, int verse) throws ServiceException {
			if(chapterName==null || chapterName.isEmpty()){
				throw new ServiceException(ErrorCodes.NULL_INPUT, "Chapter Name cannot be null");
			}
			
			int chapter = indexService.quranChapterNameLookup(chapterName);
			return getSingleVerse(version, chapter, verse);
	}
	
	@Override
	public QuranVerseDTO getSingleVerse(int chapter, int verse) throws ServiceException{
		return getSingleVerse(QuranVersionEnum.SHAKIR, chapter ,verse);
	}
	
	@Override
	public QuranVerseDTO getSingleVerse(String chapterName, int verse) throws ServiceException{
		return getSingleVerse(QuranVersionEnum.SHAKIR, chapterName ,verse);
	}

	private List<QuranVerseDTO> convertEntitiesToDTOs(List<VerseEntity> entities){
		List<QuranVerseDTO> dtos = new ArrayList<QuranVerseDTO>();
		for(VerseEntity verseEntity: entities){
			dtos.add(new QuranVerseDTO(verseEntity));
		}
		Collections.sort(dtos);
		return dtos;
	}
	
	private String sanitizeVerseString(String verse) throws ServiceException{
		verse = verse.replaceAll("\\s+", " ");
		String bookRegex = "([A-z\\s]+)";
		Pattern bookRegexPattern = Pattern.compile(bookRegex);
		String newVerse = verse;
		Matcher m = bookRegexPattern.matcher(verse);
		while(m.find()){
			String book = m.group(1).trim();
			if(book==null || book.isEmpty())
				continue;
			int chapter = indexService.quranChapterNameLookup(book.toLowerCase());
			newVerse = newVerse.replace(book+" ", chapter+":");
			newVerse = newVerse.replace(book, chapter+"");
		}
		verse = newVerse;
		return verse;
	}
}
