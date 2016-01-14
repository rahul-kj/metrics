package com.rahul.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class VMMetric implements Serializable {

	private static final long serialVersionUID = -5502030905649165163L;

	private JobDetail jobDetail;
	private FixedAttribute fixedAttribute;

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public FixedAttribute getFixedAttribute() {
		return fixedAttribute;
	}

	public void setFixedAttribute(FixedAttribute fixedAttribute) {
		this.fixedAttribute = fixedAttribute;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
