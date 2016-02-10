package com.rahul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.domain.Application;
import com.rahul.service.ApplicationService;

@RestController
@RequestMapping(value = "apps")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;

	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public List<Application> getApps() {
		return applicationService.getApplications();
	}
}
