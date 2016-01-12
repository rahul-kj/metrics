package com.rahul.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Deployment implements Serializable {

	private static final long serialVersionUID = -1358313674931839106L;

	private String name;

	private List<Job> jobs = new ArrayList<Job>();

	public Deployment(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void addJobs(Job job) {
		this.getJobs().add(job);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
