package com.adobe.assistance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adobe.assistance.beans.ResponseData;
import com.adobe.assistance.beans.UserData;
import com.adobe.assistance.collections.EntityPathsCollection;
import com.adobe.assistance.collections.EntityRecordCollection;
import com.adobe.assistance.controller.EntityPathsController;
import com.adobe.assistance.controller.EntityRecordController;
import com.adobe.assistance.mappers.EntityRecordCollectionUserDataMapper;

@Service
public class IntentService {
	
	@Autowired
	EntityPathsController entityPathsController;
	
	@Autowired
	EntityRecordController entityController;
	
	public ResponseData updateUserData(ResponseData responseData) {
		EntityPathsCollection entityPathsCollection = new EntityPathsCollection();
		entityPathsCollection.setInputData(responseData.getIntentName());
		List<EntityPathsCollection> entityPathsCollectionList = entityPathsController.updateGetData(entityPathsCollection);
		System.out.println(entityPathsCollectionList);
		
		List<String> outputDataList = new ArrayList<String>();
		for(EntityPathsCollection entityPathsRecord : entityPathsCollectionList){
			outputDataList.add(entityPathsRecord.getOutputData());
		}
		
		List<EntityRecordCollection> eclist = entityController.getListData(outputDataList);
		List<UserData> userDatalist = EntityRecordCollectionUserDataMapper.mapListEntityRecordToUserData(eclist);
		responseData.setUserData(userDatalist);
		
		return responseData;
	}

	public ResponseData getLUISData(ResponseData responseData) {
		String query = responseData.getUserQuery();
		//final String uri = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/1dc5771c-1761-4d86-ab36-b8730079ed09?subscription-key=77d788a631524be487b54f39a9362f13&verbose=true&timezoneOffset=0&q="+query  ;
		//final String uri = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/6db5dbbc-f6f4-43d9-9043-44901cf5ad2a?subscription-key=5d73575e2b4e4a7cb5dd9408bce7a8ea&verbose=true&timezoneOffset=0&q="+query  ;
		final String uri ="https://southeastasia.api.cognitive.microsoft.com/luis/v2.0/apps/1dc5771c-1761-4d86-ab36-b8730079ed09?subscription-key=c7c5b51b989741a69a506a45218fbb5e&timezoneOffset=0&verbose=true&q="+query;


		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);

		Map<String,String> inputData = new HashMap<String,String>();

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(json != null){
			JSONObject topScoringIntent = (JSONObject)json.get("topScoringIntent");
			System.out.println(topScoringIntent);
			if((Double)topScoringIntent.get("score") > 0.20){
				responseData.setIntentName((String)topScoringIntent.get("intent"));
			}
			responseData.setLuisCallRequired(false);
			responseData.setFinalResponse(false);

			JSONArray entities = (JSONArray) json.get("entities");
			System.out.println(entities);
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = entities.iterator();
			while (iterator.hasNext()) {
				JSONObject entity = iterator.next();
				inputData.put((String)entity.get("type"),(String) entity.get("entity"));
				UserData userData = new UserData();
				userData.setAppMessage((String) entity.get("entity"));
				userData.setName((String)entity.get("type"));
				responseData.getUserData().add(userData);
			}
		}

		System.out.println(inputData);
		return responseData;
	}

}
