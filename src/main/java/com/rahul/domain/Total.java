package com.rahul.domain;

import java.io.Serializable;

public class Total implements Serializable {

	private static final long serialVersionUID = 6305544096143577423L;

	private int orgsCount;
	private int spacesCount;
	private int usersCount;
	private int applicationsCount;
	private int buildpacksCount;
	private int routesCount;

	private int servicesCount;
	private int serviceBrokersCount;
	private int serviceInstancesCount;

	public int getOrgsCount() {
		return orgsCount;
	}

	public void setOrgsCount(int orgsCount) {
		this.orgsCount = orgsCount;
	}

	public int getSpacesCount() {
		return spacesCount;
	}

	public void setSpacesCount(int spacesCount) {
		this.spacesCount = spacesCount;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public int getApplicationsCount() {
		return applicationsCount;
	}

	public void setApplicationsCount(int applicationsCount) {
		this.applicationsCount = applicationsCount;
	}

	public int getRoutesCount() {
		return routesCount;
	}

	public void setRoutesCount(int routesCount) {
		this.routesCount = routesCount;
	}

	public int getServicesCount() {
		return servicesCount;
	}

	public void setServicesCount(int servicesCount) {
		this.servicesCount = servicesCount;
	}

	public int getServiceBrokersCount() {
		return serviceBrokersCount;
	}

	public void setServiceBrokersCount(int serviceBrokersCount) {
		this.serviceBrokersCount = serviceBrokersCount;
	}

	public int getServiceInstancesCount() {
		return serviceInstancesCount;
	}

	public void setServiceInstancesCount(int serviceInstancesCount) {
		this.serviceInstancesCount = serviceInstancesCount;
	}

	public int getBuildpacksCount() {
		return buildpacksCount;
	}

	public void setBuildpacksCount(int buildpacksCount) {
		this.buildpacksCount = buildpacksCount;
	}

}
