package com.rahul.service;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rahul.domain.Attribute;
import com.rahul.domain.CustomJobMetric;
import com.rahul.domain.FixedAttribute;
import com.rahul.domain.JobDetail;
import com.rahul.domain.Metrics;
import com.rahul.domain.VMMetric;

@Service
public class MetricService {
	@Autowired
	MBeanServerConnection connection;

	public Metrics getMetrics() {
		Metrics metrics = new Metrics();

		Set<ObjectInstance> objects;
		try {
			objects = connection.queryMBeans(new ObjectName("org.cloudfoundry:*"), null);

			System.out.println("Initial Jobs size is " + objects.size());

			for (ObjectInstance object : objects) {
				populateMetrics(object, metrics);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int finalSize = metrics.getCustomJobMetrics().size() + metrics.getVmMetrics().size();
		System.out.println("Final Jobs size is " + finalSize);
		System.out.println("Metrics - " + metrics);

		return metrics;
	}

	private void populateMetrics(ObjectInstance object, Metrics metrics) throws Exception {
		ObjectName objectName = object.getObjectName();
		Hashtable<String, String> keyPropertyList = objectName.getKeyPropertyList();
		JobDetail jobDetail = new JobDetail();

		Enumeration<String> keys = keyPropertyList.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = keyPropertyList.get(key);
			populateObject(jobDetail, key, value);
		}

		if ((StringUtils.isEmpty(jobDetail.getIp()) || jobDetail.getIp().equalsIgnoreCase("null"))
				&& !jobDetail.getDeployment().equalsIgnoreCase("untitled_dev")) {
			VMMetric vmMetric = populateVMMetrics(objectName, jobDetail);
			vmMetric.setJobDetail(jobDetail);
			metrics.addVmMetrics(vmMetric);
		} else {
			CustomJobMetric customJobMetric = populateCustomJobMetrics(objectName, jobDetail);
			customJobMetric.setJobDetail(jobDetail);
			metrics.addCustomJobMetrics(customJobMetric);
		}

	}

	private void populateObject(Object obj, String key, String value) {
		try {
			Field field = obj.getClass().getDeclaredField(key);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private VMMetric populateVMMetrics(ObjectName objectName, JobDetail jobDetail) throws Exception {
		VMMetric vmMetric = new VMMetric();
		FixedAttribute fixedAttribute = new FixedAttribute();

		MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		if (attributes != null) {
			for (MBeanAttributeInfo mBeanAttributeInfo : attributes) {
				String attributeName = mBeanAttributeInfo.getName();
				Object attributeValue = connection.getAttribute(objectName, attributeName);

				String fieldName = attributeName.replace(".", "_");
				String fieldValue = attributeValue.toString();

				populateObject(fixedAttribute, fieldName, fieldValue);
			}
		}

		vmMetric.setFixedAttribute(fixedAttribute);
		vmMetric.setJobDetail(jobDetail);

		return vmMetric;
	}

	private CustomJobMetric populateCustomJobMetrics(ObjectName objectName, JobDetail job) throws Exception {
		CustomJobMetric customJobMetric = new CustomJobMetric();

		MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		if (attributes != null) {
			for (MBeanAttributeInfo mBeanAttributeInfo : attributes) {
				String attributeName = mBeanAttributeInfo.getName();
				Object attributeValue = connection.getAttribute(objectName, attributeName);
				customJobMetric.addCustomAttributes(new Attribute(attributeName, attributeValue.toString()));
			}
		}

		return customJobMetric;
	}

}
