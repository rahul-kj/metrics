package com.rahul.service;

import java.util.List;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceService {
	
	@Autowired
	CloudFoundryClient cloudFoundryClient;
	
	public List<CloudSpace> getSpaces() {
		return cloudFoundryClient.getSpaces();
	}

}
