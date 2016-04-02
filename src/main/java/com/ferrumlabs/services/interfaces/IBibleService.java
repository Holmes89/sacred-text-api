package com.ferrumlabs.services.interfaces;

import java.util.List;

import com.ferrumlabs.dto.BibleVerseDTO;
import com.ferrumlabs.enums.BibleVersionEnum;
import com.ferrumlabs.exceptions.ServiceException;

public interface IBibleService {


	List<BibleVerseDTO> getVersesInRange(BibleVersionEnum version, String book, Integer chapter, Integer verse,	Integer throughChapter, Integer throughVerse) throws ServiceException;

}
