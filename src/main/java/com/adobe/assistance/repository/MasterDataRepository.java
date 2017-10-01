package com.adobe.assistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.adobe.assistance.collections.MasterDataCollection;

@Repository
public interface MasterDataRepository  extends MongoRepository<MasterDataCollection, String>{

}
