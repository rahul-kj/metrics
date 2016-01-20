package com.rahul.service;

import java.util.List;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
	
	@Autowired
	CloudFoundryClient cloudFoundryClient;
	
	public List<CloudApplication> getApplications() {
		return cloudFoundryClient.getApplications();
	}

}
