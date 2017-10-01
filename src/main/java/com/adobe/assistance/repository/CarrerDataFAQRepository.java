package com.adobe.assistance.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.adobe.assistance.collections.CarrerDataDFAQCollection;

@Repository
public interface CarrerDataFAQRepository  extends MongoRepository<CarrerDataDFAQCollection, String>{
	
	public List<CarrerDataDFAQCollection> findByInputData(String inputData);

}
