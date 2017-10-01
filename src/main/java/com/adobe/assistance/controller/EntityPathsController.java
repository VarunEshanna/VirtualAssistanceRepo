package com.adobe.assistance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.assistance.collections.EntityPathsCollection;
import com.adobe.assistance.repository.EntityPathsRepository;

@RestController
public class EntityPathsController {
		
		@Autowired
		private EntityPathsRepository repository;
		
		@RequestMapping(value = "/getEntityPath", method = RequestMethod.POST)
		public List<EntityPathsCollection> updateGetData(@RequestBody EntityPathsCollection ec) {
			System.out.println("Inside get method"+ec.getInputData());
			List<EntityPathsCollection> ec1 = repository.findByInputData(ec.getInputData());
			System.out.println("ec"+ec1);
			System.out.println("ec"+ec1.size());
			return ec1;
		}
		
		@RequestMapping(value = "/updateEntityPaths", method = RequestMethod.POST)
		public void updateData(@RequestBody EntityPathsCollection ec) {
			repository.save(ec);
		}
		
		@RequestMapping(value = "/updateListEntityPaths", method = RequestMethod.POST)
		public void updateListData(@RequestBody List<EntityPathsCollection> ec) {
			repository.save(ec);
		}

	
	
}
