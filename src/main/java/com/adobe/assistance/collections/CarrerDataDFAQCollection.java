package com.adobe.assistance.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="CarrerFAQPaths")
public class CarrerDataDFAQCollection {

	@Id
	private String Id;
	@Field(value="INPUT_DATA")
	private String inputData;
	@Field(value="OUTPUT_DATA")
	private String outputData;
	
	public CarrerDataDFAQCollection(){
		super();
	}
	
	public CarrerDataDFAQCollection(String inputData, String outputData) {
		super();
		this.inputData = inputData;
		this.outputData = outputData;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getInputData() {
		return inputData;
	}
	public void setInputData(String inputData) {
		this.inputData = inputData;
	}
	public String getOutputData() {
		return outputData;
	}
	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}

	@Override
	public String toString() {
		return "CarrerDataDFAQCollection [Id=" + Id + ", inputData=" + inputData + ", outputData=" + outputData + "]";
	}

}


