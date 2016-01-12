package com.rahul;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

@Configuration
public class SpringConfiguration {
	private static final String SERVER_IP = "host";
	private static final String SERVER_PORT = "port";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	
	private static final String JMX_URL="service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi";

	@Bean
	public MBeanServerConnectionFactoryBean connection() {
		String host = System.getenv(SERVER_IP);
		String port = System.getenv(SERVER_PORT) != null ? System.getProperty(SERVER_PORT) : "44444";
		String username = System.getenv(USERNAME);
		String password = System.getenv(PASSWORD);

		MBeanServerConnectionFactoryBean factoryBean = new MBeanServerConnectionFactoryBean();
		try {
			String serviceURL = createServiceURL(host, port);
			factoryBean.setServiceUrl(serviceURL);
			Map<String, Object> environmentMap = new HashMap<String, Object>();
			String[] credentials = { username, password };
			environmentMap.put("jmx.remote.credentials", credentials);
			factoryBean.setEnvironmentMap(environmentMap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return factoryBean;
	}

	private String createServiceURL(String host, String port) {
		String serviceURL = String.format(JMX_URL, host, port, host, port);
		System.out.println(serviceURL);
		return serviceURL;
	}
	
}
