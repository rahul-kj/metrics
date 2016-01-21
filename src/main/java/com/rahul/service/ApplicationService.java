package com.rahul.service;

import java.util.List;
import java.util.Map;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
	
	@Autowired
	CloudFoundryClient cloudFoundryClient;
	
	@Autowired
	CloudFoundryClientService clientService;
	
	public List<CloudApplication> getApplications() {
		return cloudFoundryClient.getApplications();
	}
	
	public Integer getTotalApps() {
		Map<String, Object> respMap = clientService.getResponseMap("/v2/apps");
		Integer totalCount = clientService.getTotalResults(respMap);
		return totalCount;
	}


}
