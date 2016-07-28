package com.joeldholmes.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories
class MongoDbConfig extends AbstractMongoConfiguration {
	
	@Value("${db.name:religious_texts}")
	String database;
	
	@Value("${db.host:localhost}")
	String host;
	
	@Value("${db.port:27017}")
	Integer port;
	
	@Value("${db.username:''}")
	String userName;

	@Value("${db.password:''}")
	String password;
		
	@Override
	public Mongo mongo() throws Exception {
		System.out.println(database);
		MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
		MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
		return mongoClient;
	}
	
	@Override
	protected String getDatabaseName() {
		return database;
	}
	
	@Override
	public MongoTemplate mongoTemplate() throws Exception{
		return new MongoTemplate(mongo(), database);
	}
}