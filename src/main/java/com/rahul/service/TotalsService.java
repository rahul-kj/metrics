package com.rahul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.domain.Total;

@Service
public class TotalsService {
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	BuildpackService buildpackService;
	
	@Autowired
	SpaceService spaceService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	ServicesService servicesService;
	
	public Total getTotals() {
		Total total = new Total();
		
		total.setApplicationsCount(applicationService.getTotalApps());
		total.setBuildpacksCount(buildpackService.getTotalBuildpacks());
		total.setOrgsCount(orgService.getTotalOrgs());
		total.setRoutesCount(routeService.getTotalRoutes());
		total.setSpacesCount(spaceService.getTotalSpaces());
		total.setServiceBrokersCount(servicesService.getTotalServiceBrokers());
		total.setServicesCount(servicesService.getTotalServices());
		total.setServiceInstancesCount(servicesService.getTotalServiceInstances());
		total.setUsersCount(userService.getTotalUsers());
		
		return total;
	}
}
