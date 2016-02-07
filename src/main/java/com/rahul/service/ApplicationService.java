package com.rahul.service;

import java.util.List;
import java.util.Map;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.domain.Total;

@Service
public class ApplicationService {

	@Autowired
	CloudFoundryClient cloudFoundryClient;

	@Autowired
	CloudFoundryClientService clientService;

	public List<CloudApplication> getApplications() {
		return cloudFoundryClient.getApplications();
	}

	@SuppressWarnings("unchecked")
	public void populateAppCounts(Total total) {
		Map<String, Object> respMap = clientService.getResponseMap("/v2/apps");
		Integer runningAIs = 0;
		Integer totalAIs = 0;
		Integer totalDiegoApps = 0;
		Integer totalWardenApps = 0;

		List<Map<String, Object>> resources = (List<Map<String, Object>>) respMap.get("resources");
		for (Map<String, Object> resource : resources) {
			Map<String, Object> entity = (Map<String, Object>) resource.get("entity");
			if (entity.get("state").toString().equalsIgnoreCase("STARTED")) {
				runningAIs += Integer.valueOf(entity.get("instances").toString());
			}
			totalAIs += Integer.valueOf(entity.get("instances").toString());
			if (Boolean.valueOf(entity.get("diego").toString())) {
				totalDiegoApps += 1;
			} else {
				totalWardenApps += 1;
			}
		}

		Integer totalCount = clientService.getTotalResults(respMap);

		total.setApplicationsCount(totalCount);
		total.setApplicationInstanceCount(totalAIs);
		total.setRunningApplicationInstanceCount(runningAIs);
		total.setTotalDiegoAppsCount(totalDiegoApps);
		total.setTotalWardenAppsCount(totalWardenApps);
	}

}
