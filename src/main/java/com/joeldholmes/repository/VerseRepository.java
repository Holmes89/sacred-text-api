package com.joeldholmes.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.joeldholmes.entity.VerseEntity;

@Component
public interface VerseRepository extends Repository<VerseEntity, String>{
	
	 @Query("{'bool': { 'must': [ { 'match': { 'religiousText': ?0}},{ 'match': { 'book': ?1 } }, {'match': {'chapter': ?2} } , {'match': {'verse': ?3} } ] } } }")
	public List<VerseEntity> getVerse(String religiousText, String book, int chapter, int verse);

}
