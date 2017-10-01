package com.adobe.assistance.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="EntityRecords")
public class EntityRecordCollection {
	
	@Id
	private String Id;
	@Field(value="NAME")
	private String name;
	@Field(value="TYPE")
	private String type;
	@Field(value="APP_MESSAGE")
	private String appMessage;
	@Field(value="USER_RESPONSE")
	private String userResponse;
	
	public EntityRecordCollection(){
		super();
	}
	
	public EntityRecordCollection(String name, String type, String appMessage, String userResponse) {
		super();
		this.name = name;
		this.type = type;
		this.appMessage = appMessage;
		this.userResponse = userResponse;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAppMessage() {
		return appMessage;
	}
	public void setAppMessage(String appMessage) {
		this.appMessage = appMessage;
	}
	public String getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(String userResponse) {
		this.userResponse = userResponse;
	}

	@Override
	public String toString() {
		return "EntityRecordCollection [Id=" + Id + ", name=" + name + ", type=" + type + ", appMessage=" + appMessage
				+ ", userResponse=" + userResponse + "]";
	}

}
