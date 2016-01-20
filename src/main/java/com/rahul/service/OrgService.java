package com.rahul.service;

import java.util.List;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgService {
	@Autowired
	CloudFoundryClient cloudFoundryClient;
	
	public List<CloudOrganization> getOrgs() {
		List<CloudOrganization> organizations = cloudFoundryClient.getOrganizations();
		return organizations;
	}
	
}
