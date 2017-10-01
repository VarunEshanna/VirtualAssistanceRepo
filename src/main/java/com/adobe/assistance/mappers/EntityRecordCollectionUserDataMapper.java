package com.adobe.assistance.mappers;

import java.util.ArrayList;
import java.util.List;

import com.adobe.assistance.beans.UserData;
import com.adobe.assistance.collections.EntityRecordCollection;

public class EntityRecordCollectionUserDataMapper {
	
	public static List<UserData> mapListEntityRecordToUserData(List<EntityRecordCollection> list){
		List<UserData> userDataList = new ArrayList<UserData>();
		for(EntityRecordCollection ec : list){
			if(ec != null){
				userDataList.add(mapEntityRecordToUserData(ec));	
			}
		}
		return userDataList;
	}
	
	public static UserData mapEntityRecordToUserData(EntityRecordCollection ec1){
		UserData userData = new UserData();
		userData.setName(ec1.getName());
		userData.setUserResponse(ec1.getUserResponse());
		userData.setAppMessage(ec1.getAppMessage());
		userData.setType(ec1.getType());
		return userData;
	}

}
