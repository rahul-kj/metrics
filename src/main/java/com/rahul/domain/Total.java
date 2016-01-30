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

	private int appCreateEventsCount;
	private int appDeleteEventsCount;
	private int appCrashEventsCount;
	private int appAuthorizedSSHEventsCount;
	private int appUnAuthorizedSSHEventsCount;
	private int appStartEventsCount;
	private int appStopEventsCount;
	private int appUpdateEventsCount;

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

	public int getAppCreateEventsCount() {
		return appCreateEventsCount;
	}

	public void setAppCreateEventsCount(int appCreateEventsCount) {
		this.appCreateEventsCount = appCreateEventsCount;
	}

	public int getAppDeleteEventsCount() {
		return appDeleteEventsCount;
	}

	public void setAppDeleteEventsCount(int appDeleteEventsCount) {
		this.appDeleteEventsCount = appDeleteEventsCount;
	}

	public int getAppCrashEventsCount() {
		return appCrashEventsCount;
	}

	public void setAppCrashEventsCount(int appCrashEventsCount) {
		this.appCrashEventsCount = appCrashEventsCount;
	}

	public int getAppAuthorizedSSHEventsCount() {
		return appAuthorizedSSHEventsCount;
	}

	public void setAppAuthorizedSSHEventsCount(int appAuthorizedSSHEventsCount) {
		this.appAuthorizedSSHEventsCount = appAuthorizedSSHEventsCount;
	}

	public int getAppUnAuthorizedSSHEventsCount() {
		return appUnAuthorizedSSHEventsCount;
	}

	public void setAppUnAuthorizedSSHEventsCount(int appUnAuthorizedSSHEventsCount) {
		this.appUnAuthorizedSSHEventsCount = appUnAuthorizedSSHEventsCount;
	}

	public int getAppStartEventsCount() {
		return appStartEventsCount;
	}

	public void setAppStartEventsCount(int appStartEventsCount) {
		this.appStartEventsCount = appStartEventsCount;
	}

	public int getAppStopEventsCount() {
		return appStopEventsCount;
	}

	public void setAppStopEventsCount(int appStopEventsCount) {
		this.appStopEventsCount = appStopEventsCount;
	}

	public int getAppUpdateEventsCount() {
		return appUpdateEventsCount;
	}

	public void setAppUpdateEventsCount(int appUpdateEventsCount) {
		this.appUpdateEventsCount = appUpdateEventsCount;
	}

}
