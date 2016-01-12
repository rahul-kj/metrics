package com.rahul.service;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.domain.Attribute;
import com.rahul.domain.Deployment;
import com.rahul.domain.Job;
import com.rahul.domain.Metrics;

@Service
public class MbeanService {
	@Autowired
	MBeanServerConnection connection;

	private Map<String, Deployment> deploymentsMap;

	public Metrics getMetrics() {
		deploymentsMap = new HashMap<>();
		
		Set<ObjectInstance> objects;
		try {
			objects = connection.queryMBeans(new ObjectName("org.cloudfoundry:*"), null);
			
			System.out.println("Initial Jobs size is " + objects.size());

			for (ObjectInstance object : objects) {
				populateMap(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int finalSize = 0;
		Metrics metrics = new Metrics();

		Set<String> deploymentNames = deploymentsMap.keySet();
		for (String deploymentName : deploymentNames) {
			Deployment deployment = deploymentsMap.get(deploymentName);
			metrics.addDeployment(deployment);
			finalSize += deployment.getJobs().size();
		}
		
		System.out.println("Final Jobs size is " + finalSize);
		
		deploymentsMap = null;

		return metrics;
	}

	private void populateMap(ObjectInstance object) throws Exception {
		ObjectName objectName = object.getObjectName();

		Deployment deployment = null;

		populateDeployment(objectName, deployment);
	}

	private void populateDeployment(ObjectName objectName, Deployment deployment) throws Exception {
		Hashtable<String, String> keyPropertyList = objectName.getKeyPropertyList();
		Job job = new Job();

		Enumeration<String> keys = keyPropertyList.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = keyPropertyList.get(key);

			if (key.equals("deployment")) {
				deployment = deploymentsMap.containsKey(value) ? deploymentsMap.get(value) : new Deployment(value);
			} else if (!key.isEmpty()) {
				populateJob(job, key, value);
			}
		}

		populateJobWithAttributes(objectName, job);

		if (deployment != null) {
			deployment.addJobs(job);
			deploymentsMap.put(deployment.getName(), deployment);
		}
	}

	private void populateJob(Job job, String key, String value) {
		try {
			Field field = Job.class.getDeclaredField(key);
			field.setAccessible(true);
			field.set(job, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateJobWithAttributes(ObjectName objectName, Job job) throws Exception {
		if (job == null) {
			return;
		}

		MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		if (attributes != null) {
			for (MBeanAttributeInfo mBeanAttributeInfo : attributes) {
				String attributeName = mBeanAttributeInfo.getName();
				Object attributeValue = connection.getAttribute(objectName, attributeName);

				job.addAttribute(new Attribute(attributeName, attributeValue.toString()));
			}
		}
	}

}
