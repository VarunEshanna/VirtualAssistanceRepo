package com.adobe.assistance.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.adobe.assistance.beans.ResponseData;
import com.adobe.assistance.beans.UserData;

public class AccessRequest {
	
	public static String createCaseForAccess (ResponseData respData){
		String responseText = "Unable to get the user details. We have raised a case for the same";
		String userName = "Varun Eshanna";
		for(UserData userData : respData.getUserData()){
			if(userData.getName().equals("GET_USER")){
				userName = userData.getUserResponse();
			}
		}
		
		String accessToken = FetchDataFromApp.getAccessToken();
		
		String query = "Select Id from User where FullName__c ='"+userName+"'";
		HttpClient httpclient = HttpClientBuilder.create().build();

		URIBuilder builder = new URIBuilder();
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
		System.out.println(((JSONObject)datas.get(0)).get("Id"));
		String Id  = (String) ((JSONObject)datas.get(0)).get("Id");
		
		query = "select PermissionSet.Name from PermissionSetAssignment where Assignee.Id = '"+Id+"' order by PermissionSet.Name";
		httpclient = HttpClientBuilder.create().build();

		builder = new URIBuilder();
		builder.setScheme("https").setHost("adobe--sfdev.cs27.my.salesforce.com").setPath("/services/data/v39.0/query")
		.setParameter("q", query);
		uri1 = null;
		try {
			uri1 = builder.build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		get = new HttpGet(uri1);
		get.setHeader("Authorization", "Bearer " + accessToken);
		resp = null;
		try {
			resp = httpclient.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}

		json = null;
		entity1 = resp.getEntity();
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
		datas = (JSONArray) json.get("records");
		System.out.println(datas);
		
		List<String> permissionSets = new ArrayList<String>();
		for(int i = 0; i < datas.size(); i++)
		{
		      JSONObject objects = (JSONObject) datas.get(i);
		      JSONObject ps = (JSONObject) objects.get("PermissionSet");
		      String name = (String) ps.get("Name");
		      permissionSets.add(name);
		}
		
		List<String> requiredPermissionSets = new ArrayList<String>();
		requiredPermissionSets.add("ECM_Permission_Set");
		requiredPermissionSets.add("ECM_Permission_Set_VL");
		
		List<String> cloneRequiredPermissionSet = new ArrayList<String>();
		cloneRequiredPermissionSet.add("ECM_Permission_Set");
		cloneRequiredPermissionSet.add("ECM_Permission_Set_VL");
		
		for(String userPermissionSet : permissionSets){
			for(String requiredPermissionSet : requiredPermissionSets){
				if(requiredPermissionSet.equals(userPermissionSet)){
					cloneRequiredPermissionSet.remove(requiredPermissionSet);
				}
			}
		}
		
		String caseNumber  = CreateCase.createCase(respData);
		responseText = "To create Sales agreement user needs " + requiredPermissionSets + " permission sets.";
		if(cloneRequiredPermissionSet.size() > 0){
			responseText += "However for the user "+userName+" following permission set is missing " +cloneRequiredPermissionSet;
		}else{
			responseText += "Looks like for the user "+userName+ " all the permission sets needed are present.";
		}
		if(!caseNumber.equals("")){
			responseText += "We have created a case "+caseNumber+" for the same. Backend team will verify and contact you if needed";
		}
		return responseText;

		
	}
	


	
	
	
	
}
