package com.rahul.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Metrics implements Serializable{

	private static final long serialVersionUID = 7593142592683888616L;
	
	private List<Deployment> deployments = new ArrayList<>();

	public List<Deployment> getDeployments() {
		return deployments;
	}
	
	public void addDeployment(Deployment deployment) {
		this.getDeployments().add(deployment);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
