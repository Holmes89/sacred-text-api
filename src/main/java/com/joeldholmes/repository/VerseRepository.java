package com.joeldholmes.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.joeldholmes.entity.VerseEntity;

@Repository
public interface VerseRepository extends MongoRepository<VerseEntity, String>{
	
	static final Logger logger = Logger.getLogger(VerseRepository.class);
	
	@Query("{\"religiousText\": \"bible\",\"version\": \"niv\", \"book\": ?0,\"chapter\": ?1, \"verse\": ?2 }")
	VerseEntity getSingleBibleVerse(String book, int chapter, int verse);
}
