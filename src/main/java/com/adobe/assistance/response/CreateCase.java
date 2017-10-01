package com.adobe.assistance.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.adobe.assistance.beans.ResponseData;
import com.mongodb.util.JSON;

public class CreateCase {

	public static String createCase(ResponseData respData){
		String responseText = "";
		try{

			String accessToken = FetchDataFromApp.getAccessToken();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			headers.add("Content-Type", "application/json");

			Map<String, String> body = new HashMap<String, String>();     
			body.put("Subject", "Create Case from Virtual Assistance - "+respData.getIntentName());
			body.put("Business_Area__c", "Adobe Business Direct");
			body.put("General_Application_area__c", "Opportunity Management");
			body.put("Sub_Functional_Area__c", "Account");
			body.put("Geo__c", "All");
			body.put("Support_Region__c", "All");
			body.put("Description", "Hi, \n"+respData.getUserQuery());
			body.put("Status", "1-Submitted");
			
			String jsonData = JSON.serialize(body);
			
			HttpEntity<?> entity = new HttpEntity<Object>(jsonData, headers);
			
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("adobe--sfdev.cs27.my.salesforce.com").setPath("/services/data/v39.0/sobjects/Case");
			URI uri = null;
			try {
				uri = builder.build();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);
			LinkedHashMap<String,String> s1 = (LinkedHashMap<String, String>) response.getBody();
			String id  = s1.get("id");
			
			
			
			String query = "SELECT CaseNumber FROM Case where Id ='"+id+"'";
			HttpClient httpclient = HttpClientBuilder.create().build();

			builder = new URIBuilder();
			builder.setScheme("https").setHost("adobe--sfdev.cs27.my.salesforce.com").setPath("/services/data/v39.0/query")
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
			String caseNumber  = (String) ((JSONObject)datas.get(0)).get("CaseNumber");
			System.out.println("After query: "+ caseNumber);
			responseText = caseNumber;
			
			
			/*
			
			HttpClient httpclient = HttpClientBuilder.create().build();



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
			String status = (String) ((JSONObject)datas.get(0)).get("Apttus__Status_Category__c");

			if(status.equals("In Signatures") && statusCategory.equals("Ready For Singature")){
				responseText = "Send for signature button should be enabled. Can you please check again?";
			}else{
				responseText = "For Send for Signature button to be enabled, the status and status category of the agreement should be Ready For Signatures and In Signatures respectively.\nHowever for the "
						+ "mentioned the agreement the status is "+status+ " and status category is "+statusCategory;
			}*/

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return responseText;
	}
}