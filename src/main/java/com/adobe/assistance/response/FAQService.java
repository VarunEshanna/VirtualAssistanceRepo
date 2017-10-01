package com.adobe.assistance.response;

public class FAQService {
	
	public static String getAgmtLinks(String agmtType){
		String responseText = "Unable to identfy the agreement type "+agmtType+". Please contact customer care for quicker resolution";
		if(agmtType != null){
			agmtType = agmtType.toLowerCase();
		}
		
		String C2Alink = "https://inside.corp.adobe.com/content/dam/legal/documents/C2A%20Job%20Aid_12.pdf";
		String NDAlink = "https://inside.corp.adobe.com/content/dam/legal/documents/ECM_JobAid4.pdf";
		
		if(agmtType.contains("c2a")){
			responseText = "Creating a C2A agreement is very simple. Please follow below the link to create one "+C2Alink;
		}else if (agmtType.contains("nda")){
			responseText = "Creating a NDA agreement is very simple. Please follow below the link to create one "+NDAlink;
		}
		return responseText;
	}

}
