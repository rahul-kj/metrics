package com.rahul.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.domain.Metrics;
import com.rahul.service.MbeanService;

@RestController
@RequestMapping("/mbeans")
public class MbeanController {
	@Autowired
	MbeanService mbeanService;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public Metrics getAllMetrics() {
		System.out.println("Got a request - " + new Date());
		Metrics metrics = mbeanService.getMetrics();
		System.out.println("Sending response - " + new Date());
		return metrics;
	}
}
