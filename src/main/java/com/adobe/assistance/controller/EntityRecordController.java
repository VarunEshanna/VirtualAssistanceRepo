package com.adobe.assistance.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.assistance.collections.EntityRecordCollection;
import com.adobe.assistance.repository.EntityRecordRepository;

@RestController
public class EntityRecordController {
	
	@Autowired
	private EntityRecordRepository repository1;
	
	@RequestMapping(value = "/getEntityByName", method = RequestMethod.POST)
	public EntityRecordCollection updateGetData(@RequestBody EntityRecordCollection ec) {
		System.out.println("Inside get method"+ec.getName());
		EntityRecordCollection ec1 = repository1.findByName(ec.getName());
		System.out.println("ec"+ec1);
		return ec1;
	}
	
	@RequestMapping(value = "/updateEntity", method = RequestMethod.POST)
	public void updateData(@RequestBody EntityRecordCollection ec) {
		repository1.save(ec);
	}
	
	@RequestMapping(value = "/updateListEntity", method = RequestMethod.POST)
	public void updateListData(@RequestBody List<EntityRecordCollection> ec) {
		repository1.save(ec);
	}
	
	@RequestMapping(value = "/getListEntity", method = RequestMethod.POST)
	public List<EntityRecordCollection> getListData(@RequestBody List<String> name) {
		List<EntityRecordCollection> lst = new ArrayList<EntityRecordCollection>();
		for(String s : name){
			lst.add(repository1.findEntityRecordCollectionByName(s));
		}
		return lst;
	}
	
	
}
