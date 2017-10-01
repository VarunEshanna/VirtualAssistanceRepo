package com.adobe.assistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.adobe.assistance.collections.EntityRecordCollection;

@Repository
public interface EntityRecordRepository extends MongoRepository<EntityRecordCollection, String>{
	
	public EntityRecordCollection findByName(String name);
	
	 @Query("{'name' : ?0}")
	 public EntityRecordCollection findEntityRecordCollectionByName(String name);

}

