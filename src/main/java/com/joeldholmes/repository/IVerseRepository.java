package com.joeldholmes.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.joeldholmes.entity.VerseEntity;

@Repository
public interface IVerseRepository extends MongoRepository<VerseEntity, String>{
	
	static final Logger logger = Logger.getLogger(IVerseRepository.class);
	
	//Bible
	@Query("{\"religiousText\": \"bible\",\"version\": ?0, \"book\": ?1,\"chapter\": ?2, \"verse\": ?3 }")
	VerseEntity getSingleBibleVerse(String version, String book, int chapter, int verse);
	
	@Query("{\"religiousText\": \"bible\",\"version\": ?0, \"book\": ?1,\"chapter\": ?2, \"verse\": { $lte:?4, $gte:?3 }}")
	List<VerseEntity> getBibleVersesInChapter(String version, String book, int chapter, int startVerse, int endVerse);	
	
	@Query("{\"religiousText\": \"bible\",\"version\": ?0, \"book\": ?1,\"chapter\": ?2}")
	List<VerseEntity> getBibleVersesInChapter(String version, String book, int chapter);	
	
	@Query("{\"religiousText\": \"bible\",\"version\": ?0, \"book\": ?1,\"chapter\": { $lte:?3, $gte:?2 }}")
	List<VerseEntity> getBibleVersesInChapterRange(String version, String book, int startChapter, int endChapter);	

	//Quran
	@Query("{\"religiousText\": \"quran\",\"version\": ?0, \"chapter\": ?1, \"verse\": ?2 }")
	VerseEntity getSingleQuranVerse(String version,  int chapter, int verse);
	
	@Query("{\"religiousText\": \"quran\",\"version\": ?0, \"chapter\": ?1, \"verse\": { $lte:?3, $gte:?2 }}")
	List<VerseEntity> getQuranVersesInChapter(String version,  int chapter, int startVerse, int endVerse);	
	
	@Query("{\"religiousText\": \"quran\",\"version\": ?0,\"chapter\": ?1}")
	List<VerseEntity> getQuranVersesInChapter(String version,  int chapter);	
	
	@Query("{\"religiousText\": \"quran\",\"version\": ?0, \"chapter\": { $lte:?2, $gte:?1 }}")
	List<VerseEntity> getQuranVersesInChapterRange(String version, int startChapter, int endChapter);	
	
	//Tao
	@Query("{\"religiousText\": \"tao-te-ching\", \"chapter\": ?0, \"verse\": ?1 }")
	VerseEntity getSingleTaoVerse(int chapter, int verse);
	
	@Query("{\"religiousText\": \"tao-te-ching\", \"chapter\": ?0, \"verse\": { $lte:?2, $gte:?1 }}")
	List<VerseEntity> getTaoVersesInChapter(int chapter, int startVerse, int endVerse);	
	
	@Query("{\"religiousText\": \"tao-te-ching\",\"chapter\": ?0}")
	List<VerseEntity> getTaoVersesInChapter(int chapter);	
	
	@Query("{\"religiousText\": \"tao-te-ching\", \"chapter\": { $lte:?1, $gte:?0 }}")
	List<VerseEntity> getTaoVersesInChapterRange(int startChapter, int endChapter);	
	
	
}
