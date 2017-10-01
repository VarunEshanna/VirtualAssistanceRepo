package com.adobe.assistance.beans;

public class UserData {
	
	public UserData() {
		super();
	}
	
	private String name;
	private String appMessage;
	private String userResponse;
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "UserData [name=" + name + ", appMessage=" + appMessage + ", userResponse=" + userResponse + ", type="
				+ type + "]";
	}
	
}
