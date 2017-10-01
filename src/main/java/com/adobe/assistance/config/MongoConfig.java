package com.adobe.assistance.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.adobe.assistance.repository.EntityPathsRepository;
import com.adobe.assistance.repository.EntityRecordRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackageClasses = {EntityRecordRepository.class, EntityPathsRepository.class})
public class MongoConfig
{
	@Value("virtualassistance")
	private String  databaseName;

	@Value("ds151433.mlab.com")
	private String  mongoHost;

	@Value("51433")
	private Integer mongoPort;

	@Value("varunreadonly")
	private String  mongoUser;

	@Value("123456")
	private String  mongoPass;

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

	    // Set credentials      
	    MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);

	    // Mongo Client
	    MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 

	    // Mongo DB Factory
	    SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, databaseName);

	    return simpleMongoDbFactory;
	}

	/**
	 * Template ready to use to operate on the database
	 * 
	 * @return Mongo Template ready to use
	 */
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	    return new MongoTemplate(mongoDbFactory());
	}
}