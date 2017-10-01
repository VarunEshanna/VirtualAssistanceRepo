package com.adobe.assistance.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FetchDataFromApp {
	
	public static String getData(String agmtId){
		String responseText = "Unable to retrive information for the agreement "+ agmtId +". Please contact customer care for quicker resolution";
		try{
		
		String accessToken = getAccessToken();
		
		String query = "Select Apttus__Status__c, Apttus__Status_Category__c, Owner.Name, Owner.Email From Apttus__APTS_Agreement__c where Id = '"+agmtId+"'";
		HttpClient httpclient = HttpClientBuilder.create().build();

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("adobe--sfdev.cs27.my.salesforce.com").setPath("/services/data/v39.0/query")
		//.setParameter("q", "SELECT ID, Body FROM ApexClassMember Where FullName='ECMAgreementHelper'");
		.setParameter("q", query);
		URI uri1 = null;
		try {
			uri1 = builder.build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		HttpGet get = new HttpGet(uri1);
		get.setHeader("Authorization", "Bearer " + accessToken);
		HttpResponse resp = null;
		try {
			resp = httpclient.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject json = null;
		org.apache.http.HttpEntity entity1 = resp.getEntity();
		if (entity1 != null) {
			InputStream instream = null;
			try {
				instream = entity1.getContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				json = (JSONObject) JSONValue.parse(new InputStreamReader(instream));
			} finally {
			}
		}
		JSONArray datas = (JSONArray) json.get("records");

		System.out.println(((JSONObject)datas.get(0)).get("Apttus__Status_Category__c"));
		System.out.println(((JSONObject)datas.get(0)).get("Apttus__Status__c"));
		
		String statusCategory = (String) ((JSONObject)datas.get(0)).get("Apttus__Status_Category__c");
		String status = (String) ((JSONObject)datas.get(0)).get("Apttus__Status__c");
		JSONObject ownerRecord = (JSONObject) ((JSONObject)datas.get(0)).get("Owner");
		String owner = (String) ownerRecord.get("Name");
		String email = (String) ownerRecord.get("Email");
		
		if(statusCategory.equals("In Signatures") && status.equals("Ready for Signatures")){
			responseText = "Send for signature button should be enabled as the status is Ready for Signature and Status category is In Signatures. Please contact the Agreement owner "+ owner +" for more information.";
			if(email != null){
				responseText += " Email of agreement owner is "+email;
			}
		}else{
			responseText = "For Send for Signature button to be enabled, the status and status category of the agreement should be Ready For Signatures and In Signatures respectively. However for the "
					+ "mentioned the agreement the status is "+status+ " and status category is "+statusCategory;
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return responseText;
	}

	
	public static String getAccessToken() {
		String clientId = "3MVG973QtA4.tpvlw7_.HNNhbulJFI2oXMebp03cY8rJpxYZppjVCzZ0HGROsOuCbvRDkzD5mGlAC11RYh1oB";
		String clientSecret = "6155762148310014609";
		String environment = "https://test.salesforce.com";
		String username = "eshanna@adobe.com.sfdev";
		String password = "Welcome4DFcBbG73GeuNleRbFdw8Zi68VX";
		
		String authUrl = environment+ "/services/oauth2/token?grant_type=password&client_id="+clientId+"&client_secret="+clientSecret+"&username="+username+"&password="+password;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(authUrl, HttpMethod.POST, null, Object.class);
		
		@SuppressWarnings("unchecked")
		Map<String,String> map =(LinkedHashMap<String,String>)response.getBody(); 
		String accessToken = (String) map.get("access_token");
		return accessToken;
	}

}
