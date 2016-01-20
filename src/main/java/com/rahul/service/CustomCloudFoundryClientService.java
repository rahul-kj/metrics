package com.rahul.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.util.JsonUtil;
import org.cloudfoundry.client.lib.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rahul.domain.CloudUser;
import com.rahul.domain.Metadata;
import com.rahul.domain.UserEntity;
import com.rahul.domain.Users;

@Service
public class CustomCloudFoundryClientService {
	@Autowired
	CloudFoundryClient cloudFoundryClient;

	RestTemplate restTemplate;

	RestUtil restUtil;

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	public Users getUsers() {
		OAuth2AccessToken token = cloudFoundryClient.login();
		String authorizationHeaderValue = token.getTokenType() + " " + token.getValue();

		String url = cloudFoundryClient.getCloudControllerUrl() + "/v2/users";

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);
		Map<String, Object> respMap = JsonUtil.convertJsonToMap(response.getBody());

		Integer totalUsersCount = Integer.valueOf(respMap.get("total_results").toString());
		System.out.println("Total users count: " + totalUsersCount);

		List<Map<String, Object>> allResources = getAllResources(respMap);
		Users users = getCloudUsers(allResources);
		users.setTotalUsers(totalUsersCount);
		
		return users;
	}

	private Users getCloudUsers(List<Map<String, Object>> allResources) {
		Users user = new Users();
		for (Map<String, Object> resource : allResources) {
			user.addCloudUsers(getCloudUser(resource));
		}
		return user;
	}

	private CloudUser getCloudUser(Map<String, Object> resource) {
		CloudUser cloudUser = new CloudUser();

		Metadata meta = getMeta(resource);
		UserEntity userEntity = getEntity(resource);

		cloudUser.setMetadata(meta);
		cloudUser.setEntity(userEntity);

		return cloudUser;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getAllResources(Map<String, Object> respMap) {
		List<Map<String, Object>> allResources = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> newResources = (List<Map<String, Object>>) respMap.get("resources");
		if (newResources != null && newResources.size() > 0) {
			allResources.addAll(newResources);
		}
		String nextUrl = (String) respMap.get("next_url");
		while (nextUrl != null && nextUrl.length() > 0) {
			nextUrl = addPageOfResources(nextUrl, allResources);
		}
		return allResources;
	}

	private RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restUtil = new RestUtil();
			restTemplate = restUtil.createRestTemplate(null, true);
		}
		return restTemplate;
	}

	protected String getUrl(String path) {
		String cloudControllerUrl = cloudFoundryClient.getCloudControllerUrl().toString();
		return cloudControllerUrl + (path.startsWith("/") ? path : "/" + path);
	}

	@SuppressWarnings("unchecked")
	private String addPageOfResources(String nextUrl, List<Map<String, Object>> allResources) {
		String resp = getRestTemplate().getForObject(getUrl(nextUrl), String.class);
		Map<String, Object> respMap = JsonUtil.convertJsonToMap(resp);
		List<Map<String, Object>> newResources = (List<Map<String, Object>>) respMap.get("resources");
		if (newResources != null && newResources.size() > 0) {
			allResources.addAll(newResources);
		}
		return (String) respMap.get("next_url");
	}

	@SuppressWarnings("unchecked")
	public static Metadata getMeta(Map<String, Object> resource) {
		Map<String, Object> metadata = (Map<String, Object>) resource.get("metadata");
		UUID guid;
		try {
			guid = UUID.fromString(String.valueOf(metadata.get("guid")));
		} catch (IllegalArgumentException e) {
			guid = null;
		}
		Date createdDate = parseDate(String.valueOf(metadata.get("created_at")));
		Date updatedDate = parseDate(String.valueOf(metadata.get("updated_at")));
		String url = String.valueOf(metadata.get("url"));
		return new Metadata(guid, createdDate, updatedDate, url);
	}

	@SuppressWarnings("unchecked")
	private UserEntity getEntity(Map<String, Object> resource) {
		UserEntity userEntity = new UserEntity();
		
		Map<String, Object> metadata = (Map<String, Object>) resource.get("entity");
		userEntity.setDefaultSpaceGuid(String.valueOf(metadata.get("default_space_guid")));
		userEntity.setUsername(String.valueOf(metadata.get("username")));
		userEntity.setSpacesUrl(String.valueOf(metadata.get("spaces_url")));
		userEntity.setOrganizationsUrl(String.valueOf(metadata.get("organizations_url")));
		userEntity.setManagedOrganizationsUrl(String.valueOf(metadata.get("managed_organizations_url")));
		userEntity.setBillingManagedOrganizationsUrl(String.valueOf(metadata.get("billing_managed_organizations_url")));
		userEntity.setAuditedOrganizationsUrl(String.valueOf(metadata.get("audited_organizations_url")));
		userEntity.setManagedSpacesUrl(String.valueOf(metadata.get("managed_spaces_url")));
		userEntity.setAuditedSpacesUrl(String.valueOf(metadata.get("audited_spaces_url")));
		userEntity.setAdmin(Boolean.getBoolean(String.valueOf(metadata.get("admin"))));
		userEntity.setActive(Boolean.getBoolean(String.valueOf(metadata.get("active"))));
		
		return userEntity;
	}

	private static Date parseDate(String dateString) {
		if (dateString != null) {
			try {
				// if the time zone part of the dateString contains a colon
				// (e.g. 2013-09-19T21:56:36+00:00)
				// then remove it before parsing
				String isoDateString = dateString.replaceFirst(":(?=[0-9]{2}$)", "").replaceFirst("Z$", "+0000");
				return dateFormatter.parse(isoDateString);
			} catch (Exception ignore) {
			}
		}
		return null;
	}
}
