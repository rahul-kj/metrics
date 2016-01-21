package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.domain.Total;
import com.rahul.service.TotalsService;

@RestController
@RequestMapping(value = "totals")
public class TotalController {

	@Autowired
	TotalsService totalsService;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public Total getTotals() {
		return totalsService.getTotals();
	}

}
