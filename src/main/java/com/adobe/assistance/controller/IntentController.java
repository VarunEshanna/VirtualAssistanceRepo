package com.adobe.assistance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.adobe.assistance.beans.ResponseData;
import com.adobe.assistance.beans.UserData;
import com.adobe.assistance.collections.CarrerDataDFAQCollection;
import com.adobe.assistance.repository.CarrerDataFAQRepository;
import com.adobe.assistance.service.IntentService;
import com.adobe.assistance.response.AccessRequest;
import com.adobe.assistance.response.FAQService;
import com.adobe.assistance.response.FetchDataFromApp;

@RestController
public class IntentController {
	
	@Autowired
	IntentService intentService;

	@Autowired
	CarrerDataFAQRepository carrerDataFAQRepository;
	
	@CrossOrigin	
	@RequestMapping(value = "/getLuisData", method = RequestMethod.POST)
	public ResponseData getLuisData(@RequestBody ResponseData responseData){
		if(responseData.getLuisCallRequired()){
			responseData = intentService.getLUISData(responseData);
			
			if(responseData.getIntentName().equalsIgnoreCase("NONE")){
				responseData.setFinalResponseText("Unable to understand your query. Please try again");
				return responseData;
			}
			
			Map<String,String> testMap = new HashMap<String,String>();
			for(UserData userData : responseData.getUserData()){
				testMap.put(userData.getName(), userData.getAppMessage());
			}

			responseData = intentService.updateUserData(responseData);
			
			for(UserData userData : responseData.getUserData()){
				for(String s :testMap.keySet()){
					if(s.equals(userData.getName())){
						userData.setUserResponse(testMap.get(s));
					}
				}
			}
		}
		return responseData;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getRespData", method = RequestMethod.POST)
	public ResponseData getResponseData(@RequestBody ResponseData responseData){
		boolean finalResponse = true;
		for(UserData userData : responseData.getUserData()){
			if(userData.getUserResponse() == null && userData.getUserResponse().equals("") && userData.getType().equals("ENTITY")){
				finalResponse = false;
			}
		}
		
		if(finalResponse){
			responseData.setFinalResponse(true);
			String finalResponseText = null;
			
			String agmtType = null;
			String agmtId = null;

			for(UserData userData : responseData.getUserData()){

				if(userData.getName().equals("GET_AGREEMENT_TYPE")){
					agmtType = userData.getUserResponse();
				}
				if(userData.getName().equals("GET_AGREEMENTS")){
					agmtId = userData.getUserResponse();
				}
			}

			for(UserData userData : responseData.getUserData()){
				if(userData.getType().startsWith("RESPONSE")){
					if(userData.getName().equals("CREATE_CASE")){
						finalResponseText = AccessRequest.createCaseForAccess(responseData);
						//finalResponseText = CreateCase.createCase(responseData);
					}else if(userData.getName().equals("GET_HELP_TEXT")){
						finalResponseText = FAQService.getAgmtLinks(agmtType);
					}else if(userData.getName().equals("NOTIFY_USER")){
						finalResponseText =  FetchDataFromApp.getData(agmtId);
					}
				}
			}
			responseData.setFinalResponseText(finalResponseText);
		}
		return responseData;
		
	}
	
	
	@CrossOrigin	
	@RequestMapping(value = "/getCareerData", method = RequestMethod.POST)
	public ResponseData getCareerData(@RequestBody ResponseData responseData){
		if(responseData.getLuisCallRequired()){
			responseData = intentService.getLUISData(responseData);
			System.out.println(responseData);
			if(responseData.getIntentName() != null){
				List<CarrerDataDFAQCollection> list = carrerDataFAQRepository.findByInputData(responseData.getIntentName());
				if(list.size() == 1){
					CarrerDataDFAQCollection c = list.get(0);
					System.out.println(c.getOutputData());
					responseData.setFinalResponseText(c.getOutputData());
				}
			}
			
		}
		return responseData;
	}

}
