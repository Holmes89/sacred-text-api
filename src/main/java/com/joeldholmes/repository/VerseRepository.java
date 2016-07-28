package com.joeldholmes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.joeldholmes.entity.VerseEntity;

@Repository
public interface VerseRepository extends MongoRepository<VerseEntity, String>{
	
}
