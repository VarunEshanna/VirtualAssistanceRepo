package com.adobe.assistance.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.adobe.assistance.collections.EntityPathsCollection;

@Repository
public interface EntityPathsRepository extends MongoRepository<EntityPathsCollection, String>{

	public List<EntityPathsCollection> findByInputData(String inputData);

}
