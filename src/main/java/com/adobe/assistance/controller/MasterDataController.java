package com.adobe.assistance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.assistance.repository.MasterDataRepository;

@RestController
public class MasterDataController {
	
	@Autowired
	public MasterDataRepository masterDataRepository;
	
	@CrossOrigin	
	@RequestMapping(value = "/saveMasterData", method = RequestMethod.POST)
	public String saveData(){
		return "Success";
	}

}